package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.decoration.GridDividerItemDecoration;
import com.htxtdshopping.htxtd.frame.ui.third.adapter.ChangeAvatarAdapter;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author chenzhipeng
 */
@RuntimePermissions
public class ChangeAvatarActivity extends BaseFitsWindowActivity {

    private static final int CODE_SINGLE_PICKER = 0;
    private static final int CODE_MULTIPLE_PICKER = 1;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    private ChangeAvatarAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_avatar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRvContent.setLayoutManager(new GridLayoutManager(this, 4));
        mRvContent.addItemDecoration(new GridDividerItemDecoration(this, 10));
        mAdapter = new ChangeAvatarAdapter();
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChangeAvatarActivityPermissionsDispatcher.takeMultipleImagesWithPermissionCheck(ChangeAvatarActivity.this);
            }
        });
    }

    @Override
    public void initData() {
        mAdapter.addData("");
    }

    @OnClick({R.id.iv_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                ChangeAvatarActivityPermissionsDispatcher.takePictureWithPermissionCheck(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CODE_SINGLE_PICKER:
                String imagePath = data.getStringExtra(ImagePickerActivity.RESULT_KEY_IMAGE_PATH);
                mIvAvatar.setImageBitmap(ImageUtils.getBitmap(imagePath));
                break;
            case CODE_MULTIPLE_PICKER:
                List<String> imagePaths = (List<String>) data.getSerializableExtra(ImagePickerActivity.RESULT_KEY_IMAGE_PATH);
                mAdapter.setNewData(imagePaths);
                break;
            default:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void takePicture() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.KEY_SELECT_MODE, ImagePickerActivity.MODE_AVATAR);
        startActivityForResult(intent, CODE_SINGLE_PICKER);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void takeMultipleImages() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.KEY_SELECT_MODE, ImagePickerActivity.MODE_MULTIPLE);
        intent.putExtra(ImagePickerActivity.KEY_MAX_SELECT_NUM, 9);
        startActivityForResult(intent, CODE_MULTIPLE_PICKER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ChangeAvatarActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onShowRationale(PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onPermissionDenied() {
        ToastUtils.showLong("此功能需要文件权限");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onNeverAskAgain() {
        ToastUtils.showLong("请到设置中开启文件权限");
    }
}
