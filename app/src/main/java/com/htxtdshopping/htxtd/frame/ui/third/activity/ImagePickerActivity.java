package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.constant.Constants;
import com.htxtdshopping.htxtd.frame.decoration.GridDividerItemDecoration;
import com.htxtdshopping.htxtd.frame.popup_window.ImageFolderPopupWindow;
import com.htxtdshopping.htxtd.frame.ui.third.adapter.ImagePickerAdapter;
import com.htxtdshopping.htxtd.frame.utils.IntentUtils;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.htxtdshopping.htxtd.frame.utils.local_data.CameraItem;
import com.htxtdshopping.htxtd.frame.utils.local_data.ImageDataUtils;
import com.htxtdshopping.htxtd.frame.utils.local_data.ImageFolder;
import com.htxtdshopping.htxtd.frame.utils.local_data.ImageItem;
import com.htxtdshopping.htxtd.frame.view.TitleBar;

import java.io.File;
import java.util.ArrayList;
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
public class ImagePickerActivity extends BaseFitsWindowActivity implements ImageDataUtils.OnImagesLoadedListener, ImageFolderPopupWindow.OnImageFolderSelectedListener {

    public static final String RESULT_KEY_IMAGE_PATH = "result_key_image_path";
    private static final int CODE_CROP_IMAGE = 1;
    private static final int CODE_TAKE_PHOTO = 0;
    private static final int CODE_IMAGE_PREVIEW = 2;

    public static final String KEY_SELECT_MODE = "key_select_mode";
    //选择头像
    public static final int MODE_AVATAR = 1;
    //选择多张图片
    public static final int MODE_MULTIPLE = 2;

    //选择数量
    public static final String KEY_MAX_SELECT_NUM = "key_max_select_num";

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.ll_footer)
    LinearLayout mLlFooter;
    @BindView(R.id.tv_dir)
    TextView mTvDir;
    @BindView(R.id.tv_preview)
    TextView mTvPreview;
    @BindView(R.id.tb_title)
    TitleBar mTbTitle;
    private int mSelectMode;
    private File mCameraTmpFile;
    private ImagePickerAdapter mAdapter;
    /**
     * 所有的图片文件夹
     */
    private List<ImageFolder> mImageFolders;
    private ImageFolderPopupWindow mFolderPopupWindow;
    //选中的图片
    private ArrayList<ImageItem> mSelectedImages;
    private int mMaxSelectNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_picker;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSelectedImages = new ArrayList<>();

        mMaxSelectNum = getIntent().getIntExtra(KEY_MAX_SELECT_NUM, 1);
        mSelectMode = getIntent().getIntExtra(KEY_SELECT_MODE, MODE_AVATAR);
        if (mSelectMode == MODE_MULTIPLE) {
            mTvPreview.setVisibility(View.VISIBLE);
            mTbTitle.setRightTextVisible(View.VISIBLE);
            refreshView();
        }

        mRvContent.setLayoutManager(new GridLayoutManager(this, 4));
        mRvContent.addItemDecoration(new GridDividerItemDecoration(this, 10));
        mAdapter = new ImagePickerAdapter(mSelectMode);
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity entity = mAdapter.getData().get(position);
                int itemType = entity.getItemType();
                if (itemType == ImagePickerAdapter.CAMERA) {
                    ImagePickerActivityPermissionsDispatcher.takePhotoWithPermissionCheck(ImagePickerActivity.this);
                } else {
                    ImageItem imageItem = (ImageItem) entity;
                    if (mSelectMode == MODE_AVATAR) {
                        IntentUtils.toImageCrop(ImagePickerActivity.this, imageItem.path, CODE_CROP_IMAGE);
                    } else {
                        IntentUtils.toImagePreview(ImagePickerActivity.this, mImageFolders.get(0).images, imageItem.path, mMaxSelectNum, CODE_IMAGE_PREVIEW);
                    }
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_check:
                        ImageItem imageItem = (ImageItem) mAdapter.getItem(position);
                        if (imageItem.isChecked == false) {
                            if (mSelectedImages.size() >= mMaxSelectNum) {
                                ToastUtils.showLong("您最多只能选择" + mMaxSelectNum + "张图片");
                                break;
                            }
                        }
                        imageItem.isChecked = !imageItem.isChecked;
                        mAdapter.notifyItemChanged(position, 0);
                        if (imageItem.isChecked == true) {
                            mSelectedImages.add(imageItem);
                        } else {
                            mSelectedImages.remove(imageItem);
                        }
                        refreshView();
                        break;
                    default:
                        break;
                }
            }
        });
        mTbTitle.setOnRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });
        mTbTitle.setOnTextBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initData() {
        new ImageDataUtils(this, null, this);
    }

    private void refreshView(){
        if (mSelectedImages.size() == 0) {
            mTvPreview.setText("预览");
            mTbTitle.setRightTextString("完成");
            mTbTitle.setRightTextClickable(false);
            mTbTitle.setAlpha(0.5f);
        } else {
            mTvPreview.setText("预览(" + mSelectedImages.size() + ")");
            mTbTitle.setRightTextString("完成(" + mSelectedImages.size() + "/" + mMaxSelectNum + ")");
            mTbTitle.setRightTextClickable(true);
            mTbTitle.setAlpha(1);
        }
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        this.mImageFolders = imageFolders;
        refreshData(0);
    }

    @OnClick({R.id.cl_dir, R.id.tv_preview})
    public void onViewClicked(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.cl_dir:
                if (ObjectUtils.isEmpty(mImageFolders)) {
                    return;
                }
                if (mFolderPopupWindow == null) {
                    mFolderPopupWindow = new ImageFolderPopupWindow(this,
                            mRvContent.getHeight(), mImageFolders, this);
                }
                if (!mFolderPopupWindow.isShowing()) {
                    mFolderPopupWindow.showAtLocation(mLlFooter, Gravity.BOTTOM, 0,
                            ScreenUtils.getScreenHeight() - mLlFooter.getTop());
                } else {
                    mFolderPopupWindow.dismiss();
                }
                break;
            case R.id.tv_preview:
                if (ObjectUtils.isEmpty(mImageFolders) || ObjectUtils.isEmpty(mImageFolders.get(0))) {
                    ToastUtils.showLong("没有图片可以预览");
                    return;
                }
                IntentUtils.toImagePreview(ImagePickerActivity.this, mImageFolders.get(0).images, mImageFolders.get(0).images.get(0).path, mMaxSelectNum, CODE_IMAGE_PREVIEW);
                break;
            default:
                break;
        }
    }

    @Override
    public void OnImageFolderSelected(int selectedPos) {
        refreshData(selectedPos);
        mTvDir.setText(mImageFolders.get(selectedPos).name);
    }

    private void refreshData(int selectedPos) {
        mAdapter.getData().clear();
        if (mSelectMode == MODE_AVATAR) {
            mAdapter.getData().add(new CameraItem());
        }
        if (mImageFolders.size() != 0) {
            mAdapter.addData(mImageFolders.get(selectedPos).images);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setResult(){
        ArrayList<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < mSelectedImages.size(); i++) {
            imagePaths.add(mSelectedImages.get(i).path);
        }
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_IMAGE_PATH, imagePaths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    IntentUtils.toImageCrop(this, mCameraTmpFile.getAbsolutePath(), CODE_CROP_IMAGE);
                } else {
                    mCameraTmpFile.delete();
                }
                break;
            case CODE_CROP_IMAGE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                String imagePath = data.getStringExtra(CropActivity.RESULT_KEY_IMAGE_PATH);
                Intent intent = new Intent();
                intent.putExtra(RESULT_KEY_IMAGE_PATH, imagePath);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case CODE_IMAGE_PREVIEW:
                if (resultCode != RESULT_OK){
                    return;
                }
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePreviewActivity.RESULT_KEY_ALL_IMAGE);
                boolean isFinish = data.getBooleanExtra(ImagePreviewActivity.RESULT_KEY_FINISH, false);
                ArrayList<ImageItem> allImages = mImageFolders.get(0).images;
                for (int i = 0; i < allImages.size(); i++) {
                    allImages.get(i).isChecked = images.get(i).isChecked;
                }
                mSelectedImages.clear();
                for (int i = 0; i < allImages.size(); i++) {
                    if (allImages.get(i).isChecked){
                        mSelectedImages.add(allImages.get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();
                refreshView();
                if (isFinish){
                    setResult();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mFolderPopupWindow != null && mFolderPopupWindow.isShowing()) {
            mFolderPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImagePickerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void takePhoto() {
        // 设置系统相机拍照后的输出路径
        // 创建临时文件
        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            mCameraTmpFile = new File(Constants.PATH_EXTERNAL_CACHE_IMAGE, "avatar.jpg");
        } else {
            mCameraTmpFile = new File(Constants.PATH_CACHE_IMAGE, "avatar.jpg");
        }
        IntentUtils.toCapture(this, mCameraTmpFile, CODE_TAKE_PHOTO);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onShowRationale(PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onPermissionDenied() {
        ToastUtils.showLong("此功能需要相机权限");
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onNeverAskAgain() {
        ToastUtils.showLong("请到设置中开启相机权限");
    }
}