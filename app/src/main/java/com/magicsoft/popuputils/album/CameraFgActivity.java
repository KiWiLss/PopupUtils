package com.magicsoft.popuputils.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.magicsoft.popuputils.R;

/**
 * @author : Lss kiwilss
 * @FileName: CameraFgActivity
 * @e-mail : kiwilss@163.com
 * @time : 2019-07-03
 * @desc : {DESCRIPTION}
 */
public class CameraFgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_fg);

        FrameLayout fl = (FrameLayout) findViewById(R.id.fl_camera_fg_fl);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_camera_fg_fl,new CamearFg())
                .commit();

    }
}
