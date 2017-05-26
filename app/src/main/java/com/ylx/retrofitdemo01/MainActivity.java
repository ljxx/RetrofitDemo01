package com.ylx.retrofitdemo01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ylx.retrofitdemo01.base.BaseActivity;
import com.ylx.retrofitdemo01.base.BaseObserver;
import com.ylx.retrofitdemo01.bean.Person;
import com.ylx.retrofitdemo01.bean.Repo;
import com.ylx.retrofitdemo01.bean.User;
import com.ylx.retrofitdemo01.retrofit.LocalRetrofit;
import com.ylx.retrofitdemo01.retrofit.RetroFactory;
import com.ylx.retrofitdemo01.retrofit.RetrofitTest;
import com.ylx.retrofitdemo01.util.BaseTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "=====MainActivity=====";

    private TextView responseBtn,get_local_data,rxjavaBtn,constraintLayout_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        /**
         * Retrofit2小试牛刀
         */
        responseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Repo>> response = RetrofitTest.getResponseData();
                response.enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        List<Repo> listData = response.body();
                        final StringBuilder sb = new StringBuilder();
                        for (Repo repo: listData){
                            sb.append("id: " + repo.getId() + "====== name: " + repo.getName() + "\n");
                            Log.i("========","======="+repo.getId() + "===="+repo.getName());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {

                    }
                });
            }
        });

        /**
         * 获取本地服务器数据
         */
        get_local_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getTest();
//                getUser();
                getUsers();
            }
        });

        /**
         * Rxjava2小试牛刀
         */
        rxjavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                studyRxjava2Second();
                rxjavaUpdateThread();
                Toast.makeText(MainActivity.this,"请求数据：", Toast.LENGTH_LONG).show();
                /*Flowable.just("Hello World").subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Toast.makeText(MainActivity.this,"打印出值：" + s, Toast.LENGTH_LONG).show();
                        System.out.println(s);
                    }
                });*/
            }
        });

        /**
         * constraintLayout_text
         */
        constraintLayout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DaggerTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private Observable observable;
    private BaseObserver baseObserver;

    /**
     * 仿真版请求本地服务器数据，rxJava2 + retrofit2
     */
    private void getUsers(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "张三丰");
        map.put("age", "123");

        observable = RetroFactory.getInstance().getUser(map);
        baseObserver = new BaseObserver<User>(MainActivity.this, pd) {

            @Override
            public void onHandleSuccess(User user) {
                get_local_data.setText("name = " + user.getName() + ",age = " + user.getAge());
            }
        };
        observable.compose(composeFunction).subscribe(baseObserver);
    }

    /**
     * 仿真版请求本地服务器json数据
     */
    private void getUser(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "张三丰");
        map.put("age", "90");

        new BaseTask<List<Person>>(this, LocalRetrofit.getStringService().getUsers(map)).handlerResponse(new BaseTask.ResonseLinsteren<List<Person>>() {

            @Override
            public void onSuccess(List<Person> persons) {
                for(int i = 0; i < persons.size(); i ++){
                    Person person = persons.get(i);
                    Log.i(TAG, "name = " + person.getName() + " age = " + person.getAge());
                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    /**
     * 获取本地服务器数据
     */
    private void getTest(){
        Call<String> call = (Call<String>) LocalRetrofit.getStringService().getTest();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.errorBody() == null){
                    Toast.makeText(MainActivity.this, "str = " + response.body().toString(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "str = " + response.body().toString());
                    postTest();
                } else {
                    Toast.makeText(MainActivity.this, "error code = " + response.code() + "=== error message = " + response.message(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "error code = " + response.code());
                    Log.i(TAG, "error message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "fail=" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG,"fail=" + t.getMessage());
            }
        });
    }

    /**
     * postTest
     */
    private void postTest(){
        Call<Void> call = LocalRetrofit.getStringService().createPerson("张三丰", "admin123");
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /**
     * rxjava当前线程-验证当前线程在主线程
     */
    private void rxjavaThread(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG,"=Observable=thread=is=="+Thread.currentThread().getName());
                e.onNext(1);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG,"=subscribe=thread=is=="+Thread.currentThread().getName());
                Log.d(TAG,"==="+integer);
            }
        });
    }

    /**
     * 更改线程，让其在子线程发送事件，在主线程执行
     */
    private void rxjavaUpdateThread(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG,"=Observable=thread=is=="+Thread.currentThread().getName());
                e.onNext(1);
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG,"=subscribe=thread=is=="+Thread.currentThread().getName());
                Log.d(TAG,"==="+integer);
            }
        });
    }

    /**
     * 将方法studyRxjava2Fist()合并一起写
     */
    private void studyRxjava2Second(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1001);
                e.onNext(1002);
                e.onNext(1003);
//                e.onComplete();
//                e.onError(new Throwable());
                e.onNext(1004);
                e.onNext(1005);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("====observer===","=onSubscribe===");
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i("====observer===","===="+integer);
                if(integer == 1003){
                    disposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("====observer===","==onError==");
            }

            @Override
            public void onComplete() {
                Log.i("====observer===","==onComplete加载完成==");
            }
        });
    }

    /**
     * 分布式写法
     */
    private void studyRxjava2Fist(){
        /**
         * 1、创建一个上游Observer
         */
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        /**
         * 2、创建一个下游 Observer
         */
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("====observer===","=onSubscribe===");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i("====observer===","===="+integer);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("====observer===","==onError==");
            }

            @Override
            public void onComplete() {
                Log.i("====observer===","==onComplete加载完成==");
            }
        };

        /**
         * 3、建立连接
         */
        observable.subscribe(observer);
    }

    private void initView() {
        responseBtn = (TextView) findViewById(R.id.retrofit2_btn);
        get_local_data = (TextView) findViewById(R.id.get_local_data);
        rxjavaBtn = (TextView) findViewById(R.id.rxjava2_btn);
        constraintLayout_text = (TextView) findViewById(R.id.constraintLayout_text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(baseObserver != null){
            baseObserver.closeResponse();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(baseObserver != null){
//            baseObserver.closeResponse();
//        }
    }
}
