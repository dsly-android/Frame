package com.htxtdshopping.htxtd.frame.ui.first.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseLazyMvpFragment;
import com.htxtdshopping.htxtd.frame.ui.first.activity.BannerActivity;
import com.htxtdshopping.htxtd.frame.ui.first.activity.GenerateQrCodeActivity;
import com.htxtdshopping.htxtd.frame.ui.first.activity.PermissionActivity;
import com.htxtdshopping.htxtd.frame.ui.first.activity.RefreshAndLoadMoreActivity;
import com.htxtdshopping.htxtd.frame.ui.first.activity.RxjavaActivity;
import com.htxtdshopping.htxtd.frame.ui.first.presenter.FirstPresenter;
import com.htxtdshopping.htxtd.frame.ui.first.view.IFirstView;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.htxtdshopping.htxtd.frame.zxing.CaptureActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author 陈志鹏
 * @date 2018/9/7
 */
@RuntimePermissions
public class FirstFragment extends BaseLazyMvpFragment<FirstPresenter> implements IFirstView {

    private static final int KEY_SCAN_QR_CODE = 1;
    @BindView(R.id.v_bar)
    View mVBar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(mVBar, getResources().getColor(R.color._81D8CF));
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        mPresenter.haha();
    }

    @OnClick({R.id.btn_refresh_and_load_more, R.id.btn_permission, R.id.btn_scanQrCode, R.id.btn_generateQrCode,
            R.id.btn_banner,R.id.btn_rxjava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh_and_load_more:
                ActivityUtils.startActivity(RefreshAndLoadMoreActivity.class);
                break;
            case R.id.btn_permission:
                ActivityUtils.startActivity(PermissionActivity.class);
                break;
            case R.id.btn_scanQrCode:
                FirstFragmentPermissionsDispatcher.cameraWithPermissionCheck(this);
                break;
            case R.id.btn_generateQrCode:
                ActivityUtils.startActivity(GenerateQrCodeActivity.class);
                break;
            case R.id.btn_banner:
                ActivityUtils.startActivity(BannerActivity.class);
                break;
            case R.id.btn_rxjava:
                ActivityUtils.startActivity(RxjavaActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void aaa() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case KEY_SCAN_QR_CODE:
                String scanResult = data.getStringExtra(CaptureActivity.RESULT_KEY_SCAN_RESULT);
                ToastUtils.showLong(scanResult);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FirstFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void camera() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, KEY_SCAN_QR_CODE);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    public void onShowRationale(PermissionRequest request) {
        ToastUtils.showLong("OnShowRationale");
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void onPermissionDenied() {
        ToastUtils.showLong("OnPermissionDenied");
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void onNeverAskAgain() {
        ToastUtils.showLong("OnNeverAskAgain");
    }
}
