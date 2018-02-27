package com.hong.cookbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.widget.Toast
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.event.TopMsg
import com.hong.cookbook.http.CommonApi
import com.hong.cookbook.http.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isQuit = false
    private var fragmentList =ArrayList<Fragment>()
    private var compositeList: ArrayList<CompositeDisposable>? = null

    private var pageAdapter:PageAdapter?=null

    private var viewHeight=0

    private var totalScrollRange:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeList = ArrayList()

        fab.setOnClickListener {
            EventBus.getDefault().post(TopMsg())
        }

        initToolBar()

        initRecy()


        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i("test", "verticalOffset: " + verticalOffset)
            totalScrollRange=appBarLayout.totalScrollRange
            if (verticalOffset == 0) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                search_icon.alpha = 0f
            } else {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                search_icon.alpha = 1f
            }
        }
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
                var cookPage=CookPage.newInstance(childs[j].childs)
                fragmentList.add(cookPage)
            }

            pageAdapter= PageAdapter(supportFragmentManager,fragmentList,items!!)
            page.adapter = pageAdapter
            tab_layout.setupWithViewPager(page)
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            viewHeight=title_menu.measuredHeight
        }
    }
    private fun initToolBar() {
        toolbar.title = "酷可博"
        toolbar.setNavigationIcon(R.mipmap.menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

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
