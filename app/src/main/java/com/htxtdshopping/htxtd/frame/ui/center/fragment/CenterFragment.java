package com.htxtdshopping.htxtd.frame.ui.center.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseLazyFragment;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeConfig;

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

    @OnClick({R.id.btn_test})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btn_test:
                float initScaledDensity = AutoSizeConfig.getInstance().getInitScaledDensity();
                float initDensity = AutoSizeConfig.getInstance().getInitDensity();
                Log.e("aaa",initScaledDensity+"   "+initDensity);
                break;
                default:
                    break;
        }
    }
}
