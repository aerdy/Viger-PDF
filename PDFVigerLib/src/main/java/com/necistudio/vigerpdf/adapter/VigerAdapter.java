package com.necistudio.vigerpdf.adapter;

/**
 * Created by jarod on 12/15/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.necistudio.pdfvigerengine.R;
import com.necistudio.vigerpdf.utils.PhotoViewAttacher;

import java.util.ArrayList;

public class VigerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Bitmap> itemList;
    private LayoutInflater mLayoutInflater;

    //Variables used to set page number and colors
    private static boolean isPageNumberEnabled = true;
    private static int pageNumberColor = 0;
    private static int pageNumberBgColor = 0;

    public VigerAdapter(Context context, ArrayList<Bitmap> itemList) {
        this.context = context;
        this.itemList = itemList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pdf_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgData);
        imageView.setImageBitmap(itemList.get(position));

        //Set page number and colors if isPageNumberEnabled = true
        if (isPageNumberEnabled) {
            TextView pageNumber = (TextView) itemView.findViewById(R.id.pageNumber);
            pageNumber.setVisibility(View.VISIBLE);
            pageNumber.setText(String.valueOf(position + 1) + "/" + String.valueOf(itemList.size()));

            if (pageNumberColor != 0)
                pageNumber.setTextColor(ContextCompat.getColor(context, pageNumberColor));

            if (pageNumberBgColor != 0) {
                GradientDrawable bg_drawable = (GradientDrawable) pageNumber.getBackground().getCurrent();
                bg_drawable.setColor(ContextCompat.getColor(context, pageNumberBgColor));
            }
        }

        container.addView(itemView);
        new PhotoViewAttacher(imageView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public static void setPageNumberEnabled(boolean enabled) {
        isPageNumberEnabled = enabled;
    }

    public static void setPageNumberColors(int pageNumberColor) {
        VigerAdapter.pageNumberColor = pageNumberColor;
    }

    public static void setPageNumberBgColor(int pageNumberBgColor) {
        VigerAdapter.pageNumberBgColor = pageNumberBgColor;
    }

}