package com.htxtdshopping.htxtd.frame.ui.four.activity;

import android.os.Bundle;

import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseActivity;
import com.htxtdshopping.htxtd.frame.view.CommonTextView;

import butterknife.BindView;

/**
 * @author chenzhipeng
 */
public class CommonTextViewActivity extends BaseActivity {

    @BindView(R.id.ctv_test1)
    CommonTextView mCtvTest1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_text_view;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}