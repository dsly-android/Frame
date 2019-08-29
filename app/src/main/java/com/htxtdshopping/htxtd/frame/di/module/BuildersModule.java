package com.htxtdshopping.htxtd.frame.di.module;

import com.htxtdshopping.htxtd.frame.di.scope.ActivityScope;
import com.htxtdshopping.htxtd.frame.di.scope.FragmentScope;
import com.htxtdshopping.htxtd.frame.ui.first.activity.RefreshAndLoadMoreActivity;
import com.htxtdshopping.htxtd.frame.ui.first.fragment.FirstFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 陈志鹏
 * @date 2019-08-04
 */
@Module
public abstract class BuildersModule {

    @FragmentScope
    @ContributesAndroidInjector
    public abstract FirstFragment firstFragmentInject();

    @ActivityScope
    @ContributesAndroidInjector
    public abstract RefreshAndLoadMoreActivity refreshAndLoadMoreActivityInject();
}
