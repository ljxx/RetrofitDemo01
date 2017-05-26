package com.ylx.retrofitdemo01.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ylx.retrofitdemo01.bean.BaseEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/26  上午11:05
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class BaseTask<T> {
    private Call<BaseEntity<T>> mCall;
    private Context mContext;
    private static final int SUCCESS = 0;
    private final String TAG = "response";

    public BaseTask(Context context, Call call){
        this.mContext = context;
        this.mCall = call;
    }

    public void handlerResponse(final ResonseLinsteren linsteren){
        mCall.enqueue(new Callback<BaseEntity<T>>() {
            @Override
            public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
                if(response.isSuccessful() && response.errorBody() == null){
                    if(response.body().getCode() == SUCCESS){
                        linsteren.onSuccess((T)response.body().getData());
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        linsteren.onFail();
                    }
                } else {
                    Log.i(TAG, "error code = " + response.code());
                    Log.i(TAG, "error message = " + response.message());
                    Toast.makeText(mContext, "网络返回异常", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
                Log.d(TAG, "error: " + t.getMessage());
                Toast.makeText(mContext, "网络请求出现异常", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 设置监听
     */
    public interface ResonseLinsteren<T> {
        void onSuccess(T t);

        void onFail();
    }
}
