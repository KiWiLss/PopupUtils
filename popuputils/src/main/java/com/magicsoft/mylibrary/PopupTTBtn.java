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

public class PopupTTBtn extends BottomPushPopupWindow<PopupTTBtn.ContentTClickListener>{


    private TextView tvHint;
    private TextView tvCancel;
    private TextView tvSure;
    private TextView tvTitle;
    private CardView cvCv;

    public PopupTTBtn(Context context, ContentTClickListener contentTClickListener) {
        super(context, contentTClickListener);
    }

    /**设置圆角
     * @param multiple
     */
    public PopupTTBtn setRadius(int multiple){
        float dimension1 = context.getResources().getDimension(R.dimen.m5);
        if (cvCv!=null){
            cvCv.setRadius(dimension1*multiple);
        }
        return this;
    }

    //设置提示
    public PopupTTBtn setHint(String hint, int textColor, boolean isBold){
        if (!TextUtils.isEmpty(hint)){
            tvHint.setText(hint);
        }
        //设置是否加粗
        tvHint.setTypeface(isBold? Typeface.defaultFromStyle(Typeface.BOLD):Typeface.defaultFromStyle(Typeface.NORMAL));
        if (textColor!=0){
            tvHint.setTextColor(ContextCompat.getColor(context, textColor));
        }
        return this;
    }
    //设置标题
    public PopupTTBtn setTitle(String title, int textColor, boolean isBold){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        //设置是否加粗
        tvTitle.setTypeface(isBold? Typeface.defaultFromStyle(Typeface.BOLD):Typeface.defaultFromStyle(Typeface.NORMAL));
        if (textColor!=0){
            tvTitle.setTextColor(ContextCompat.getColor(context, textColor));
        }
        return this;
    }
    public PopupTTBtn setSureTextAndColor(String text, int textColor, int backColor){
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
    public PopupTTBtn setCancelTextAndColor(String text, int textColor, int backColor){
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

//    public PopupTwoUtils(Context context, ContentTClickListener contentClickListener) {
//        super(context,  contentClickListener);
//    }

    @Override
    protected View generateCustomView(final ContentTClickListener contentClickListener) {

        View contentView = View.inflate(context, R.layout.overall_pw_two2, null);
        tvHint = (TextView) contentView.findViewById(R.id.tv_pw_twotitle_titleOne);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_pw_twotitle_titleTwo);
        tvSure = (TextView) contentView.findViewById(R.id.tv_pw_twotitle_sure);
        tvCancel = (TextView) contentView.findViewById(R.id.tv_pw_twotitle_cancel);
        cvCv = (CardView) contentView.findViewById(R.id.cv_pw_twotitle_cv);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                contentClickListener.cancelClickListener();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentClickListener.sureClickListener(PopupTTBtn.this);
            }
        });
        return contentView;
    }


    public interface ContentTClickListener{
        void sureClickListener(PopupTTBtn ptu);
        void cancelClickListener();
    }
}
