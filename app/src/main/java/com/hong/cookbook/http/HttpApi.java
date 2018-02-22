package com.hong.cookbook.http;

import com.hong.cookbook.bean.CategoryInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/2/9.
 */

public interface HttpApi {

    //获取所有的菜系
    @GET("category/query")
    Observable<HttpResult<CategoryInfoBean>> getAllCategorys(@Query("key") String key);


}
