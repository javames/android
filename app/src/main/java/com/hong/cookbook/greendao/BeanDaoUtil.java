package com.hong.cookbook.greendao;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hong.cookbook.GreenDaoManager;
import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/3.
 */

public class BeanDaoUtil{

    protected GreenDaoManager mManager;

    private BeanDaoUtil(){
        mManager = GreenDaoManager.getInstance();
    }

    private static class BeanHolder{
        private static final BeanDaoUtil INSTANCE=new BeanDaoUtil();
    }

    public static BeanDaoUtil getInstance(){
        return BeanHolder.INSTANCE;
    }
    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param clazz
     * @return
     */
//    public <T>boolean insertBean(T clazz){
//        boolean flag = false;
//        flag = mManager.getmDaoSession().getHistorySelectDao().insert(clazz) == -1 ? false : true;
//        Log.i("", "insert Menu :" + flag + "-->" + clazz.toString());
//        return flag;
//    }

    /**
     * 查找所有数据
     */
    public <T> ArrayList<T> QueryAllBean(Class<T> clazz){
        ArrayList<T> list = (ArrayList<T>) mManager.getmDaoSession().loadAll(clazz);
        return list;
    }

    /**
     * 删除单条记录
     * @param clazz
     * @return
     */
    public <T>boolean deleteBean(Class<T> clazz){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().delete(clazz);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(Class clazz){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().deleteAll(clazz);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param clazz
     * @return
     */
    public boolean updateBean(Class clazz){
        boolean flag = false;
        try {
            mManager.getmDaoSession().update(clazz);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
