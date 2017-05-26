package com.ylx.retrofitdemo01.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ylx.retrofitdemo01.bean.BaseEntity;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/26  下午2:56
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private Context mContext;
    private ProgressDialog mDialog;
    private Disposable mDisposable;
    private final int SUCCESS_CODE = 0;

    public BaseObserver(Context context, ProgressDialog progressDialog) {
        this.mContext = context;
        this.mDialog = progressDialog;

//        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Toast.makeText(mContext, "取消数据加载", Toast.LENGTH_LONG).show();
//                mDisposable.dispose();
//            }
//        });
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onNext(@NonNull BaseEntity<T> tBaseEntity) {
        if(tBaseEntity.getCode() == SUCCESS_CODE){
            T t = tBaseEntity.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(tBaseEntity.getCode(), tBaseEntity.getMessage());
        }

    }


    public abstract void onHandleSuccess(T t);

    public void onHandleError(int code, String message) {
        Toast.makeText(mContext, "code = " + code + "===message = " + message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onError(@NonNull Throwable e) {
        Log.d("====", "Error : " + e.getMessage());
        dissmissDialog();

        Toast.makeText(mContext, "网络请求异常，请稍后再试！", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onComplete() {

        Log.d("===", "====onComplete====");
        dissmissDialog();

    }

    /**
     * 关闭加载进度框
     */
    private void dissmissDialog(){
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void closeResponse(){
        if(mDisposable != null){
            mDisposable.dispose();
        }
    }
}
