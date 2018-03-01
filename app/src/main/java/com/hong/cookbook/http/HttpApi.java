package com.hong.cookbook.http;

import com.hong.cookbook.bean.CategoryInfoBean;
import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.bean.CookMenuBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/2/9.
 */

public interface HttpApi {

    public static final String BASE_URL="http://apicloud.mob.com/v1/cook/";

    //获取所有的菜系
    @GET("category/query")
    Observable<CookBean> getAllCategorys(@Query("key") String key);

    //根据标签查询菜谱
    @GET("menu/search")
    Observable<CookMenuBean> getMenuCooks(@Query("page") int page,
                                                      @Query("name")String name,
                                                      @Query("cid")String cid,
                                                      @Query("key")String key,
                                                      @Query("size")int size
                                                      );

}
