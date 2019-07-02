package com.magicsoft.popuputils.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author : Lss kiwilss
 * @FileName: Utils
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-01
 * @desc : {DESCRIPTION}
 */
public class Utils {
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }

    public static Bitmap mergeBitmap2(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth() + secondBitmap.getWidth(),
                firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, firstBitmap.getWidth(), 0, null);
        return bitmap;
    }

    public static Bitmap mergeBitmap3(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(),
                firstBitmap.getHeight() + secondBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, firstBitmap.getHeight(), null);
        return bitmap;
    }

    public static Bitmap mergeBitmap4(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap thirdBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(),
                firstBitmap.getHeight() + secondBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, firstBitmap.getHeight(), null);
        canvas.drawBitmap(thirdBitmap, 0, firstBitmap.getHeight(), null);

        return bitmap;
    }

    public static Bitmap mergeBp(Bitmap bg, Bitmap fg) {
        int bgHeight = bg.getHeight();
        int bgWidth = bg.getWidth();

        int fgWidth = fg.getWidth();

        Bitmap bitmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(bg, 0, 0, null);
        canvas.drawBitmap(fg, (bgWidth - fgWidth) / 2, (float) (bgHeight / 4 * 2.1), null);

        return bitmap;
    }

    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    public static String urlEncode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

}
