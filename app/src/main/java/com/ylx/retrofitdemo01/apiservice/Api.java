package com.ylx.retrofitdemo01.apiservice;

import com.ylx.retrofitdemo01.bean.LoginRequest;
import com.ylx.retrofitdemo01.bean.LoginResponse;
import com.ylx.retrofitdemo01.bean.RegisterRequest;
import com.ylx.retrofitdemo01.bean.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/22  上午10:37
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public interface Api {

    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

}
