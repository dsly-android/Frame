package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseActivity;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.htxtdshopping.htxtd.frame.utils.VoiceRecordManager;
import com.htxtdshopping.htxtd.frame.view.VoiceLineView;

import java.io.File;

import androidx.annotation.NonNull;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author chenzhipeng
 */
@RuntimePermissions
public class VoiceRecordActivity extends BaseActivity {

    @BindView(R.id.vlv_wave)
    VoiceLineView mVlvWave;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private VoiceRecordManager mManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_voice_record;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mManager = VoiceRecordManager.getInstance();
        mVlvWave.setPause();
    }

    @Override
    public void initEvent() {
        mManager.setOnAudioRecordListener(new VoiceRecordManager.OnAudioRecordListener() {
            @Override
            public void onStartRecord() {
                mVlvWave.setContinue();
            }

            @Override
            public void onAudioVolumeChanged(int volume, double db) {
                mVlvWave.setVolume((int) db);
            }

            @Override
            public void onPauseRecord() {
                mVlvWave.setPause();
            }

            @Override
            public void onResumeRecord() {
                mVlvWave.setContinue();
            }

            @Override
            public void onFinishRecord(int duration, File recordFile) {
                ToastUtils.showLong(duration + "  " + recordFile.getAbsolutePath());
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.stop();
        mManager.setOnAudioRecordListener(null);
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    public void startRecord(){
        mManager.start();
    }

    public void start(View view) {
        VoiceRecordActivityPermissionsDispatcher.startRecordWithPermissionCheck(this);
    }

    public void pause(View view) {
        mManager.pause();
    }

    public void resume(View view) {
        mManager.resume();
    }

    public void stop(View view) {
        mManager.stop();
    }

    public void record(View view) {
        ActivityUtils.startActivity(VoicePlayActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VoiceRecordActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
