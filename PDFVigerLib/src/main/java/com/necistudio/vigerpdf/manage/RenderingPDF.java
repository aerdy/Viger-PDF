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

public class RenderingPDF extends AsyncTask<Void, Bitmap, ArrayList<Bitmap>> {
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

            int a = 10;
            for (int i = 0; i < pageCount; i++) {
                pageData = i;
                if (isCancelled()) {
                    break;
                }
                CodecPage page = decodeService.getPage(i);
                RectF rectF = new RectF(0, 0, 1, 1);
                Bitmap bitmap = page.renderBitmap(decodeService.getPageWidth(i), decodeService.getPageHeight(i), rectF);
                publishProgress(bitmap);
                if (i == a) {
                    a = a + 10;
                    try {
                        Thread.currentThread();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
    protected void onProgressUpdate(Bitmap... values) {
        VigerPDF.setData(values[0]);
    }

    @Override
    public void onPostExecute(ArrayList<Bitmap> uris) {

    }
}
