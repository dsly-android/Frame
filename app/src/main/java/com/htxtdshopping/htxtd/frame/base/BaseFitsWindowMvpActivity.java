package com.htxtdshopping.htxtd.frame.base;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
public abstract class BaseFitsWindowMvpActivity<P extends BasePresenter> extends BaseMvpActivity<P> {

    @Override
    protected boolean isFitWindow() {
        return true;
    }
}
