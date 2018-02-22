package com.hong.cookbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hong.cookbook.bean.CategoryInfoBean
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

    private var compositeList:ArrayList<CompositeDisposable>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeList=ArrayList()

        initToolBar()

        initRecy()

        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i("test","verticalOffset: "+verticalOffset)
            if(verticalOffset==0){
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                search_icon.alpha=0f
            }else{
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                search_icon.alpha=1f
            }
        }

    }

    private fun initRecy(){
        RetrofitService.getInstance().createAPI()
                .getAllCategorys(CommonApi.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: HttpResult<CategoryInfoBean>? ->
                }, {
                    Toast.makeText(this@MainActivity,"网络请求出现错误！", Toast.LENGTH_LONG).show()
                })


    }


    private fun initToolBar(){
        toolbar.title="酷可博"
        toolbar.setNavigationIcon(R.mipmap.menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (!isQuit) {
            Toast.makeText(this@MainActivity,"再按一次退出程序",Toast.LENGTH_LONG).show()
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

}
