package com.hong.cookbook.homepage

import android.os.Bundle
import com.hong.cookbook.R
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.homepage.contact.CookMenuContact
import com.hong.cookbook.homepage.presenter.CookMenuPresenter
import com.hong.mvplib.mvpbase.BasePresenter
import com.hong.mvplib.mvpbase.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_cookmenu.*

/**
 * Created by Administrator on 2018/3/3.
 */
class CookMenuActivity:BaseActivity(),CookMenuContact.View {


    override fun initPresenter(): BasePresenter<*> {
        return CookMenuPresenter(this,this)
    }

    override fun setData(cookId: MutableList<CookBean.ResultBean.ChildsBeanX>?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cookmenu)
        var name=dataBundle.get("name")

        var fragment=CookPage.newInstance(name.toString())
        var activ=fragment.activity
        if(null!=activ){
            val supportFragmentManager = activ.supportFragmentManager
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.remove(fragment)
            beginTransaction.commit()
        }


        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        //创建fragment但是不绘制UI
        transaction.add(R.id.result_list,fragment)
        transaction.commit()

        back_fab.setOnClickListener {
            finish()
        }
    }
}