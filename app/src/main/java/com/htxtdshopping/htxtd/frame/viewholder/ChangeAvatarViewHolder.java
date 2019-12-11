package com.htxtdshopping.htxtd.frame.viewholder;

import android.content.Context;
import android.view.View;

import com.android.dsly.common.base.BaseViewHolder;
import com.blankj.utilcode.util.ObjectUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.utils.GlideUtils;

/**
 * @author 陈志鹏
 * @date 2019-09-26
 */
public class ChangeAvatarViewHolder extends BaseViewHolder<String> {

    public ChangeAvatarViewHolder(View view) {
        super(view);
    }

    @Override
    public void convert(Context context, String data) {
        super.convert(context, data);
        if (ObjectUtils.isEmpty(data)) {
            setImageResource(R.id.iv_img, R.drawable.image_add_sel);
        } else {
            GlideUtils.loadImage(context, data, getView(R.id.iv_img));
        }
    }
}
