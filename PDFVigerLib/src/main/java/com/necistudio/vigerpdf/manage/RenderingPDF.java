package com.necistudio.vigerpdf.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.necistudio.vigerpdf.VigerPDF;

import org.vudroid.core.DecodeServiceBase;
import org.vudroid.core.codec.CodecPage;
import org.vudroid.pdfdroid.codec.PdfContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vim on 1/31/2017.
 */

public class RenderingPDF extends AsyncTask<Void, String, ArrayList<Bitmap>> {
    File file;
    Context context;
    int pageCount, pageData, type;

    public RenderingPDF(Context context, File file, int type) {
        this.file = file;
        this.context = context;
        this.type = type;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(Void... params) {
        try {
            ArrayList<Bitmap> uris = new ArrayList<>();
            DecodeServiceBase decodeService = new DecodeServiceBase(new PdfContext());
            decodeService.setContentResolver(context.getContentResolver());
            decodeService.open(Uri.fromFile(file));
            pageCount = decodeService.getPageCount();

            for (int i = 0; i < pageCount; i++) {
                pageData = i;
                if (isCancelled()) {
                    break;
                }
                CodecPage page = decodeService.getPage(i);
                RectF rectF = new RectF(0, 0, 1, 1);
                Bitmap bitmap = page.renderBitmap(decodeService.getPageWidth(i), decodeService.getPageHeight(i), rectF);
                try {
                    uris.add(bitmap);
                } catch (Exception e) {
                }
                publishProgress("" + (int) ((i * 100) / pageCount));
            }
            if (type == 1) {
                file.delete();
            }

            return uris;

        } catch (Exception e) {

        }
        return null;

    }

    @Override
    protected void onProgressUpdate(String... values) {
//        progressBar.setProgress(Integer.parseInt(values[0]));
//        progressBar.setSecondaryProgress(Integer.parseInt(values[0]) + 10);
//        txtPageProgress.setText(pageData + "/" + pageCount + " page");
    }

    @Override
    public void onPostExecute(ArrayList<Bitmap> uris) {
        try {
            VigerPDF.setData(uris);
        } catch (Exception e) {

        }
    }
}
