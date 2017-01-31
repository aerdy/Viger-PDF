package com.necistudio.vigerpdf.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.necistudio.vigerpdf.VigerPDF;
import com.necistudio.vigerpdf.network.RestClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vim on 1/31/2017.
 */

public class RenderingPDFNetwork {
    private Context context;
    public RenderingPDFNetwork(Context context, String endpoint) {
        this.context = context;
        final RestClient.ApiInterface service = RestClient.getClient();
        Call<ResponseBody> call = service.streamFile(endpoint);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    writeResponseBodyToDisk(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private boolean writeResponseBodyToDisk(final ResponseBody body) {
        new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String root = Environment.getExternalStorageDirectory().toString();
                    final File pdfFolder = new File(root + "/Android/data/" + context.getPackageName());
                    pdfFolder.mkdirs();
                    String path = pdfFolder + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    pdfFolder.createNewFile();
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        byte[] fileReader = new byte[4096];

                        long fileSize = body.contentLength();
                        long fileSizeDownloaded = 0;

                        inputStream = body.byteStream();
                        outputStream = new FileOutputStream(path);

                        while (true) {
                            if (isCancelled()) {
                                break;
                            }
                            int read = inputStream.read(fileReader);
                            if (read == -1) {
                                break;
                            }
                            outputStream.write(fileReader, 0, read);
                            fileSizeDownloaded += read;
                            publishProgress("" + (int) ((fileSizeDownloaded * 100) / fileSize));
                        }

                        outputStream.flush();

                    } catch (IOException e) {
                        //  Log.e("data",e.getMessage());
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    }
                    return path;
                } catch (IOException e) {
                    return null;
                }
            }


            @Override
            protected void onProgressUpdate(String... values) {
                Log.e("data",""+Integer.parseInt(values[0]));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    File file = new File(s);
                    new RenderingPDF(context, file, 1).execute();
                } catch (Exception e) {

                }
            }
        }.execute();
        return true;
    }
}
