package com.kiwilss.lxkj.ximageselect.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author : Lss kiwilss
 * @FileName: Utils
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-02
 * @desc : {DESCRIPTION}
 */
public class Utils {

    //检查权限
    public static boolean checkPermission(Context context, String permissionName) {
        int res = context.checkCallingOrSelfPermission(permissionName);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    //申请权限,activity 中使用
    public static void applyPermission(Activity context,String[] perimiss,int requestCode){
        ActivityCompat.requestPermissions(context, perimiss, requestCode);
    }
    //申请权限fragment 中使用
    public static void applyPermission(Fragment context, String[] perimiss, int requestCode){
        context.requestPermissions(perimiss,requestCode);
    }


    //判断相机是否可用
    public static boolean cameraIsCanUse(Context context){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        return true;
    }



    public static String getFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;

        final String scheme = uri.getScheme();
        String data = null;

        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[]
                    { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 上传图片
     */
    public static File upDateFile(Context context,Uri uriClipUri) {
        Bitmap photoBitmap = null;
        File file;
        try {
            //裁剪后的图像转成BitMap
            photoBitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uriClipUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //创建路径
        String path = Environment.getExternalStorageDirectory()
                .getPath() + "/Pic";
        //获取外部储存目录
        file = new File(path);
        //创建新目录, 创建此抽象路径名指定的目录，包括创建必需但不存在的父目录。
        file.mkdirs();
        //以当前时间重新命名文件
        long time = System.currentTimeMillis();
        //生成新的文件
        file = new File(file.toString() + "/" + time+ ".png");
        //创建输出流
        OutputStream out = null;
        try {
            out = new FileOutputStream(file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //压缩文件，返回结果,true说明成功了
        assert photoBitmap != null;
        boolean bCompress = photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        return file;
    }

}
