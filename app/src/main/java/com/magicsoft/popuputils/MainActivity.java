package com.magicsoft.popuputils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.magicsoft.mylibrary.PopupUtils;
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
       //pwChoiceHeader.showDrop(view,0,0);
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

    /**带阴影的菜单样式
     * @param view
     */
    public void menu(View view) {
//        PopupOOBtn popupOOBtn = new PopupOOBtn(this, new PopupOOBtn.BtnClick() {
//            @Override
//            public void sureClickListener(PopupOOBtn pob) {
//
//            }
//        });
//
//        popupOOBtn.setAnimationStyle(R.style.PopDownLeftMenu);
//        popupOOBtn.showAsDropDown(view);
        //popupOOBtn.showAsDropDown(view,0,0);

        PopupUtils popupUtils = new PopupUtils(this, R.layout.pw_menu);
        popupUtils.showAsDropDown(view);
    }

    /**背景无阴影样式菜单
     * @param view
     */
    public void leftMenu(View view) {
        PopupWindowUtils popupWindowUtils = new PopupWindowUtils(view);
        popupWindowUtils.setContentView(R.layout.pw_menu1);
        popupWindowUtils.showLikePopDownLeftMenu();
    }
    /**背景无阴影样式菜单
     * @param view
     */
    public void bottomMenu(View view) {
        PopupWindowUtils popupWindowUtils = new PopupWindowUtils(view);
        popupWindowUtils.setContentView(R.layout.pw_bottom_menu);
        //popupWindowUtils.showCenter();
        popupWindowUtils.showLikePopDownLeftMenu();
    }
}
