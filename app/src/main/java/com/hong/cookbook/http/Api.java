package com.hong.cookbook.http;

import com.hong.mvplib.retrofit.BaseApiImpl;

/**
 * Created by Administrator on 2018/3/1.
 */

public class Api extends BaseApiImpl {

    private static Api api = new Api(HttpApi.BASE_URL);

    public Api(String baseUrl) {
        super(baseUrl);
    }

    public static HttpApi getInstance() {
        return api.getRetrofit().create(HttpApi.class);
    }


}
