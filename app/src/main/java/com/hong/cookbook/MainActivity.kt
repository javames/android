package com.hong.cookbook

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hong.cookbook.bean.CategoryInfoBean
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.http.CommonApi
import com.hong.cookbook.http.HttpResult
import com.hong.cookbook.http.RetrofitService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isQuit = false
    private var fragmentList =ArrayList<Fragment>()
    private var compositeList: ArrayList<CompositeDisposable>? = null

    private var pageAdapter:PageAdapter?=null

    private var lastOffset=0;

    private var viewHeight=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeList = ArrayList()


        initToolBar()

        initRecy()

        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i("test", "verticalOffset: " + verticalOffset)

            if (verticalOffset == 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                search_icon.alpha = 0f
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                search_icon.alpha = 1f
            }

//            if(lastOffset==verticalOffset&&verticalOffset!=0){
//                title_menu.visibility=View.GONE
//            }else{
//                title_menu.visibility=View.VISIBLE
//            }

            lastOffset=verticalOffset
        }



        nevscroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            Log.i("nevscroll","scrollY= "+scrollY+"oldScrollY= "+oldScrollY)

//            if (scrollY > oldScrollY) {
//                // 向下滑动
//                //显示
//                anim(title_menu,true)
//            }

//            if (scrollY < oldScrollY) {
//                // 向上滑动
//                //消失
//                anim(title_menu,false)
//            }

            if (scrollY == 0) {
                // 顶部 显示
                title_menu.visibility=View.VISIBLE
                anim(title_menu,true)
            }

            if (scrollY == (v!!.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                // 底部
                title_menu.visibility=View.GONE
//                anim(title_menu,false)
            }
        }

    }
    var anim:ObjectAnimator?=null
    private fun anim(view:View,isHide:Boolean){
        if(anim!=null&&anim!!.isRunning){
            return
        }

        //属性动画对象
        var va:ValueAnimator?=null
        if(!isHide){
            //显示view，高度从0变到height值
            va = ValueAnimator.ofInt(title_menu.measuredHeight,0)
        }else{
           //隐藏view，高度从height变为0
            va = ValueAnimator.ofInt(0,viewHeight)

        }
        va.addUpdateListener(ValueAnimator.AnimatorUpdateListener() {
            valueAnimator->initAnim(valueAnimator)
        })

        va.duration=1000
        //开始动画
        va.start()
    }

    private fun initAnim(anim:ValueAnimator){
        var hs=anim.getAnimatedValue()
         //动态更新view的高度
        title_menu.layoutParams.height = hs as Int
        title_menu.requestLayout()
    }

    private fun initRecy() {
        RetrofitService.getInstance().createAPI()
                .getAllCategorys(CommonApi.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: CookBean? -> initPage(t)
                }, {
                    Toast.makeText(this@MainActivity, "网络请求出现错误！", Toast.LENGTH_LONG).show()
                })


    }

    private fun initPage(t: CookBean?) {
        if(t?.msg=="success"){
            val childs = t.result.childs
            var items= Array<String>(childs.size,{i->(i*i).toString()})
//            var items = arrayOfNulls<String>(childs.size)
            for(i in 0..(childs.size-1)){
                when(childs[i].categoryInfo.ctgId){
                    "0010001002"->items[i]="菜品"
                    "0010001003"->items[i]="工艺"
                    "0010001004"->items[i]="菜系"
                    "0010001005"->items[i]="人群"
                    "0010001006"->items[i]="功能"
                }
            }
            for(j in 0..(childs.size-1)){
                tab_layout.addTab(tab_layout.newTab().setText(items[j]))
                fragmentList.add(CookPage.newInstance(childs[j].childs))
            }

            pageAdapter= PageAdapter(supportFragmentManager,fragmentList,items!!)
            page.adapter = pageAdapter
            tab_layout.setupWithViewPager(page)

        }
    }


    private fun initToolBar() {
        toolbar.title = "酷可博"
        toolbar.setNavigationIcon(R.mipmap.menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
          viewHeight=title_menu.measuredHeight
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!isQuit) {
            Toast.makeText(this@MainActivity, "再按一次退出程序", Toast.LENGTH_LONG).show()
//            toast("再按一次退出程序")
            isQuit = true
            object : Thread() {
                override fun run() {
                    try {
                        Thread.sleep(2000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        isQuit = false
                    }
                }
            }.start()
        } else {
            System.exit(0)
        }

    }

    private class PageAdapter(fm: FragmentManager, var fragments:List<Fragment>, var titles:Array<String> ) :FragmentPagerAdapter(fm){

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}
