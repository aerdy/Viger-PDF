package com.necistudio.vigerpdf.manage;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vim on 1/31/2017.
 */

public interface OnResultListener  {
    void resultData(Bitmap data);
    void progressData(int progress);
    void failed(Throwable t);
}