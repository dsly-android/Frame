package com.htxtdshopping.htxtd.frame.utils.local_data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.htxtdshopping.htxtd.frame.ui.third.adapter.ImagePickerAdapter;

/**
 * @author 陈志鹏
 * @date 2018/12/11
 */
public class CameraItem implements MultiItemEntity {

    @Override
    public int getItemType() {
        return ImagePickerAdapter.CAMERA;
    }
}
