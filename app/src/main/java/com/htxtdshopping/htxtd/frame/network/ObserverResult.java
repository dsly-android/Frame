package com.htxtdshopping.htxtd.frame.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 陈志鹏
 * @date 2019/1/17
 */
public abstract class ObserverResult<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        /*try {
            MessageBean bean = JSON.parseObject(e.getMessage(), MessageBean.class);
            onError(bean.getCode(), bean.getContent());
        } catch (Exception e1) {
            onError(0, e.getMessage());
        }*/
    }

    protected void onError(int code, String content) {

    }

    @Override
    public void onComplete() {

    }
}