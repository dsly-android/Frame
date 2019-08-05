package com.htxtdshopping.htxtd.frame.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseDialog;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * @author 陈志鹏
 * @date 2018/10/25
 */
public class LoadingDialog extends BaseDialog {

    @BindView(R.id.iv_loading)
    ImageView mIvLoading;
    private ProgressDrawable mProgressDrawable;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.AppTheme_Translucent_Dialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(mContext.getResources().getColor(android.R.color.white));
        mIvLoading.setImageDrawable(mProgressDrawable);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
        mProgressDrawable.stop();
    }

    @Override
    public void show() {
        super.showDefault();
        mProgressDrawable.start();
    }
}
