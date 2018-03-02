package com.hong.cookbook.homepage

import android.os.Bundle
import com.hong.cookbook.R
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.homepage.contact.SearchContact
import com.hong.cookbook.homepage.presenter.HomePresenter
import com.hong.cookbook.homepage.presenter.SearchPresenter
import com.hong.mvplib.mvpbase.BasePresenter
import com.hong.mvplib.mvpbase.impl.BaseActivity

/**
 * Created by Administrator on 2018/3/2.
 */
class SearchActivity:BaseActivity() ,SearchContact.View{

    override fun initPresenter(): BasePresenter<*> {
        return SearchPresenter(this,this)
    }

    override fun setData(cookId: MutableList<CookBean.ResultBean.ChildsBeanX>?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}