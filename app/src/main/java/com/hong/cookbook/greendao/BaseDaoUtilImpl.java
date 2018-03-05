package com.hong.cookbook.greendao;

import android.util.Log;

import com.hong.cookbook.GreenDaoManager;

/**
 * Created by Administrator on 2018/3/3.
 */

public abstract class BaseDaoUtilImpl implements BaseDaoUtil {

    protected GreenDaoManager mManager;
    public BaseDaoUtilImpl() {
        mManager = GreenDaoManager.getInstance();
    }

}
