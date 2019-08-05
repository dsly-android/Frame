package com.htxtdshopping.htxtd.frame.base;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

import java.lang.reflect.Field;

/**
 * @author 陈志鹏
 * @date 2018/11/27
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected Context mContext;
    protected View mRootView;

    public BasePopupWindow(Context context, int width, int height) {
        super(width, height);
        mContext = context;
        if (getLayoutId() != 0) {
            mRootView = View.inflate(context, getLayoutId(), null);
            setContentView(mRootView);
        }
        //解决边框距离屏幕四周有间距的问题
        setBackgroundDrawable(null);
    }

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 使PopupWindow能覆盖住状态栏
     */
    public void fitPopupWindowOverStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(this, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
