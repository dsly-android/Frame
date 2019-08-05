package com.htxtdshopping.htxtd.frame.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.htxtdshopping.htxtd.frame.R;

import java.io.File;

/**
 * @author 陈志鹏
 * @date 2018/10/29
 */
public final class GlideUtils {

    public static void loadCircleImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop().dontAnimate();
        Glide.with(context).load(url).apply(options).into(iv);
    }

    public static void loadCircleImage(Context context, File file, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop().dontAnimate();
        Glide.with(context).load(file).apply(options).into(iv);
    }

    public static void loadImage(Context context, String urlOrPath, ImageView iv) {
        loadImage(context, R.mipmap.ic_launcher, R.mipmap.ic_launcher, urlOrPath, iv);
    }

    public static void loadImage(Context context, int placeRes, int errRes, String urlOrPath, ImageView iv) {
        RequestOptions options = new RequestOptions().placeholder(placeRes).error(errRes);
        Glide.with(context).load(urlOrPath).apply(options).into(iv);
    }
}