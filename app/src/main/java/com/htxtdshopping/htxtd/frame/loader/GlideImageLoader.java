package com.htxtdshopping.htxtd.frame.loader;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /*Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);*/
    }
}
