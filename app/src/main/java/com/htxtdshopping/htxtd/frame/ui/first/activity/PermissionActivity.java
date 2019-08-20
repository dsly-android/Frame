package com.htxtdshopping.htxtd.frame.ui.first.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author chenzhipeng
 */
public class PermissionActivity extends BaseFitsWindowActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    public void click(View view) {
        new RxPermissions(this)
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            ToastUtils.showLong("成功");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            ToastUtils.showLong("shouldShowRequestPermissionRationale");
                        } else {
                            ToastUtils.showLong("OnPermissionDenied");
                        }
                    }
                });
    }
}
