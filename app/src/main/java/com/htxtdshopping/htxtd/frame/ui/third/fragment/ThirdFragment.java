package com.htxtdshopping.htxtd.frame.ui.third.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.AppContext;
import com.htxtdshopping.htxtd.frame.base.BaseLazyFragment;
import com.htxtdshopping.htxtd.frame.ui.third.activity.ChangeAvatarActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.GridActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.LifecycleActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.ListActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.LoginActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.MaterialDesignActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.NotificationActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.PopupWindowActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.ServiceActivity;
import com.htxtdshopping.htxtd.frame.ui.third.activity.VoiceRecordActivity;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author 陈志鹏
 * @date 2018/9/7
 */
@RuntimePermissions
public class ThirdFragment extends BaseLazyFragment {

    @BindView(R.id.v_bar)
    View mVBar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_third;
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

    }

    @OnClick({R.id.btn_grid, R.id.btn_list, R.id.btn_record, R.id.btn_popup, R.id.btn_image_picker,
            R.id.btn_notification, R.id.btn_service, R.id.btn_login,R.id.btn_design,R.id.btn_version_update,
            R.id.btn_lifecycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_grid:
                ActivityUtils.startActivity(GridActivity.class);
                break;
            case R.id.btn_list:
                ActivityUtils.startActivity(ListActivity.class);
                break;
            case R.id.btn_record:
                ThirdFragmentPermissionsDispatcher.recordAudioWithPermissionCheck(this);
                break;
            case R.id.btn_popup:
                ActivityUtils.startActivity(PopupWindowActivity.class);
                break;
            case R.id.btn_image_picker:
                ActivityUtils.startActivity(ChangeAvatarActivity.class);
                break;
            case R.id.btn_notification:
                ActivityUtils.startActivity(NotificationActivity.class);
                break;
            case R.id.btn_service:
                ActivityUtils.startActivity(ServiceActivity.class);
                break;
            case R.id.btn_login:
                ActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.btn_design:
                ActivityUtils.startActivity(MaterialDesignActivity.class);
                break;
            case R.id.btn_version_update:
                AppContext.getInstance().checkUpdate(true);
                break;
            case R.id.btn_lifecycle:
                ActivityUtils.startActivity(LifecycleActivity.class);
                break;
            default:
                break;
        }
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    public void recordAudio() {
        ActivityUtils.startActivity(VoiceRecordActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ThirdFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
