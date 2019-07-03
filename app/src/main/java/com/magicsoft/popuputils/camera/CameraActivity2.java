package com.magicsoft.popuputils.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.magicsoft.mylibrary.PhotoUtils2;
import com.magicsoft.popuputils.R;

/**
 * @author : Lss kiwilss
 * @FileName: CameraActivity2
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-02
 * @desc : {DESCRIPTION}
 */
public class CameraActivity2 extends AppCompatActivity {

    private PhotoUtils2 mPhotoUtils2;
    private ImageView mIvPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        mIvPic = (ImageView) findViewById(R.id.iv_camera_pic);
        mPhotoUtils2 = new PhotoUtils2(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPhotoUtils2.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = mPhotoUtils2.onActivityResult(requestCode,resultCode,data);
        mIvPic.setImageBitmap(bitmap);
    }

    public void takePhoto(View view) {
        mPhotoUtils2.takePhoto();
    }

    public void openalbum(View view) {
        mPhotoUtils2.openAlbum();
    }
}
