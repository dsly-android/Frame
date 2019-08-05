package com.htxtdshopping.htxtd.frame.ui.first.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.utils.QrCodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenzhipeng
 */
public class GenerateQrCodeActivity extends BaseFitsWindowActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.iv_img)
    ImageView mIvImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_generate_qr_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {}

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_generateQrCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_generateQrCode:
                String code = mEtCode.getText().toString();
                if (ObjectUtils.isEmpty(code)) {
                    return;
                }
                QrCodeUtils.builder(code).into(mIvImg);
//                BarCodeUtils.builder(code).into(mIvImg);
                break;
            default:
                break;
        }
    }
}
