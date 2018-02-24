package com.hong.cookbook

import android.graphics.drawable.GradientDrawable
import android.view.View

/**
 * Created by Administrator on 2018/2/23.
 */

object ShapeUtil{
    fun setSpColor(view: View, spColor:Int, connerRad:Float){
        val drawable = GradientDrawable()
        drawable.cornerRadius = connerRad
        drawable.setStroke(1, spColor)
        drawable.setColor(spColor)
        view.setBackgroundDrawable(drawable)
    }
    fun setSpColor(view: View, skColor:Int, spColor:Int, connerRad:Float){
        val drawable = GradientDrawable()
        drawable.cornerRadius = connerRad
        drawable.setStroke(1, skColor)
        drawable.setColor(spColor)
        view.setBackgroundDrawable(drawable)
    }
}
