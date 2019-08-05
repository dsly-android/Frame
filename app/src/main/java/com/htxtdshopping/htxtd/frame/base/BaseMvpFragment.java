package com.htxtdshopping.htxtd.frame.base;

import android.app.Activity;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment{

    @Inject
    protected P mPresenter;

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @Override
    public void setMvpView(IView view) {
        super.setMvpView(view);
        mPresenter.setView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }
}
