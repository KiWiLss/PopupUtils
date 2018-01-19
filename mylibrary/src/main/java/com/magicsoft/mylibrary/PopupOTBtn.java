package com.magicsoft.mylibrary;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kiwi on 2017/6/5.
 * Email:2763015920@qq.com
 */

public class PopupOTBtn extends BottomPushPopupWindow<PopupOTBtn.ContentClickListener> {
    public PopupOTBtn(Context context, ContentClickListener contentClickListener) {
        super(context, contentClickListener);
    }
    TextView tvTitle;
    TextView tvSure;
    TextView tvCancel;
    CardView cvCv;

    /**设置圆角弧度
     * @param multiple 倍数
     */
    public PopupOTBtn setRadius(int multiple){
        float dimension1 = context.getResources().getDimension(R.dimen.m5);
        if (cvCv!=null){
            cvCv.setRadius(dimension1*multiple);
        }
        return this;
    }
    /**设置标题内容,字体颜色和是否加粗
     * @param title
     * @param color
     * @param isBold
     */
    public PopupOTBtn setTitleAndColor(String title, int color, boolean isBold){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        //设置是否加粗
        tvTitle.setTypeface(isBold? Typeface.defaultFromStyle(Typeface.BOLD):Typeface.defaultFromStyle(Typeface.NORMAL));
        if (color!=0){
            tvTitle.setTextColor(ContextCompat.getColor(context, color));
        }
        return this;
    }
    public PopupOTBtn setSureTextAndColor(String text, int textColor, int backColor){
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
    public PopupOTBtn setCancelTextAndColor(String text, int textColor, int backColor){
        if (!TextUtils.isEmpty(text)){
            tvCancel.setText(text);
        }
        if (textColor!=0){
            tvCancel.setTextColor(ContextCompat.getColor(context, textColor));
        }
        if (backColor!=0){
            tvCancel.setBackgroundColor(ContextCompat.getColor(context, backColor));
        }
        return this;
    }

    @Override
    protected View generateCustomView(final ContentClickListener contentClickListener) {
        View contentView = View.inflate(context, R.layout.overall_pw_one2, null);
         tvTitle = (TextView) contentView.findViewById(R.id.tv_pw_onetitle_title);
         tvSure = (TextView) contentView.findViewById(R.id.tv_pw_onetitle_sure);
         tvCancel = (TextView) contentView.findViewById(R.id.tv_pw_onetitle_cancel);
         cvCv = (CardView) contentView.findViewById(R.id.cv_pw_onetitle_cv);
        //点击监听
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentClickListener.cancelClickListener();
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentClickListener.sureClickListener(PopupOTBtn.this);
            }
        });
        return contentView;
    }



    public interface ContentClickListener{
        void sureClickListener(PopupOTBtn pou);
        void cancelClickListener();
    }
}
