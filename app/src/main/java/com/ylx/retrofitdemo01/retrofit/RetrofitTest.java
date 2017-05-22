package com.ylx.retrofitdemo01.retrofit;

import com.ylx.retrofitdemo01.apiservice.GitHubService;
import com.ylx.retrofitdemo01.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/17  上午11:19
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class RetrofitTest {

    public static final String GITHUB_HTTP_URL = "https://api.github.com/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GITHUB_HTTP_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static GitHubService gitHubService = retrofit.create(GitHubService.class);

    public static Call<List<Repo>> getResponseData(){
        return gitHubService.listRepos("octocat");
    }


}
