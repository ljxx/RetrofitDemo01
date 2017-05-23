package com.ylx.retrofitdemo01.bean;

import javax.inject.Inject;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/23  上午10:01
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class Poetry {
    private String mPemo;

    //用@Inject标记构造函数，表示用它来注入到目标对象中去
    @Inject
    public Poetry(){
        mPemo = "生活就像海洋";
    }

    public String getPemo(){
        return mPemo;
    }
}
