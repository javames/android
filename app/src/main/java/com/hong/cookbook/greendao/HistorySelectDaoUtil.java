package com.hong.cookbook.greendao;

import android.content.Context;
import android.util.Log;

import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.Menu;
import com.hong.cookbook.gen.HistorySelectDao;
import com.hong.cookbook.gen.MenuDao;
import com.hong.mvplib.mvpbase.BaseView;
import com.hong.mvplib.mvpbase.impl.BasePresenterImpl;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/3.
 */

public class HistorySelectDaoUtil extends BaseDaoUtilImpl{

    private HistorySelectDaoUtil() {
    }

    private static class ViewHolder{
        private static final HistorySelectDaoUtil INSTANCE=new HistorySelectDaoUtil();
    }

    public static HistorySelectDaoUtil getInstance(){
        return ViewHolder.INSTANCE;
    }
    @Override
    public boolean insertBean(BaseBean clazz) {
        boolean flag = mManager.getmDaoSession().getHistorySelectDao().insert((HistorySelect) clazz) == -1 ? false : true;
        Log.i("", "insert Menu :" + flag + "-->" + clazz.toString());
        return flag;
    }

    @Override
    public ArrayList QueryAllBean() {
        return (ArrayList) mManager.getmDaoSession().getHistorySelectDao().loadAll();
    }

    @Override
    public boolean deleteBean(BaseBean clazz) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().getHistorySelectDao().delete((HistorySelect) clazz);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().getHistorySelectDao().deleteAll();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean updateBean(BaseBean clazz) {
        return false;
    }

    /**
     * 根据menuId查询
     */
    public List<HistorySelect> queryHistorySelectByKey(String key){
        Query<HistorySelect> nQuery = mManager.getmDaoSession().queryBuilder(HistorySelect.class).where(HistorySelectDao.Properties.Key.eq(key)).build();
        return nQuery==null?null:nQuery.list();
    }

}
