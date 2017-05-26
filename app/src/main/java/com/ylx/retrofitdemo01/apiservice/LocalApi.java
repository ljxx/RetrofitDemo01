package com.ylx.retrofitdemo01.apiservice;

import com.ylx.retrofitdemo01.bean.BaseEntity;
import com.ylx.retrofitdemo01.bean.Person;
import com.ylx.retrofitdemo01.bean.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/26  上午10:13
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public interface LocalApi {
    @GET("ServletTest")
    Call<String> getTest();

    /**
     * FormUrlEncoded表示对POST请求的参数进行编码，这是Retrofit2强制要求的，否则运行会报错，@POST注解表示这是一个POST方法，
     * createUser是请求的名称，这里假设我只提交参数，不需要返回值，所以Call里的泛型是Void，完后方法里定义了2个参数注解，
     * 名字和年龄，表示客户端要传的2个参数
     * @param name
     * @param password
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("ServletTest")
    Call<Void> createPerson(@Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("ServletTest")
    Call<BaseEntity<List<Person>>> getUsers(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("ServletTest")
    Observable<BaseEntity<User>> getUser(@FieldMap Map<String, String> map);
}
