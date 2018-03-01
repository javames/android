package com.hong.mvplib.retrofit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface BaseApi {

    Retrofit getRetrofit();

    OkHttpClient.Builder setInterceptor(Interceptor interceptor);

    Retrofit.Builder setConverterFactory(Converter.Factory factory);
}
