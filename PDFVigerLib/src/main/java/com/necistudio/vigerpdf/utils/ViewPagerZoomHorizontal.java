package com.necistudio.vigerpdf.utils;

/**
 * Created by vim on 13/03/17.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * <p/>
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * <p/>
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 *
 * @author Chris Banes
 */
public class ViewPagerZoomHorizontal extends ViewPager {

    public ViewPagerZoomHorizontal(Context context) {
        super(context);
    }

    public ViewPagerZoomHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newY, newX);

        return ev;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercepted;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}