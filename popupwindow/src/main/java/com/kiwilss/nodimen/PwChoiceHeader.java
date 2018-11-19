package com.kiwilss.nodimen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * FileName: PwChoiceHeader
 *
 * @author : Lss kiwilss
 *         e-mail : kiwilss@163.com
 *         time   : 2018/3/19
 *         desc   : ${DESCRIPTION}
 *         Description: ${DESCRIPTION}
 */

public class PwChoiceHeader extends BottomPushPopupWindow<PwChoiceHeader.ChoiceHeader>{


    private View mContentView;

    public PwChoiceHeader(Context context, PwChoiceHeader.ChoiceHeader integer) {
        super(context, integer);
    }

    @Override
    protected View generateCustomView(final PwChoiceHeader.ChoiceHeader choiceHeader) {
        //引入布局
        mContentView = LayoutInflater.from(context).inflate(R.layout.normal_modify_header, null);
        //获取内部控件,设置点击事件
        //取消的监听
        mContentView.findViewById(R.id.tv_pw_modify_header_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PwChoiceHeader.this.dismiss();
            }
        });
        //拍照的监听
        mContentView.findViewById(R.id.tv_pw_modify_header_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceHeader.takePhoto(PwChoiceHeader.this);
            }
        });
        //选择相册的监听
        mContentView.findViewById(R.id.tv_pw_modify_header_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceHeader.openAlbum(PwChoiceHeader.this);
            }
        });

        return mContentView;
    }

    public <T extends View>T getView(int id){
        return mContentView.findViewById(id);
    }

    public interface ChoiceHeader{
        void takePhoto(PwChoiceHeader pwChoiceHeader);
        void openAlbum(PwChoiceHeader pwChoiceHeader);
    }
}
