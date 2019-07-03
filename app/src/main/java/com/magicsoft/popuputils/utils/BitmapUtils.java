package com.magicsoft.popuputils.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    //微信分享时压缩图片
    public Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 80;
        int newHeight = 80;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        if (needRecycle) bitMap.recycle();
        return newBitMap;
    }


    //drawable转bitmap
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    public static Drawable bitmapTodrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        return drawable;
    }

    public static Bitmap getBitmapFromDrawable2(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    //bitmap---.byte[]
    public static byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //byte[]---.bitmap
    public Bitmap getBitmapFromByte(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 根据文字获取图片
     *
     * @param text
     * @return
     */
    public static Bitmap getIndustry(Context context, String text, int drawableId) {
        String color = "#ffeeeade";

        Bitmap src = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
        int x = src.getWidth();
        int y = src.getHeight();
        Bitmap bmp = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(bmp);
        canvasTemp.drawColor(Color.parseColor(color));
        Paint p = new Paint(Paint.FAKE_BOLD_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#ff4e0a13"));
        p.setAlpha(45);
        p.setFilterBitmap(true);
        int size = (int) (18 * context.getResources().getDisplayMetrics().density);
        p.setTextSize(size);
        float tX = (x - getFontlength(p, text)) / 2;
        float tY = (y - getFontHeight(p)) / 2 + getFontLeading(p);
        canvasTemp.drawText(text, tX, tY, p);
        return toRoundCorner(bmp, 2);
    }

    /**
     * 获取圆角图片
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    //压缩图片的尺寸大小
    public static Bitmap compressPathSize(String path, int newWidth, int newHight) {
        //先获取图片的尺寸大小
        //获取解析bitmap的选项参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        //仅仅解析图片的边框
        options.inJustDecodeBounds = true;
        //从路径或文件中获取图片bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        //解析后,bitmap为空,会将长度和宽度放到options中,
        //此时取得bitmap的宽高
        int oldWidth = (int) Math.ceil(options.outWidth);//向上取整
        int oldHight = (int) Math.ceil(options.outHeight);
        //获取宽高的比例
        int sizeWidth = oldWidth / newWidth;
        int sizeHight = oldHight / newHight;

        int sampleSize = sizeHight > sizeWidth ? sizeHight : sizeWidth;
        //如果超出指定的大小,就压缩图片
        if (sizeHight > 1 && sizeWidth > 1) {
            //如同前面的步骤,向选项参数中放入新的压缩比例
            options.inSampleSize = sampleSize;
        }
        //需要完整解析整张图片
        options.inJustDecodeBounds = false;
        //按照新的缩放比例重新解析
        Bitmap bp = BitmapFactory.decodeFile(path, options);
        return bp;
    }

    //压缩图片的内存大小,使文件占据更小的空间
    public static String compressPathFileSize(String path, String newFileName, int size) {//size单位kb
        //先获取图片bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        //将图片压缩到想要的内存以内
        //第一次,不压缩图片,先判断图片内存是否符合要求
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int compressQuality = 100;

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > size) {
            //清空已经写入的流
            baos.reset();
            //大于想要的尺寸继续压缩
            compressQuality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos);
        }
        //跳出循环,表示图片尺寸已经小于size,往文件中写
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(path, newFileName + ".jpg"));
            byte[] bytes = baos.toByteArray();
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            return path.replace(".jpg", newFileName + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path;
    }


    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        int bytes = baos.toByteArray().length;
        while ((bytes / 1024 > 100) && (options >= 20)) {  //循环判断如果压缩后图片是否大于10kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            bytes = baos.toByteArray().length;
        }
        image.recycle();
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static void saveBpImageToGallery(Context context, Bitmap bmp) {//保存bitmap图片
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

    public static void saveFileImageToGallery(Context context, String imgFile) {//保存file图片
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Bitmap bmp = BitmapFactory.decodeFile(imgFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }


    public static Bitmap getBitmapByUri(Context context, Uri uri) {
        Bitmap bp = null;
        try {
            bp = BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bp;
    }
}