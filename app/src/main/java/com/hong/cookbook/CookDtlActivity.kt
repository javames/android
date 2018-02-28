package com.hong.cookbook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.hong.cookbook.adapter.MethodAdapter
import com.hong.cookbook.bean.CookMenuBean
import com.hong.cookbook.bean.MethodBean
import kotlinx.android.synthetic.main.activity_cook.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * Created by changhong on 2018/2/27.
 */
class CookDtlActivity: AppCompatActivity()  {

    var recipe: CookMenuBean.ResultBean.ListBean.RecipeBean?=null
    var methodAdapter: MethodAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cook)
        recipe= intent.getSerializableExtra("recipe") as CookMenuBean.ResultBean.ListBean.RecipeBean?
        initView()
    }

    private fun initView(){

        title_bar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        back.setOnClickListener {
            finish()
        }

        title_txt.text=recipe!!.title
        if(!TextUtils.isEmpty(recipe!!.img)){
            GlideUtil.loadImg(this,recipe!!.img,img)
        }
        val ingredients = recipe!!.ingredients
        if (!TextUtils.isEmpty(ingredients)) {
            var items = ingredients.replace("[\\[\\]]".toRegex(), "")
            items = items.replace("\"", "").replace("\"", "")
            items=items.replace(" ","")
            ingredients_txt.text="  "+items
        }

        var methods=recipe!!.method
        if(!TextUtils.isEmpty(methods)){
            var list=JSONUtils.fromJsonList(methods,MethodBean::class.java)
            if(list.size!=0){
                methodAdapter= MethodAdapter(list)
                method_recycler.layoutManager=LinearLayoutManager(this)
                method_recycler.adapter=methodAdapter
            }
        }

        var summary=recipe!!.sumary
        summary.replace("  ","")
        sumary.text=summary
    }


    override fun onDestroy() {
        super.onDestroy()

        methodAdapter=null
        ResetApplication.getRefWatcher(this).watch(this)
    }
}