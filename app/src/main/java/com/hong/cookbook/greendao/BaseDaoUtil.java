package com.hong.cookbook.greendao;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/3.
 */

public interface BaseDaoUtil<T extends BaseBean> {

     boolean insertBean(T clazz);

     ArrayList<T> QueryAllBean();

     boolean deleteBean(T clazz);

     public boolean deleteAll();

     public boolean updateBean(T clazz);

}
