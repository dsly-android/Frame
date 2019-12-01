package com.htxtdshopping.htxtd.frame.widget.web;

import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author 陈志鹏
 * @date 2019-11-18
 */
public class X5WebViewClient extends WebViewClient {

    @Override
    public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        //忽略SSL证书错误检测
        //表示等待证书响应
        sslErrorHandler.proceed();
    }
}