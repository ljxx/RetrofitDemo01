package com.ylx.retrofitdemo01.retrofit;

import com.ylx.retrofitdemo01.apiservice.LocalApi;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/26  上午10:14
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class LocalRetrofit {
    private static String baseUrl = "http://192.168.1.108:8080/WenBoWeb/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public static LocalApi getStringService(){
        LocalApi localApi = retrofit.create(LocalApi.class);
        return localApi;
    }
}
