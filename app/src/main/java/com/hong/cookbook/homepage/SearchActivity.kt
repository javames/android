package com.hong.cookbook.homepage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.widget.TextView
import com.hong.cookbook.R
import com.hong.cookbook.bean.CookBean
import com.hong.cookbook.homepage.contact.SearchContact
import com.hong.cookbook.homepage.presenter.HomePresenter
import com.hong.cookbook.homepage.presenter.SearchPresenter
import com.hong.mvplib.mvpbase.BasePresenter
import com.hong.mvplib.mvpbase.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import android.view.Gravity
import android.util.TypedValue
import android.graphics.Color.parseColor
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextSwitcher
import com.hong.cookbook.DensityUtil
import com.hong.cookbook.SoftInputUtil
import com.hong.cookbook.bean.HistorySelect
import com.hong.cookbook.bean.HotSelect
import com.hong.cookbook.event.RefreshEvent
import com.hong.cookbook.greendao.HistorySelectDaoUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList


/**
 * Created by Administrator on 2018/3/2
 */
class SearchActivity:BaseActivity<SearchPresenter>() ,SearchContact.View{

    override fun initPresenter(): SearchPresenter{
        return SearchPresenter(this,this)
    }

    override fun setData(historyList: ArrayList<HistorySelect>?, hotList: ArrayList<HotSelect>?) {

        history_flowlayout.removeAllViews()
        hot_flowlayout.removeAllViews()

        if(historyList!=null&&historyList.size>0){
            for(i in 0..(historyList!!.size-1)){
                if(i>12){
                    return
                }
                val item = getItem()
                item.text=historyList[i].key
                item.tag = item.text.toString()
                item.setOnClickListener(viewClick)
                history_flowlayout.addView(item)

            }
        }


        if(hotList!=null&&hotList.size>0){

            for(i in 0..(hotList!!.size-1)){
                if(i>12){
                    return
                }
                val item = getItem()
                item.text=hotList[i].key
                item.tag = item.text.toString()
                item.setOnClickListener(viewClick)
                hot_flowlayout.addView(item)
            }
        }else{
            var hotList=listOf(HotSelect(null,"红烧肉"), HotSelect(null,"花粥食谱"), HotSelect(null,"花粥食谱"), HotSelect(null,"番茄牛腩饭"), HotSelect(null,"地瓜糕"))

            for(i in 0..(hotList!!.size-1)){
                if(i>12){
                    return
                }
                val item = getItem()
                item.text=hotList[i].key
                item.tag = item.text.toString()
                item.setOnClickListener(viewClick)
                hot_flowlayout.addView(item)
            }
        }

    }

    private var viewClick=View.OnClickListener {
        var key=it.tag.toString()
        toCookMenuActivity(key)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshThread(event: RefreshEvent) {
        presenter.getData()
    }
    override fun setLayoutResId() {
        layoutResId=R.layout.activity_search
    }

    override fun initData(){
        presenter.getData()
    }

    override fun respMenuCooks(cookId: MutableList<CookBean.ResultBean.ChildsBeanX>?) {

    }

    private fun getItem():TextView{
        val ranHeight = DensityUtil.dip2px(this, 32f)
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight)
        lp.setMargins(DensityUtil.dip2px(this, 10f), 0, DensityUtil.dip2px(this, 10f), 0)
        val tv = TextView(this)
        tv.setPadding(DensityUtil.dip2px(this, 15f), 0, DensityUtil.dip2px(this, 15f), 0)
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.gravity = Gravity.CENTER_VERTICAL
        tv.background=resources.getDrawable(R.drawable.item_shape)
        tv.setLines(1)
        tv.layoutParams=lp
        return tv
    }

    private fun toCookMenuActivity(name:String){
        var bundle=Bundle()
        bundle.putString("name",name)
        toActivity(this@SearchActivity,bundle,CookMenuActivity::class.java)
    }
    override fun initView(){
        EventBus.getDefault().register(this)
        search_btn.setOnClickListener {
            toCookMenuActivity(edit_txt.text.toString().trim())
        }

        del_search.setOnClickListener {
            edit_txt.text=null
        }

        close.setOnClickListener {
            finish()
        }

        edit_txt.setOnEditorActionListener { v, actionId, event ->
            if(actionId== EditorInfo.IME_ACTION_SEND ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)){
                when(event.action){
                    KeyEvent.ACTION_UP->
                        toCookMenuActivity(edit_txt.text.toString().trim())
                }
            }
            true

        }

        edit_txt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                var size=s.toString().length
                if(size>0){
                    del_search.visibility=View.VISIBLE
                }else{
                    del_search.visibility=View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        recycle_record.setOnClickListener {
            HistorySelectDaoUtil.getInstance().deleteAll()
            presenter.getData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}