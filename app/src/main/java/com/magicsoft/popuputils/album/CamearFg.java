package com.magicsoft.popuputils.album;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kiwilss.lxkj.ximageselect.XImgSelect;
import com.kiwilss.lxkj.ximageselect.callback.XImgSelectCallback;
import com.magicsoft.popuputils.R;

/**
 * @author : Lss kiwilss
 * @FileName: CamearFg
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-03
 * @desc : {DESCRIPTION}
 */
public class CamearFg extends Fragment {

    private XImgSelect mXImgSelect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_camera, container, false);
        final ImageView iv = view.findViewById(R.id.iv_fg_pic);
        mXImgSelect = new XImgSelect(this, false, new XImgSelectCallback() {


            @Override
            public void noCamerPermission(boolean isShow) {
                if (isShow){
                    Toast.makeText(getContext(), "请设置权限", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "在设置中授予权限", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void getPicPath(String path) {
                Log.e("MMM", "getPicPath: "+ path );
               iv.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        });

        view.findViewById(R.id.btn_fg_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXImgSelect.takePhoto();
            }
        });

        view.findViewById(R.id.btn_fg_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXImgSelect.openAlbum();
            }
        });



        //requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mXImgSelect.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == 100){
            Log.e("MMM", "onRequestPermissionsResult: " );
        }
        Log.e("MMM", "onRequestPermissionsResult: ----" );

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mXImgSelect.onActivityResult(requestCode,resultCode,data);
    }
}
