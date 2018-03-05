package com.hong.cookbook.greendao;

import android.util.Log;

import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.HotSelect;
import com.hong.cookbook.gen.HistorySelectDao;
import com.hong.cookbook.gen.HotSelectDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/3.
 */

public class HotSelectDaoUtil extends BaseDaoUtilImpl{

    private HotSelectDaoUtil() {
    }

    private static class ViewHolder{
        private static final HotSelectDaoUtil INSTANCE=new HotSelectDaoUtil();
    }

    public static HotSelectDaoUtil getInstance(){
        return ViewHolder.INSTANCE;
    }
    @Override
    public boolean insertBean(BaseBean clazz) {
        boolean flag = mManager.getmDaoSession().getHotSelectDao().insert((HotSelect)clazz) == -1 ? false : true;
        Log.i("", "insert Menu :" + flag + "-->" + clazz.toString());
        return flag;
    }

    @Override
    public ArrayList QueryAllBean() {
        return (ArrayList) mManager.getmDaoSession().getHotSelectDao().loadAll();
    }

    @Override
    public boolean deleteBean(BaseBean clazz) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().getHotSelectDao().delete((HotSelect)clazz);
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
            mManager.getmDaoSession().getHotSelectDao().deleteAll();
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
    public List<HotSelect> queryHotSelectByKey(String key){
        Query<HotSelect> nQuery = mManager.getmDaoSession().queryBuilder(HotSelect.class).where(HotSelectDao.Properties.Key.eq(key)).build();
        return nQuery==null?null:nQuery.list();
    }

}
