package com.htxtdshopping.htxtd.frame.ui.other.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseActivity;
import com.htxtdshopping.htxtd.frame.ui.first.fragment.FirstFragment;
import com.htxtdshopping.htxtd.frame.ui.four.fragment.FourFragment;
import com.htxtdshopping.htxtd.frame.ui.second.fragment.SecondFragment;
import com.htxtdshopping.htxtd.frame.ui.third.fragment.ThirdFragment;
import com.htxtdshopping.htxtd.frame.widget.tablayout.CommonTabLayout;
import com.htxtdshopping.htxtd.frame.widget.tablayout.listener.CustomTabEntity;
import com.htxtdshopping.htxtd.frame.widget.tablayout.listener.OnTabSelectListener;
import com.htxtdshopping.htxtd.frame.widget.tablayout.utils.TabEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * @author chenzhipeng
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.ctl_tab)
    CommonTabLayout mCtlTab;

    private String[] mTitles;
    private int[] mIconUnSelectIds;
    private int[] mIconSelectIds;

    private List<Fragment> mFragments;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourFragment fourFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));

        mTitles = new String[]{"框架", "第三方", "系统", "控件"};
        mIconUnSelectIds = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        mIconSelectIds = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        List<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            tabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mCtlTab.setTabData(tabEntities);

        if (savedInstanceState == null) {
            firstFragment = new FirstFragment();
            secondFragment = new SecondFragment();
            thirdFragment = new ThirdFragment();
            fourFragment = new FourFragment();
            mFragments = new ArrayList<>();
            mFragments.add(firstFragment);
            mFragments.add(secondFragment);
            mFragments.add(thirdFragment);
            mFragments.add(fourFragment);
            FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fl_container, 0);
        } else {
            firstFragment = (FirstFragment) FragmentUtils.findFragment(getSupportFragmentManager(), FirstFragment.class);
            secondFragment = (SecondFragment) FragmentUtils.findFragment(getSupportFragmentManager(), SecondFragment.class);
            thirdFragment = (ThirdFragment) FragmentUtils.findFragment(getSupportFragmentManager(), ThirdFragment.class);
            fourFragment = (FourFragment) FragmentUtils.findFragment(getSupportFragmentManager(), FourFragment.class);
            mFragments = new ArrayList<>();
            mFragments.add(firstFragment);
            mFragments.add(secondFragment);
            mFragments.add(thirdFragment);
            mFragments.add(fourFragment);
        }
    }

    @Override
    public void initEvent() {
        mCtlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                FragmentUtils.showHide(position, mFragments);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void initData() {
        
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}