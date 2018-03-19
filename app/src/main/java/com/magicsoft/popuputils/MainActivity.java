package com.magicsoft.popuputils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.magicsoft.mylibrary.PopupWindowUtils;
import com.magicsoft.mylibrary.PwChoiceHeader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void oneTitleOneBtn(View view) {
        PwChoiceHeader pwChoiceHeader = new PwChoiceHeader(this, new PwChoiceHeader.ChoiceHeader() {
            @Override
            public void takePhoto(PwChoiceHeader pwChoiceHeader) {
                Toast.makeText(MainActivity.this, "takephoto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void openAlbum(PwChoiceHeader pwChoiceHeader) {
                Toast.makeText(MainActivity.this, "openalbum", Toast.LENGTH_SHORT).show();
            }
        });
        pwChoiceHeader.setAnimationStyle(R.style.PushInBottom);
        pwChoiceHeader.showBottom(this);
//        new PopupOneBtn(this, new PopupOneBtn.BtnClick() {
//            @Override
//            public void sureClickListener(PopupOneBtn popupOneBtn) {
//                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
//            }
//        }).showCenter(this);
    }

    public void oneTitleOneBtnModify(View view) {
//        PopupOneBtn pob = new PopupOneBtn(this, new PopupOneBtn.BtnClick() {
//            @Override
//            public void sureClickListener(PopupOneBtn popupOneBtn) {
//                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
//            }
//        });
//        pob.setTitleAndColor("任意标题",0,false)
//                .setSureTextAndColor("任意文字",
//                        R.color.colorPrimary,R.color.colorAccent)
//                .setRadius(2)//设置圆角
//                .showCenter(this);
    }

    public void menu(View view) {
//        PopupOneUtils popupOneUtils = new PopupOneUtils(this, new PopupOneUtils.ContentClickListener() {
//            @Override
//            public void sureClickListener(PopupOneUtils popupOneUtils) {
//
//            }
//
//            @Override
//            public void cancelClickListener() {
//
//            }
//        });
//        popupOneUtils.setAnimationStyle(R.style.PopDownLeftMenu);
//        popupOneUtils.showAsDropDown(view);
        //popupOneUtils.showAsDropDown(view,0,0);
    }

    public void leftMenu(View view) {
        PopupWindowUtils popupWindowUtils = new PopupWindowUtils(view);
        popupWindowUtils.setContentView(R.layout.pw_menu);
        popupWindowUtils.showLikePopDownLeftMenu();
    }
}
