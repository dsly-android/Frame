package com.htxtdshopping.htxtd.frame.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dsly.rxhttp.IView;
import com.trello.rxlifecycle3.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 陈志鹏
 * @date 2018/7/29
 */
public abstract class BaseFragment extends RxFragment implements ILifeCycle, IView {

    private Unbinder mUnbinder;
    private long lastClick = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().registerSticky(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMvpView(this);
        initView(savedInstanceState);
        initEvent();
        if (!isLazy()) {
            initData();
        }
    }

    @Override
    public void setMvpView(IView view) {

    }

    /**
     * 是否使用懒加载
     *
     * @return
     */
    protected boolean isLazy() {
        return false;
    }

    @Override
    public void showLoading() {
        ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void killMyself() {
        getActivity().finish();
    }

    private boolean isFastClick() {
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

    public boolean isFragmentVisible() {
        return !isHidden() && getUserVisibleHint();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
