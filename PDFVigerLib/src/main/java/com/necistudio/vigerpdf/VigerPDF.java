package com.necistudio.vigerpdf;

import android.content.Context;
import android.graphics.Bitmap;

import com.necistudio.vigerpdf.manage.OnResultListener;
import com.necistudio.vigerpdf.manage.RenderingPDF;
import com.necistudio.vigerpdf.manage.RenderingPDFNetwork;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vim on 1/31/2017.
 */

public class VigerPDF {
    static OnResultListener onResultListener;

    public VigerPDF(Context context, File file) {
        new RenderingPDF(context, file, 0).execute();
    }

    public VigerPDF(Context context, String endpoint) {
        new RenderingPDFNetwork(context, endpoint);
    }

    public static ArrayList<Bitmap> setData(ArrayList<Bitmap> itemData) {
        onResultListener.resultData(itemData);
        return itemData;
    }

    public void initFromFile(OnResultListener resultListener) {
        this.onResultListener = resultListener;
    }


}
