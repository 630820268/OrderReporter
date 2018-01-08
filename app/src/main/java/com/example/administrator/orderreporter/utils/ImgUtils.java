package com.example.administrator.orderreporter.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by JINAN on 2017/10/16.
 */

public class ImgUtils {

    public static void imageLoader(ImageView imageView, String url, Drawable error) {
        Glide.with(imageView.getContext()).load(url).error(error).into(imageView);
    }

    public static void imageLoader(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

//    public static void imageTransform(Context context, ImageView imageView, String url) {
//        /**
//         * Glide加载圆角图片, 重写BitmapTransformation类
//         */
//        Glide.with(context).load(url).transform(new GlideRoundTransform(context)).into(imageView);
//
//    }
}
