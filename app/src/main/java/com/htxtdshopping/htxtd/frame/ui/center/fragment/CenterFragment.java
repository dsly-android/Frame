package com.htxtdshopping.htxtd.frame.ui.center.fragment;


import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseLazyFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends BaseLazyFragment {

    @BindView(R.id.v_bar)
    View mVBar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(mVBar, getResources().getColor(R.color._81D8CF));

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }
}
