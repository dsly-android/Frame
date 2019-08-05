package com.htxtdshopping.htxtd.frame.di.module;

import com.htxtdshopping.htxtd.frame.di.scope.ActivityScope;
import com.htxtdshopping.htxtd.frame.di.scope.FragmentScope;
import com.htxtdshopping.htxtd.frame.ui.first.fragment.FirstFragment;
import com.htxtdshopping.htxtd.frame.ui.other.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
@Module
public abstract class BuildersModule {

    @ActivityScope
    @ContributesAndroidInjector
    public abstract MainActivity mainActivityInject();

    @FragmentScope
    @ContributesAndroidInjector
    public abstract FirstFragment firstFragmentInject();
}
