package com.htxtdshopping.htxtd.frame.ui.third.adapter;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.utils.GlideUtils;

/**
 * @author 陈志鹏
 * @date 2018/12/13
 */
public class ChangeAvatarAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChangeAvatarAdapter() {
        super(R.layout.item_change_avatar);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (ObjectUtils.isEmpty(item)) {
            helper.setImageResource(R.id.iv_img, R.drawable.image_add_sel);
        } else {
            GlideUtils.loadImage(mContext,item, helper.getView(R.id.iv_img));
        }
    }
}
