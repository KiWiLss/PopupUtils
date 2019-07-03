package com.kiwilss.lxkj.ximageselect;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.kiwilss.lxkj.ximageselect.callback.XImgSelectCallback;
import com.kiwilss.lxkj.ximageselect.utils.Utils;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * @author : Lss kiwilss
 * @FileName: XImgSelect
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-02
 * @desc : {DESCRIPTION}
 */
public class XImgSelect {

    private String[] CAMERA = {Manifest.permission.CAMERA};

    private String[] WRITE_EXTERNAL_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Activity mActivity;
    private Fragment mFragment;
    private boolean isClip;//是否需要裁剪图片
    private XImgSelectCallback mXImgSelectCallback;//结果处理

    public static final int OPEN_ALBUM=222;//打开相册是标识
    public static final int TAKE_PHOTO = 111;//拍照的标记
    public static final int CLIP = 333;//裁剪的标记

    private int mType= 0;//1,拍照,2打开相册

    public XImgSelect(Activity activity, boolean isClip, XImgSelectCallback xImgSelectCallback){//来自 activity 界面
        this.mActivity = activity;
        this.isClip = isClip;
        this.mXImgSelectCallback = xImgSelectCallback;
    }

    public XImgSelect(Fragment fragment,boolean isClip, XImgSelectCallback xImgSelectCallback){//来自 fragment 界面
        this.mFragment = fragment;
        this.isClip = isClip;
        this.mXImgSelectCallback = xImgSelectCallback;
    }

    public Context getContext(){
        if (mActivity != null){
            return mActivity;
        }else if (mFragment != null){
            return mFragment.getContext();
        }else {
            return null;
        }
    }


    /**
     *拍照
     */
    public void takePhoto(){
        mType = 1;
        //判断相机是否可用
        if (Utils.cameraIsCanUse(getContext())) {//可用,判断是否有对应的权限
            if (Utils.checkPermission(getContext(), Manifest.permission.CAMERA)) {
                //有权限
                goToTakePhoto();
            }else {//没有权限申请
                if (mActivity != null){
                    Utils.applyPermission(mActivity,CAMERA,TAKE_PHOTO);
                }else {
                    Utils.applyPermission(mFragment,CAMERA,TAKE_PHOTO);
                }
            }
        }else {
            //相机不可用,不可用的情况比较少
            //mXImgSelectCallback.noCamera();
            Toast.makeText(getContext(), "相机不可用", Toast.LENGTH_SHORT).show();
        }

    }

    private String mImgPath;
    private Uri mImgUri;
    private void goToTakePhoto() {
        //创建用于存放拍摄照片的文件
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output.jpg");
        //如果文件存在,就删除重新创建
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
            mImgPath = outputImage.toString();//记录存储拍照图片的路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = FileProvider7.getUriForFile(getContext(), outputImage);
        mImgUri = uri;
        //mXImgSelectCallback.getPicPath(mImgPath);
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加一个临时权限
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (mActivity != null){
            mActivity.startActivityForResult(captureIntent,TAKE_PHOTO);
        }else if (mFragment != null){
            mFragment.startActivityForResult(captureIntent,TAKE_PHOTO);
        }
    }

    public  String getTakePhotoPath(){
        return mImgPath;
    }

    public Uri getTakePhotoUri(){
        return mImgUri;
    }

    public void openAlbum(){
        mType = 2;
        //先判断是否有权限
        boolean permission = Utils.checkPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            if (mActivity != null){
                mActivity.startActivityForResult(intent,OPEN_ALBUM);
            }else if (mFragment != null){
                mFragment.startActivityForResult(intent,OPEN_ALBUM);
            }
        }else {
            //申请权限
            if (mActivity != null){
                Utils.applyPermission(mActivity,WRITE_EXTERNAL_STORAGE,OPEN_ALBUM);
            }else {
                Utils.applyPermission(mFragment,WRITE_EXTERNAL_STORAGE,OPEN_ALBUM);
            }

        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == TAKE_PHOTO)
        {
            if (grantResults.length>0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    goToTakePhoto();
                } else
                {
                    boolean isNoShow = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (mActivity != null){
                            boolean b = mActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                            //Log.e("MMM", "onRequestPermissionsResult: "+b );
                            //false 代表点了不再询问
                            isNoShow = b;
                        }else if (mFragment != null){
                            boolean b1 = mFragment.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                            //Log.e("MMM", "onRequestPermissionsResult: -------"+b1 );
                            isNoShow = b1;
                        }
                    }
                    mXImgSelectCallback.noCamerPermission(isNoShow);
                    //Toast.makeText(activity, "缺少必要权限,请在设置中授予", Toast.LENGTH_SHORT).show();
                }
            }

        }

//
        if (requestCode == OPEN_ALBUM)
        {
            if (grantResults.length>0){

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openAlbum();
                } else
                {
                    boolean isShow = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (mActivity != null){
                            boolean b = mActivity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            //Log.e("MMM", "onRequestPermissionsResult: "+b );
                            //false 代表点了不再询问
                            isShow = b;
                        }else if (mFragment != null){
                            boolean b1 = mFragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            //Log.e("MMM", "onRequestPermissionsResult: -------"+b1 );
                            isShow = b1;
                        }
                    }

                    mXImgSelectCallback.noCamerPermission(isShow);
                    // Permission Denied
                    //Toast.makeText(activity, "缺少必要权限,请在设置中授予", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    int mSizeX = 300,mSizeY = 300;
    public void setClipSize(int sizeX, int sizeY){
        mSizeX = sizeX;
        mSizeY = sizeY;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO://拍照
                    if (isClip){
                        //去裁剪
                        startPhotoZoom(mImgUri,mSizeX,mSizeY);
                    }else {
                        mXImgSelectCallback.getPicPath(getTakePhotoPath());
                    }
                  break;
                case OPEN_ALBUM://打开相册
                    //判断手机系统的版本信息
                    if (Build.VERSION.SDK_INT>=19) {
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                    if (isClip){
                        //去裁剪
                        startPhotoZoom(data.getData(),mSizeX,mSizeY);
                    }else {
                        mXImgSelectCallback.getPicPath(getTakePhotoPath());
                    }
                    break;
                case CLIP://裁剪过后

                        File file = Utils.upDateFile(getContext(), uriClipUri);
                        mXImgSelectCallback.getPicPath(file.toString());

                    break;
            }
        }
    }


    /**
     * 图片裁剪的方法
     * @param uri
     */
    public Uri uriClipUri;
    public void startPhotoZoom(Uri uri,int sizeX, int sizeY) {
        Log.e("uri=====", "" + uri);
        //com.android.camera.action.CROP，这个action是调用系统自带的图片裁切功能
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//裁剪的图片uri和图片类型
        intent.putExtra("crop", "true");//设置允许裁剪，如果不设置，就会跳过裁剪的过程，还可以设置putExtra("crop", "circle")
        intent.putExtra("aspectX", 1);//裁剪框的 X 方向的比例,需要为整数
        intent.putExtra("aspectY", 1);//裁剪框的 Y 方向的比例,需要为整数
        intent.putExtra("outputX", sizeX);//返回数据的时候的X像素大小。
        intent.putExtra("outputY", sizeY);//返回数据的时候的Y像素大小。
        //uriClipUri为Uri类变量，实例化uriClipUri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0的拍照
            if (mType == 1) {
                //开启临时访问的读和写权限
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //针对7.0以上的操作
                intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
                uriClipUri = uri;
            } else if (mType == 2){//如果是7.0的相册
                //设置裁剪的图片地址Uri
                uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
            }
        } else {
            uriClipUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "clip.jpg");
        }
        Log.e("uriClipUri=====", "" + uriClipUri);
        //Android 对Intent中所包含数据的大小是有限制的，一般不能超过 1M，否则会使用缩略图 ,所以我们要指定输出裁剪的图片路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriClipUri);
        intent.putExtra("return-data", false);//是否将数据保留在Bitmap中返回
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出格式，一般设为Bitmap格式及图片类型
        intent.putExtra("noFaceDetection", true);//人脸识别功能
        if (mActivity != null){
            mActivity.startActivityForResult(intent,CLIP);
        }else {
            mFragment.startActivityForResult(intent,CLIP);
        }
        //startActivityForResult(intent, PHOTO_PHOTOCLIP);//裁剪完成的标识
    }

    private void handleImageBeforeKitKat(Intent data) {//5.0以下的系统
        Uri uri = data.getData();
        String imagePath = getimagePath(uri, null);
        if (!TextUtils.isEmpty(imagePath)){
            mImgPath=imagePath;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {//5.0以上的系统
        String imgPath=null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(),uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imgPath=getimagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imgPath=getimagePath(contentUri,null);

            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imgPath=getimagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imgPath=uri.getPath();
        }
        if (!TextUtils.isEmpty(imgPath)){
            mImgPath=imgPath;
        }
        //displayImage(imgPath);
    }
    private String getimagePath(Uri uri, String seletion) {//获取图片路径的方法
        String path=null;
        Cursor cursor = getContext().getContentResolver().query(uri, null, seletion, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}


