package com.htxtdshopping.htxtd.frame.ui.first.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.dsly.common.base.BaseActivity;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.widget.CoverVideoPlayer;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import androidx.core.content.ContextCompat;
import butterknife.BindView;

public class NewsVideoActivity extends BaseActivity {

    @BindView(R.id.layout_top)
    LinearLayout mLlTop;
    @BindView(R.id.cvp_video)
    CoverVideoPlayer mCvpVideo;
    private boolean isPause;
    private OrientationUtils orientationUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_video;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(android.R.color.transparent));
        BarUtils.setStatusBarLightMode(this, true);

        mLlTop.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLlTop.getLayoutParams();
        params.topMargin = BarUtils.getStatusBarHeight();
        mLlTop.setLayoutParams(params);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mCvpVideo);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        //title
        mCvpVideo.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        mCvpVideo.getBackButton().setVisibility(View.VISIBLE);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(false)
                .setUrl("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4")
                .setReleaseWhenLossAudio(false)
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        mCvpVideo.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        }).build(mCvpVideo);
        mCvpVideo.loadCoverImage("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", R.mipmap.ic_launcher);
    }

    @Override
    public void initEvent() {
        mCvpVideo.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCvpVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orientationUtils.getIsLand() != 1) {
                    //直接横屏
                    orientationUtils.resolveByClick();
                }
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mCvpVideo.startWindowFullscreen(NewsVideoActivity.this, true, true);
            }
        });
    }

    @Override
    public void initData() {
        mCvpVideo.startAfterPrepared();
    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mCvpVideo.getCurrentPlayer().onVideoPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCvpVideo.getCurrentPlayer().onVideoResume(false);
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        mCvpVideo.getCurrentPlayer().release();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (!isPause) {
            mCvpVideo.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }
}
