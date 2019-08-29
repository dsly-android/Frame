package com.htxtdshopping.htxtd.frame.di.module;

import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    @Nullable
    public String aaa(){
        return "";
    }
}
