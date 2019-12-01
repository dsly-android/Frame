package com.htxtdshopping.htxtd.frame.lifecycle;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;


/**
 * @author 陈志鹏
 * @date 2019-08-09
 */
public class TestObserver implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtils.e("onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        LogUtils.e("onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        LogUtils.e("onResume");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        LogUtils.e("onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        LogUtils.e("onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        LogUtils.e("onDestroy");
    }
}