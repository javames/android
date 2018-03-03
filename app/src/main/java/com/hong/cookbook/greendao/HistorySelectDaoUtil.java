package com.hong.cookbook.greendao;

import android.content.Context;
import android.util.Log;

import com.hong.cookbook.bean.HistorySelect;
import com.hong.mvplib.mvpbase.BaseView;
import com.hong.mvplib.mvpbase.impl.BasePresenterImpl;

/**
 * Created by Administrator on 2018/3/3.
 */

public class HistorySelectDaoUtil extends BaseDaoUtilImpl<HistorySelect>{

//    @Override
//    public boolean insertBean(HistorySelect clazz) {
//        boolean flag = mManager.getmDaoSession().getHistorySelectDao().insert(clazz) == -1 ? false : true;
//        Log.i("", "insert Menu :" + flag + "-->" + clazz.toString());
//        return flag;
//    }


    @Override
    public boolean insertBean(Object clazz) {
        boolean flag = mManager.getmDaoSession().getHistorySelectDao().insert((HistorySelect)clazz) == -1 ? false : true;
        Log.i("", "insert Menu :" + flag + "-->" + clazz.toString());
        return flag;
    }
}
