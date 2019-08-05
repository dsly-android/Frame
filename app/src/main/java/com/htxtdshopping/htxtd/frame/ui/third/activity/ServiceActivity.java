package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.service.LocalService;

import butterknife.OnClick;

public class ServiceActivity extends BaseFitsWindowActivity {


    private Intent mIntent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mIntent = new Intent(this, LocalService.class);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startService(mIntent);
                /*if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                    startForegroundService(mIntent);
                } else {
                    startService(mIntent);
                }*/
                break;
            case R.id.btn_stop:
                stopService(mIntent);
                break;
            default:
                break;
        }
    }
}
