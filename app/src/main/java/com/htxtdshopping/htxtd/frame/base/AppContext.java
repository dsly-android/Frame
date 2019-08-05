package com.htxtdshopping.htxtd.frame.base;

import android.content.Context;

import com.htxtdshopping.htxtd.frame.state.LoggedInState;
import com.htxtdshopping.htxtd.frame.state.LoginState;
import com.htxtdshopping.htxtd.frame.state.NotLoggedInState;

/**
 * @author 陈志鹏
 * @date 2019/1/25
 */
public class AppContext {
    private static AppContext mAppContext;
    private Context mContext;
    private LoginState mState;

    public static void init(Context context) {
        if (mAppContext == null) {
            synchronized (AppContext.class) {
                if (mAppContext == null) {
                    mAppContext = new AppContext(context);
                }
            }
        }
    }

    public static AppContext getInstance() {
        return mAppContext;
    }

    public AppContext(Context context) {
        mContext = context;
        mState = new NotLoggedInState();
    }

    public LoginState getLoginState() {
        return mState;
    }

    public void setLoginState(boolean isLogin) {
        if (isLogin) {
            mState = new LoggedInState();
        } else {
            mState = new NotLoggedInState();
        }
    }

    public boolean isLogin(){
        return mState instanceof LoggedInState;
    }
}