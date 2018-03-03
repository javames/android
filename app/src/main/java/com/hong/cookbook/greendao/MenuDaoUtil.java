package com.hong.cookbook.greendao;

import android.content.Context;
import android.util.Log;

import com.hong.cookbook.GreenDaoManager;
import com.hong.cookbook.bean.Menu;
import com.hong.cookbook.gen.MenuDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MenuDaoUtil {

    private GreenDaoManager mManager;

    public MenuDaoUtil(Context context){
        mManager = GreenDaoManager.getInstance();
    }

    /**
     * 完成meizi记录的插入，如果表未创建，先创建Meizi表
     * @param menu
     * @return
     */
    public boolean insertMenu(Menu menu){
        boolean flag = false;
        flag = mManager.getmDaoSession().getMenuDao().insert(menu) == -1 ? false : true;
        Log.i("", "insert Menu :" + flag + "-->" + menu.toString());
        return flag;
    }

    /**
     * 修改一条数据
     * @param menu
     * @return
     */
    public boolean updateMenu(Menu menu){
        boolean flag = false;
        try {
            mManager.getmDaoSession().getMenuDao().update(menu);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查找所有数据
     */

    public void QueryAllMenu(){
        List<Menu> menus = mManager.getmDaoSession().getMenuDao().loadAll();
    }
    /**
     * 删除单条记录
     * @param menu
     * @return
     */
    public boolean deleteMeizi(Menu menu){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().getMenuDao().delete(menu);
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
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getmDaoSession().deleteAll(Menu.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<Menu> queryMenuByNativeSql(String sql, String[] conditions){
        return mManager.getmDaoSession().queryRaw(Menu.class, sql, conditions);
    }

    /**
     * 根据menuId查询
     */
    public List<Menu> queryMenuByMenuId(String condition){
        Query<Menu> nQuery = mManager.getmDaoSession().queryBuilder(Menu.class).where(MenuDao.Properties.MenuId.eq(condition)).build();
        return nQuery==null?null:nQuery.list();
    }

}
