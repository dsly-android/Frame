package com.htxtdshopping.htxtd.frame.di.component;

import com.htxtdshopping.htxtd.frame.base.App;
import com.htxtdshopping.htxtd.frame.di.module.AppModule;
import com.htxtdshopping.htxtd.frame.di.module.BuildersModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class})
public interface AppComponent extends AndroidInjector<App> {

}