package com.htxtdshopping.htxtd.frame.base;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.event.VersionUpdateEvent;
import com.htxtdshopping.htxtd.frame.network.ServerApi;
import com.htxtdshopping.htxtd.frame.state.LoggedInState;
import com.htxtdshopping.htxtd.frame.state.LoginState;
import com.htxtdshopping.htxtd.frame.state.NotLoggedInState;
import com.htxtdshopping.htxtd.frame.ui.third.activity.UpgradeActivity;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;

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

    public boolean isLogin() {
        return mState instanceof LoggedInState;
    }

    /**
     * 版本更新
     * @param isShowToast
     */
    public void checkUpdate(boolean isShowToast) {
        VersionUpdateEvent event = ServerApi.versionUpdate();
        int versionCode = event.getVersionCode();
        String versionName = event.getVersionName();
        String apkUrl = event.getApkUrl();
        boolean force = event.isForce();
        if (versionCode > AppUtils.getAppVersionCode()) {
            Intent intent = new Intent(Utils.getApp(), UpgradeActivity.class);
            intent.putExtra(UpgradeActivity.VERSIONCODE, versionCode);
            intent.putExtra(UpgradeActivity.VERSIONNAME,versionName);
            intent.putExtra(UpgradeActivity.APKURL, apkUrl);
            intent.putExtra(UpgradeActivity.ISFORCE, force);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        } else {
            if (isShowToast) {
                ToastUtils.showLong(Utils.getApp().getString(R.string.latest_version));
            }
        }
    }
}