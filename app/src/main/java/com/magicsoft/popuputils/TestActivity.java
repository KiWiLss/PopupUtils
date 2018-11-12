package com.magicsoft.popuputils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kiwilss.nodimen.PopupOTBtn;


/**
 * FileName: TestActivity
 *
 * @author : Lss kiwilss
 * e-mail : kiwilss@163.com
 * time   : 2018/11/12
 * desc   : ${DESCRIPTION}
 * Description: ${DESCRIPTION}
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void bottomMenu(View view) {

        new PopupOTBtn(this, new PopupOTBtn.ContentClickListener() {
            @Override
            public void sureClickListener(PopupOTBtn pou) {

            }

            @Override
            public void cancelClickListener() {

            }
        }).setRadius(6)
                .showCenter(this);

    }

    public void leftMenu(View view) {
        new PopupOTBtn(this, new PopupOTBtn.ContentClickListener() {
            @Override
            public void sureClickListener(PopupOTBtn pou) {

            }

            @Override
            public void cancelClickListener() {

            }
        })
                .showCenter(this);
    }
}
