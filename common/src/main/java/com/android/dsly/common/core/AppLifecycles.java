package com.android.dsly.common.core;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * ================================================
 * 用于代理 {@link Application} 的生命周期
 *
 * @see AppDelegate
 * Created by JessYan on 18/07/2017 17:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface AppLifecycles {
    void attachBaseContext(@NonNull Application application);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
