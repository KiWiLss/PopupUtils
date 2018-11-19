package com.magicsoft.mylibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 刘少帅 on 2017/10/25
 */

public class PopupTOBtn extends BottomPushPopupWindow <PopupTOBtn.BtnClick>{


    private CardView cvCv;
    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvSure;

    /**设置圆角
     * @param multiple
     */
    public PopupTOBtn setRadius(int multiple){
        float dimension1 = context.getResources().getDimension(R.dimen.m5);
        if (cvCv!=null){
            cvCv.setRadius(dimension1*multiple);
        }
        return this;
    }

    //设置提示
    public PopupTOBtn setHint(String hint, int textColor, boolean isBold){
        if (!TextUtils.isEmpty(hint)){
            tvOne.setText(hint);
        }
        //设置是否加粗
        tvOne.setTypeface(isBold? Typeface.defaultFromStyle(Typeface.BOLD):Typeface.defaultFromStyle(Typeface.NORMAL));
        if (textColor!=0){
            tvOne.setTextColor(ContextCompat.getColor(context, textColor));
        }
        return this;
    }
    //设置标题
    public PopupTOBtn setTitle(String title, int textColor, boolean isBold){
        if (!TextUtils.isEmpty(title)){
            tvTwo.setText(title);
        }
        //设置是否加粗
        tvTwo.setTypeface(isBold? Typeface.defaultFromStyle(Typeface.BOLD):Typeface.defaultFromStyle(Typeface.NORMAL));
        if (textColor!=0){
            tvTwo.setTextColor(ContextCompat.getColor(context, textColor));
        }
        return this;
    }
    public PopupTOBtn setSureTextAndColor(String text, int textColor, int backColor){
        if (!TextUtils.isEmpty(text)){
            tvSure.setText(text);
        }
        if (textColor!=0){
            tvSure.setTextColor(ContextCompat.getColor(context, textColor));
        }
        if (backColor!=0){
            tvSure.setBackgroundColor(ContextCompat.getColor(context, backColor));
        }
        return this;
    }



    public PopupTOBtn(Context context, PopupTOBtn.BtnClick btnClick) {
        super(context, btnClick);
    }

    @Override
    protected View generateCustomView(final PopupTOBtn.BtnClick btnClick) {
        View contentView = View.inflate(context, R.layout.overall_pw_two_btn, null);
        cvCv = (CardView) contentView.findViewById(R.id.cv_pw_twobtn_cv);
        tvOne = (TextView) contentView.findViewById(R.id.tv_pw_twobtn_titleOne);
        tvTwo = (TextView) contentView.findViewById(R.id.tv_pw_twobtn_titleTwo);

        tvSure = (TextView) contentView.findViewById(R.id.tv_pw_twobtn_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick.sureClickListener(PopupTOBtn.this);
            }
        });
        return contentView;
    }

    public interface BtnClick{
        void sureClickListener(PopupTOBtn ptb);
    }
}
