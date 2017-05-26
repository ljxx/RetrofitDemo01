package com.ylx.retrofitdemo01.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ylx.retrofitdemo01.util.NetworkUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ========================================
 * <p/>
 * 版 权：蓝吉星讯 版权所有 （C） 2017
 * <p/>
 * 作 者：yanglixiang
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/5/26  下午2:25
 * <p/>
 * 描 述：
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class BaseActivity extends AppCompatActivity {
    public ProgressDialog pd;
    public ObservableTransformer<Observable, ObservableSource> composeFunction;
    private final long RETRY_TIMES = 1;
    private boolean showLoading = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        pd = new ProgressDialog(this);
        composeFunction = new ObservableTransformer<Observable, ObservableSource>() {
            @Override
            public ObservableSource apply(@NonNull Observable observable) {
                return observable.retry(RETRY_TIMES)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if(NetworkUtil.isNetworkAvailable(BaseActivity.this)){
                                    if(showLoading){
                                        if(pd != null && !pd.isShowing()) {
                                            pd.show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(BaseActivity.this, "网络连接异常，请连接网络", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public void setLoadingFlag(boolean show) {
        showLoading = show;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
