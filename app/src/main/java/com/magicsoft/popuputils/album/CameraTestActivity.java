package com.magicsoft.popuputils.album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kiwilss.lxkj.ximageselect.XImgSelect;
import com.kiwilss.lxkj.ximageselect.callback.XImgSelectCallback;
import com.magicsoft.popuputils.R;

/**
 * @author : Lss kiwilss
 * @FileName: CameraTestActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-02
 * @desc : {DESCRIPTION}
 */
public class CameraTestActivity extends AppCompatActivity {

    public static final String TAG = "MMM";
    private XImgSelect mXImgSelect;
    private ImageView mIvPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        mIvPic = (ImageView) findViewById(R.id.iv_camera_test_pic);
        mXImgSelect = new XImgSelect(this, false, new XImgSelectCallback() {

            @Override
            public void noCamerPermission(boolean isShow) {
                if (isShow){
                    Toast.makeText(CameraTestActivity.this, "请设置权限", Toast.LENGTH_SHORT).show();
                }else {//点了不再询问
                    Toast.makeText(CameraTestActivity.this, "在设置中授予权限", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void getPicPath(String path) {
                Log.e(TAG, "getPicPath: "+path);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mIvPic.setImageBitmap(bitmap);
                //BitmapUtils.saveBpImageToGallery(CameraTestActivity.this,bitmap);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mXImgSelect.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果裁剪图片,在获取结果前设置图片尺寸
        //mXImgSelect.setClipSize(300,300);
        mXImgSelect.onActivityResult(requestCode,resultCode,data);
        Log.e("MMM", "onActivityResult: "+ requestCode+"--"+data);
        //mIvPic.setImageURI(mXImgSelect.uriClipUri);
    }

    public void takePhotoListener(View view) {
        mXImgSelect.takePhoto();
    }

    public void albumListener(View view) {
        mXImgSelect.openAlbum();
    }
}
