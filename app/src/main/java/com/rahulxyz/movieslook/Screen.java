package com.rahulxyz.movieslook;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by raul_Will on 6/12/2017.
 */

class Screen {
    static int width, height;
    static String sizeMax;

    static void setScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        //in lower resolution leseer size image is downloaded
        if(width<= 540)
            sizeMax = context.getString(R.string.sizeSmall);
        else
            sizeMax = context.getString(R.string.sizeBig);

    }


}
