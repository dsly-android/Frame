package com.htxtdshopping.htxtd.frame.ui.third.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.base.BaseActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 陈志鹏
 * @date 2019-08-06
 */
public class UpgradeActivity extends BaseActivity {

    public static final String VERSIONCODE = "versionCode";
    public static final String VERSIONNAME = "versionName";
    public static final String APKURL = "apkUrl";
    public static final String ISFORCE = "isForce";
    public static final String DESCRIPTION = "description";

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    private int mVersionCode;
    private String mVersionName;
    private String mApkUrl;
    private boolean mIsForce;
    private String mDescription;

    @Override
    public int getLayoutId() {
        return R.layout.activity_upgrade;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BarUtils.setStatusBarVisibility(this, false);

        mVersionCode = getIntent().getIntExtra(VERSIONCODE, AppUtils.getAppVersionCode());
        mVersionName = getIntent().getStringExtra(VERSIONNAME);
        mApkUrl = getIntent().getStringExtra(APKURL);
        mIsForce = getIntent().getBooleanExtra(ISFORCE, false);
        mDescription = getIntent().getStringExtra(DESCRIPTION);

        /*获取策略信息，初始化界面信息*/
        mTvTitle.setText(mTvTitle.getText().toString() + mVersionCode);
        mTvVersion.setText(mTvVersion.getText().toString() + mVersionName);
        mTvContent.setText(mDescription);

        /*获取下载任务，初始化界面信息*/
        updateBtn(Beta.getStrategyTask());
    }

    @Override
    public void initEvent() {
        /*注册下载监听，监听下载事件*/
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                updateBtn(task);
            }

            @Override
            public void onCompleted(DownloadTask task) {
                updateBtn(task);
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {
                updateBtn(task);
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_cancel, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                Beta.cancelDownload();
                finish();
                break;
            case R.id.btn_start:
                DownloadTask task = Beta.startDownload();
                updateBtn(task);
                finish();
                break;
            default:
                break;
        }
    }

    public void updateBtn(DownloadTask task) {
        /*根据下载任务状态设置按钮*/
        switch (task.getStatus()) {
            case DownloadTask.INIT:
            case DownloadTask.DELETED:
            case DownloadTask.FAILED:
                mBtnStart.setText("开始下载");
                break;
            case DownloadTask.COMPLETE:
                mBtnStart.setText("安装");
                break;
            case DownloadTask.DOWNLOADING:
                mBtnStart.setText("暂停");
                break;
            case DownloadTask.PAUSED:
                mBtnStart.setText("继续下载");
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*注销下载监听*/
        Beta.unregisterDownloadListener();
    }
}
