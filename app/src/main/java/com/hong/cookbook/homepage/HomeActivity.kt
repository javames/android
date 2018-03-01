package com.hong.cookbook.homepage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.hong.cookbook.CookPage

import com.hong.cookbook.GlideUtil
import com.hong.cookbook.R
import com.hong.cookbook.adapter.PageAdapter
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.event.TopMsg
import com.hong.cookbook.homepage.contact.HomeContact
import com.hong.cookbook.homepage.presenter.HomePresenter
import com.hong.cookbook.http.Api
import com.hong.cookbook.http.CommonApi
import com.hong.mvplib.mvpbase.BasePresenter
import com.hong.mvplib.mvpbase.impl.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

/**
 * Created by Administrator on 2018/3/1.
 */

class HomeActivity : BaseActivity(),HomeContact.View {

    private var isQuit = false
    private var fragmentList = ArrayList<Fragment>()
    private var compositeList: ArrayList<CompositeDisposable>? = null

    private var pageAdapter: PageAdapter?=null

    private var viewHeight=0

    private var totalScrollRange:Int=0

    override fun initPresenter(): BasePresenter<*> {
        return HomePresenter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        initView()
        initData()
    }


    override fun setData(childs: List<CookBean.ResultBean.ChildsBeanX>?) {
        if(null!=childs){
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
            var cookPage= CookPage.newInstance(childs[j].childs)
            fragmentList.add(cookPage)
        }

        pageAdapter= PageAdapter(supportFragmentManager,fragmentList,items!!)
        page.adapter = pageAdapter
        page.offscreenPageLimit=childs.size
        tab_layout.setupWithViewPager(page)
       }
    }

    private fun initView(){
        fab.setOnClickListener {
            EventBus.getDefault().post(TopMsg())
        }

        search_icon.setOnClickListener {
            toast("点击搜索...")
        }

        initToolBar()

    }

    private fun initToolBar() {
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.title = "酷可博"
        toolbar.setNavigationIcon(R.mipmap.menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }

    private fun initData() {
        GlideUtil.loadImg(this,"http://f2.mob.com/null/2015/08/19/1439945954330.jpg",findView(R.id.backdrop))

        initRecy()
    }

    private fun initRecy(){
        (presenter as HomePresenter).getData()
    }

}
