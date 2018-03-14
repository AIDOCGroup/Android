package me.aidoc.client.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import me.aidoc.client.base.MyApplication;
import me.aidoc.client.util.custom.GlideRoundTransform;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 图片加载库参考
 * https://github.com/wasabeef/glide-transformations
 * */
public class ImageUtil {


    /**===========================加载图片方式，可换其他框架加载===========================================================================*/
    /**
     * 以正常模式加载网络图片
     */

    public static void show(ImageView mImageView, String imageUrl) {


        Glide.with(MyApplication.getContext()).load(imageUrl)
                .crossFade(0)
                .into(mImageView);  //crossFade是个淡入淡出效果
    }


    /**
     * 以拉伸模式加载网络图片
     */
    public static void show1(ImageView mImageView, String imageUrl) {
        Glide.with(MyApplication.getContext()).load(imageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(MyApplication.getContext(),8))
                .crossFade(0)
                .into(mImageView);  //crossFade是个淡入淡出效果
    }

    //加载正方形图片
    public static void showSquare(ImageView mImageView, String imageUrl) {


        Glide.with(MyApplication.getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mImageView);
    }

    //加载正方形图片(商品)
    public static void showSquare2(ImageView mImageView, String imageUrl) {

        Glide.with(MyApplication.getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
    }


    /**
     * 加载圆头像
     */
    public static void showCircle(final ImageView mImageView, String imageUrl) {

        Glide.with(MyApplication.getContext()).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(MyApplication.getContext()))
                .crossFade(0)
                .into(mImageView);
    }



    public static void showRoundedImage(ImageView imageView, String imageUrl) {

        Glide.with(MyApplication.getContext())                                 //可以传getApplicationContext Activity Fragment
                .load(imageUrl)                                  //图片地址
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(MyApplication.getContext(),4))
                .centerCrop()
                .into(imageView);
    }


    public static void showGoodsImage(ImageView imageView, String imageUrl){

        Glide.with(MyApplication.getContext())                                 //可以传getApplicationContext Activity Fragment
                .load(imageUrl)                                  //图片地址
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(MyApplication.getContext(),4))
                .into(imageView);
    }


    public static void showRoundedImage2(ImageView imageView, String imageUrl, int radius) {


        Glide.with(MyApplication.getContext())                                 //可以传getApplicationContext Activity Fragment
                .load(imageUrl)                                  //图片地址
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(MyApplication.getContext(),radius))
                .into(imageView);
    }


    /**
     * 加载高斯模糊
     */
    public static void show2(ImageView mImageView, String imageUrl) {

        Glide.with(MyApplication.getContext())
                .load(imageUrl)
                .bitmapTransform(new BlurTransformation(MyApplication.getContext(), 25)).crossFade(0)
                .into(mImageView);
    }



    //=========================Glide加载GIF图片配置==============================================================//

    //加载本地GIF
    public static void showGIF(ImageView imageView, int img){
        Glide.with(MyApplication.getContext()).load(img).asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    //加载网络GIF
    public static void showGIF(ImageView imageView, String img){
       Glide.with(MyApplication.getContext()).load(img).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }


}
