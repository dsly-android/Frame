package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseFitsWindowActivity;
import com.htxtdshopping.htxtd.frame.widget.floatwindow.FloatWindow;
import com.htxtdshopping.htxtd.frame.widget.floatwindow.MoveType;
import com.htxtdshopping.htxtd.frame.widget.floatwindow.Screen;

import butterknife.OnClick;

public class FlowWindowActivity extends BaseFitsWindowActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_flow_window;
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

    @OnClick({R.id.btn_show_float, R.id.btn_hide_float})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_show_float:
                if (FloatWindow.get() == null) {
                    ImageView iv = new ImageView(this);
                    iv.setImageResource(R.drawable.umeng_socialize_wxcircle);
                    FloatWindow
                            .with(getApplicationContext())
                            .setView(iv)
                            .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
                            .setHeight(Screen.width, 0.2f)
                            .setX(Screen.width, 0.5f)
                            .setY(Screen.height, 0.3f)
                            .setMoveType(MoveType.slide, 0, 0)
                            .setMoveStyle(500, new BounceInterpolator())
                            .setDesktopShow(true)
                            .build();
                }
                if (!FloatWindow.get().isShowing()){
                    FloatWindow.get().show();
                }
                break;
            case R.id.btn_hide_float:
                if (FloatWindow.get() != null) {
                    FloatWindow.get().destory();
                }
                break;
            default:
                break;
        }
    }
}
