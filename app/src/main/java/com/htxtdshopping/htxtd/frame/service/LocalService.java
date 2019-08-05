package com.htxtdshopping.htxtd.frame.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.htxtdshopping.htxtd.frame.notification.Notifications;

import androidx.annotation.Nullable;

/**
 * @author 陈志鹏
 * @date 2018/12/25
 */
public class LocalService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
//        8.0开启前台服务
        Notifications.getInstance().showForegroundNotification(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
