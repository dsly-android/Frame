package com.android.dsly.zxing.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import androidx.annotation.NonNull;
import me.jessyan.autosize.utils.AutoSizeUtils;


/**
 * 生成条形码的工具类
 * @author chenzhipeng
 */

public class BarCodeUtils {

    /**
     * 获取建造者
     *
     * @param text 样式字符串文本
     */
    public static BarCodeUtils.Builder builder(@NonNull CharSequence text) {
        return new BarCodeUtils.Builder(text);
    }

    public static class Builder {

        private int backgroundColor = 0xffffffff;

        private int codeColor = 0xff000000;

        private int codeWidth = AutoSizeUtils.pt2px(Utils.getApp(), 1000);

        private int codeHeight = AutoSizeUtils.pt2px(Utils.getApp(), 300);

        private CharSequence content;

        public Builder backColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder codeColor(int codeColor) {
            this.codeColor = codeColor;
            return this;
        }

        public Builder codeWidth(int codeWidth) {
            this.codeWidth = codeWidth;
            return this;
        }

        public Builder codeHeight(int codeHeight) {
            this.codeHeight = codeHeight;
            return this;
        }

        public Builder(@NonNull CharSequence text) {
            this.content = text;
        }

        public Bitmap into(ImageView imageView) {
            Bitmap bitmap = BarCodeUtils.createBarCode(content, codeWidth, codeHeight, backgroundColor, codeColor);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            return bitmap;
        }
    }

    //---------------------------以下为生成条形码算法----------------------------------

    public static Bitmap createBarCode(CharSequence content, int barWidth, int barHeight, int backgroundColor, int codeColor) {
        /**
         * 条形码的编码类型
         */
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 0);

        final int backColor = backgroundColor;
        final int barCodeColor = codeColor;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(content + "", barcodeFormat, barWidth, barHeight, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? barCodeColor : backColor;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成条形码
     *
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽带
     * @param desiredHeight 生成条形码的高度
     * @return
     */
    public static Bitmap createBarCode(String contents, int desiredWidth, int desiredHeight) {
        return createBarCode(contents, desiredWidth, desiredHeight, 0xFF000000, 0xFFFFFFFF);
    }

    /**
     * 生成条形码
     * desiredWidth  生成条形码的宽带
     * desiredHeight 生成条形码的高度
     *
     * @param contents 需要生成的内容
     * @return 条形码的Bitmap
     */
    public static Bitmap createBarCode(String contents) {
        return createBarCode(contents, AutoSizeUtils.pt2px(Utils.getApp(), 1000), AutoSizeUtils.pt2px(Utils.getApp(), 300));
    }

    public static void createBarCode(String content, int codeWidth, int codeHeight, ImageView ivCode) {
        ivCode.setImageBitmap(createBarCode(content, codeWidth, codeHeight));
    }

    public static void createBarCode(String content, ImageView ivCode) {
        ivCode.setImageBitmap(createBarCode(content));
    }
}
