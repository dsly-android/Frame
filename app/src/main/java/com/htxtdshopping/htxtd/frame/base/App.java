package com.htxtdshopping.htxtd.frame.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;
import com.htxtdshopping.htxtd.frame.BuildConfig;
import com.htxtdshopping.htxtd.frame.R;
import com.htxtdshopping.htxtd.frame.constant.Constants;
import com.htxtdshopping.htxtd.frame.di.component.DaggerAppComponent;
import com.htxtdshopping.htxtd.frame.network.OssService;
import com.htxtdshopping.htxtd.frame.network.STSProvider;
import com.htxtdshopping.htxtd.frame.notification.NotificationChannels;
import com.htxtdshopping.htxtd.frame.ui.second.activity.UpgradeActivity;
import com.htxtdshopping.htxtd.frame.utils.ToastUtils;
import com.htxtdshopping.htxtd.frame.view.refresh.NewsRefreshHeader;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import okhttp3.OkHttpClient;

/**
 * @author 陈志鹏
 * @date 2018/7/27
 */
public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this);
        //初始化OkGo
        initOkGo();
        //初始化LitePal
        LitePal.initialize(this);
        //初始化AndroidAutoSize
        initAutoSize();
        //初始化AndroidUtilCode
        Utils.init(this);
        //初始化log
        initLog();
        //初始化奔溃重启和奔溃日志
        if (!AppUtils.isAppDebug()) {
            initCrash();
        }
        //bugly
        initBugly();
        //oss
        initOss();
        //初始化x5内核
        initX5();
        //初始化通知渠道
        initNotificationChannels();
        //初始化UShare
        initUShare();
        //okdownload
        initOkDownload();
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //添加OkGo默认debug日志
        builder.addInterceptor(loggingInterceptor);

        //超时时间设置，默认60秒
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS / 2, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS / 2, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS / 2, TimeUnit.MILLISECONDS);

        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        //必须调用初始化
        OkGo.getInstance().init(this)
                //建议设置OkHttpClient，不设置会使用默认的
                .setOkHttpClient(builder.build());
//                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
//                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
//                .setRetryCount(3);   //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    private void initAutoSize() {
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance().setLog(false);
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportSubunits(Subunits.PT)
                .setSupportDP(false)
                .setSupportSP(false);
    }

    private void initLog() {
        LogUtils.getConfig()
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(BuildConfig.DEBUG)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)
                // 设置 log 全局标签，默认为空
                .setGlobalTag(null)
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("")
                // 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
                .setFilePrefix("")
                // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true)
                // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(true)
                // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(LogUtils.V)
                // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)
                // log 栈深度，默认为 1
                .setStackDeep(1)
                // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setStackOffset(0)
                // 设置日志可保留天数，默认为 -1 表示无限时长
                .setSaveDays(3)
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                LogUtils.e(Log.getStackTraceString(e));
                AppUtils.relaunchApp();
            }
        });
    }

    static {//使用static代码段可以防止内存泄漏
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //开始设置全局的基本参数
                layout.setEnableLoadMore(false);
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(false);
                layout.setEnableScrollContentWhenRefreshed(true);
                layout.setEnableFooterFollowWhenLoadFinished(true);
            }
        });

        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //开始设置全局的基本参数（这里设置的属性只跟下面的MaterialHeader绑定，其他Header不会生效，能覆盖DefaultRefreshInitializer的属性和Xml设置的属性）
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(android.R.color.white, R.color._81D8CF);
                return new NewsRefreshHeader(context);
            }
        });
    }

    private void initBugly() {
        String currentProcessName = ProcessUtils.getCurrentProcessName();
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(currentProcessName == null || currentProcessName.equals(getPackageName()));

        Beta.storageDir = new File(getFilesDir().getAbsolutePath() + "/bugly");
        Beta.enableHotfix = false;
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int i, UpgradeInfo upgradeInfo, boolean b, boolean b1) {
                if (upgradeInfo != null) {
                    Intent intent = new Intent(getApplicationContext(), UpgradeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    ToastUtils.showLong("你已经是最新版了");
                }
            }
        };
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {
                ToastUtils.showLong("检查更新失败");
            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                ToastUtils.showLong("你已经是最新版了");
            }

            @Override
            public void onUpgrading(boolean b) {
                if (b) {
                    ToastUtils.showLong("正在检查更新，请稍后...");
                }
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                ToastUtils.showLong("下载完成");
            }
        };

        // 初始化Bugly
        Bugly.init(getApplicationContext(), "38552d428b", false, strategy);
    }

    private void initOss() {
        //使用自己的获取STSToken的类
        OSSCredentialProvider credentialProvider = new STSProvider();

        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求数，默认5个
        conf.setMaxConcurrentRequest(5);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);

        OSS oss = new OSSClient(this, Constants.OSS_ENDPOINT, credentialProvider, conf);
        OssService.init(oss, Constants.OSS_BUCKET);
    }

    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.i( " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //是否允许非wifi场景下下载内核
        QbSdk.setDownloadWithoutWifi(false);
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initNotificationChannels() {
        NotificationChannels.createAllNotificationChannels(this);
    }

    private void initUShare() {
        UMConfigure.init(this, "5c3ca500f1f556c482000950", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    {
        //微信
        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().build();
    }

    private void initOkDownload(){
        DownloadDispatcher.setMaxParallelRunningCount(3);
    }
}