package com.htxtdshopping.htxtd.frame.base;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import dagger.android.AndroidInjection;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setMvpView(IView view) {
        super.setMvpView(view);
        mPresenter.setView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }
}
