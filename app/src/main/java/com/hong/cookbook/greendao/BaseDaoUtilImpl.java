package com.hong.cookbook.greendao;

import com.hong.cookbook.GreenDaoManager;

/**
 * Created by Administrator on 2018/3/3.
 */

public class BaseDaoUtilImpl implements BaseDaoUtil {

    protected GreenDaoManager mManager;
    public BaseDaoUtilImpl() {
        mManager = GreenDaoManager.getInstance();
    }

    @Override
    public boolean insertBean(T clazz) {
        return false;
    }


}
