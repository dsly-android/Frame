package com.htxtdshopping.htxtd.frame.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.android.dsly.rxhttp.IView;
import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.dialog.LoadingDialog;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 陈志鹏
 * @date 2018/7/29
 */
public abstract class BaseActivity extends RxAppCompatActivity implements ILifeCycle, IView {

    private Unbinder mUnbinder;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            mUnbinder = ButterKnife.bind(this);
        }
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color._81D8CF));
        if (isFitWindow()) {
            setFitsSystemWindows(true);
        }
        findViewById(android.R.id.content).setBackgroundResource(R.color._f3f3f3);

        setMvpView(this);
        initView(savedInstanceState);
        initEvent();
        initData();
    }

    @Override
    public void setMvpView(IView view) {

    }

    protected boolean isFitWindow() {
        return false;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    public void setFitsSystemWindows(boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        ViewGroup parent = findViewById(android.R.id.content);
        View childView = parent.getChildAt(0);
        if (childView != null && childView instanceof ViewGroup) {
            childView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    public boolean isNotFastClick() {
        return !isFastClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }
}
