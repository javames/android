package com.hong.cookbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }
    private fun initToolBar(){
        toolbar.title="酷可博"
        toolbar.setNavigationIcon(R.mipmap.menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }


}
