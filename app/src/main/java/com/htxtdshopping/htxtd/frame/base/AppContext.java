package com.htxtdshopping.htxtd.frame.base;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.Utils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.constant.Constants;
import com.htxtdshopping.htxtd.frame.event.VersionUpdateEvent;
import com.htxtdshopping.htxtd.frame.network.ServerApi;
import com.htxtdshopping.htxtd.frame.notification.NotificationChannels;
import com.htxtdshopping.htxtd.frame.state.LoggedInState;
import com.htxtdshopping.htxtd.frame.state.LoginState;
import com.htxtdshopping.htxtd.frame.state.NotLoggedInState;
import com.htxtdshopping.htxtd.frame.ui.third.activity.UpgradeActivity;
import com.htxtdshopping.htxtd.frame.utils.NotificationUtils;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;

import androidx.annotation.NonNull;

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
     *
     * @param isShowToast
     */
    public void checkUpdate(boolean isShowToast) {
        VersionUpdateEvent event = ServerApi.versionUpdate();
        int versionCode = event.getVersionCode();
        String versionName = event.getVersionName();
        String apkUrl = event.getApkUrl();
        boolean force = event.isForce();
        String description = event.getDescription();
        if (versionCode > AppUtils.getAppVersionCode()) {
            Intent intent = new Intent(Utils.getApp(), UpgradeActivity.class);
            intent.putExtra(UpgradeActivity.VERSIONCODE, versionCode);
            intent.putExtra(UpgradeActivity.VERSIONNAME, versionName);
            intent.putExtra(UpgradeActivity.APKURL, apkUrl);
            intent.putExtra(UpgradeActivity.ISFORCE, force);
            intent.putExtra(UpgradeActivity.DESCRIPTION, description);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        } else {
            if (isShowToast) {
                ToastUtils.showLong(Utils.getApp().getString(R.string.latest_version));
            }
        }
    }

    /**
     * 版本更新的监听器
     */
    public DownloadListener3 getUpgradeListener() {
        return new DownloadListener3() {
            @Override
            protected void started(@NonNull DownloadTask task) {

            }

            @Override
            protected void completed(@NonNull DownloadTask task) {
                Intent intent = IntentUtils.getInstallAppIntent(task.getFile().getAbsoluteFile(),true);
                NotificationUtils.createSimpleNotification(Constants.NOTIFICATION_UPGRADE, true, NotificationChannels.CHANNEL_HIGH,
                        intent, R.mipmap.ic_launcher, Utils.getApp().getString(R.string.app_name), "下载完成");
            }

            @Override
            protected void canceled(@NonNull DownloadTask task) {
                NotificationUtils.createSimpleNotification(Constants.NOTIFICATION_UPGRADE, true, NotificationChannels.CHANNEL_HIGH,
                        null, R.mipmap.ic_launcher, Utils.getApp().getString(R.string.app_name), "取消更新");
            }

            @Override
            protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
                NotificationUtils.createSimpleNotification(Constants.NOTIFICATION_UPGRADE, true, NotificationChannels.CHANNEL_HIGH,
                        null, R.mipmap.ic_launcher, Utils.getApp().getString(R.string.app_name), "下载错误");
            }

            @Override
            protected void warn(@NonNull DownloadTask task) {

            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                NotificationUtils.createProgressNotification(Constants.NOTIFICATION_UPGRADE, true, NotificationChannels.CHANNEL_HIGH,
                        (int) (currentOffset * 100 / totalLength), R.mipmap.ic_launcher, Utils.getApp().getString(R.string.app_name), "");
            }
        };
    }
}