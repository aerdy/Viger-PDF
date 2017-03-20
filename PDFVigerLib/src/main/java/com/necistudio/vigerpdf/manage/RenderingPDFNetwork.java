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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vim on 1/31/2017.
 */

public class RenderingPDFNetwork {
    private Context context;
    private Observable<String> observable;

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
        observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
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
                            int read = inputStream.read(fileReader);
                            if (read == -1) {
                                break;
                            }
                            outputStream.write(fileReader, 0, read);
                            fileSizeDownloaded += read;
                            e.onNext("" + (int) ((fileSizeDownloaded * 100) / fileSize));
                        }

                        outputStream.flush();

                    } catch (IOException ee) {
                        //  Log.e("data",e.getMessage());
                        e.onError(ee);
                        e.onComplete();
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    }
                    File file = new File(path);
                    new RenderingPDF(context, file, 1);
                    e.onComplete();
                } catch (IOException ee) {
                    e.onError(ee);
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("data", "" + Integer.parseInt(s));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return true;
    }
}
