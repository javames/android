package com.hong.cookbook;

import com.hong.cookbook.gen.DaoMaster;
import com.hong.cookbook.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Administrator on 2018/2/26.
 */

public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper sHelper;
    private GreenDaoManager()
    {
        init();
    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder
    {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static GreenDaoManager getInstance()
    {
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init()
    {
        sHelper = new DaoMaster.DevOpenHelper(ResetApplication.getContext(),
                "menu_detail");
        mDaoMaster = new DaoMaster(sHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoMaster getmDaoMaster()
    {
        return mDaoMaster;
    }
    public DaoSession getmDaoSession()
    {
        return mDaoSession;
    }
    public DaoSession getNewSession()
    {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }


    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(mDaoSession != null){
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

}
