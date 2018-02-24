package com.hong.cookbook

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Administrator on 2018/2/24.
 */
object GlideUtil {


    fun loadImg(context: Context, imageUrl:String, imageView: ImageView){
        var  requestOptions= RequestOptions()
        requestOptions.error(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView)
    }
    fun loadQiniuImg(context: Context, imageUrl:String, imageView: ImageView){
        var  requestOptions= RequestOptions()
        requestOptions.error(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView)
    }
}