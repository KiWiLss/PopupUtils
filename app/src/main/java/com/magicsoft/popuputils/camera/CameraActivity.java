package com.magicsoft.popuputils.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kiwilss.nodimen.PhotoUtils;
import com.magicsoft.popuputils.R;
import com.magicsoft.popuputils.utils.GifSizeFilter;
import com.magicsoft.popuputils.utils.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.MediaStoreCompat;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.File;
import java.util.List;

/**
 * @author : Lss kiwilss
 * @FileName: CameraActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-01
 * @desc : {DESCRIPTION}拍照,打开相册仿知乎,不带权限,没有裁剪功能
 */
public class CameraActivity extends AppCompatActivity {

    private PhotoUtils mPhotoUtils;
    private ImageView mIvPic;
    private String mCurrentPhotoPath;
    private MediaStoreCompat mMediaStoreCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mIvPic = (ImageView) findViewById(R.id.iv_camera_pic);
        mPhotoUtils = new PhotoUtils(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPhotoUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void PhotoOneListener(View view) {
        mPhotoUtils.takePhoto();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String result = mPhotoUtils.onActivityResult(requestCode, resultCode, data);
//        Log.e("MMM", "onActivityResult: "+result);
        if (requestCode == 199){
            Uri contentUri = mMediaStoreCompat.getCurrentPhotoUri();
            String path = mMediaStoreCompat.getCurrentPhotoPath();
            Log.e("MMM", "onActivityResult: "+path );
        }
        if (requestCode == 22){
            assert data != null;
            //boolean b = Matisse.obtainOriginalState(data);
            List<String> list = Matisse.obtainPathResult(data);
            Log.e("MMM ", list.get(0));
        }
    }

    public void compusePic(View view) {
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode);
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.code);
//        Bitmap bp = Utils.mergeBp(bitmap1, bitmap2);
//        mIvPic.setImageBitmap(bp);
      takePhotoNoCompress();

    }

    public void takePhotoNoCompress() {

        mMediaStoreCompat = new MediaStoreCompat(this);
        mMediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true,
                getPackageName() + ".android7.fileprovider","test"));
        mMediaStoreCompat.dispatchCaptureIntent(this,199);


//        Log.e("MMM", "takePhotoNoCompress: "+BuildConfig.APPLICATION_ID + ".fileprovider");
//        Log.e("MMM", "takePhotoNoCompress: ---"+ getPackageName() + ".fileprovider" );
//            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
//                    .format(new Date()) + ".png";
//
//            File file = new File(Environment.getExternalStorageDirectory() , filename);
//            mCurrentPhotoPath = file.getAbsolutePath();


//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//
//            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
//                    .format(new Date()) + ".png";
//
//            File file = new File(Environment.getExternalStorageDirectory() , filename);
//            mCurrentPhotoPath = file.getAbsolutePath();
//
//            Log.e("MMM", "takePhotoNoCompress: file==" + file +"=====" + mCurrentPhotoPath);
//
//            //Uri fileUri = FileProvider7.getUriForFile(this, file);
//            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
//
//            //Uri fileUri = getUriForFile24(this, file);
//
//            List<ResolveInfo> resInfoList = getPackageManager()
//                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
//            for (ResolveInfo resolveInfo : resInfoList) {
//                String packageName = resolveInfo.activityInfo.packageName;
//                grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            }
//
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//            startActivityForResult(takePictureIntent, 199);
//        }
    }

    public static Uri getUriForFile24(Context context, File file) {
        return android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName() + ".android7.fileprovider",
                file);
    }

    public void albumListener(View view) {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy
                        (true, getPackageName()+".android7.fileprovider",
                                "test"))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(22);
    }

    public void album2Listener(View view) {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                //.capture(true)
                .captureStrategy(new CaptureStrategy
                        (true, getPackageName()+".android7.fileprovider",
                                "test"))
                .maxSelectable(1)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(22);
    }
}
