package com.htxtdshopping.htxtd.frame.ui.other.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseActivity;
import com.htxtdshopping.htxtd.frame.ui.first.fragment.FirstFragment;
import com.htxtdshopping.htxtd.frame.ui.four.fragment.FourFragment;
import com.htxtdshopping.htxtd.frame.ui.second.fragment.SecondFragment;
import com.htxtdshopping.htxtd.frame.ui.third.fragment.ThirdFragment;
import com.htxtdshopping.htxtd.frame.widget.pagerbottomtabstrip.NavigationController;
import com.htxtdshopping.htxtd.frame.widget.pagerbottomtabstrip.PageNavigationView;
import com.htxtdshopping.htxtd.frame.widget.pagerbottomtabstrip.item.BaseTabItem;
import com.htxtdshopping.htxtd.frame.widget.pagerbottomtabstrip.item.NormalItemView;
import com.htxtdshopping.htxtd.frame.widget.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author chenzhipeng
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.pnv_tab)
    PageNavigationView mPnvTab;

    private String[] mTitles;
    private int[] mIconUnSelectIds;
    private int[] mIconSelectIds;

    private List<Fragment> mFragments;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourFragment fourFragment;
    private NavigationController mController;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.transparent));

        mController = mPnvTab.custom()
                .addItem(newItem(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, "框架"))
                .addItem(newItem(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, "第三方"))
                .addItem(newItem(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, "系统"))
                .addItem(newItem(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, "控件"))
                .build();

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
        mController.addSimpleTabItemSelectedListener(new SimpleTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentUtils.showHide(index, mFragments);
            }
        });
    }

    @Override
    public void initData() {

    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.BLACK);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color._81D8CF));
        normalItemView.setTextSize(AutoSizeUtils.pt2px(this, 24));
        normalItemView.setUnreadMsgTextSize(AutoSizeUtils.pt2px(this,18));
        return normalItemView;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}