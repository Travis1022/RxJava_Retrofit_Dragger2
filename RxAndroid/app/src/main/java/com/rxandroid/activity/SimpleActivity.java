package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.rxandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 简单的RxAndroid使用方法
 * Created by Travis1022 on 2017/2/7.
 */
public class SimpleActivity extends AppCompatActivity {

    @Bind(R.id.tv_simple)
    TextView mTvSimple;

    //观察事件发生
    private Observable.OnSubscribe mOnSubscribe = new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(sayHello());   //发送事件
            subscriber.onCompleted();        //完成事件
        }
    };

    //订阅者:接收字符串，显示文本
    private Subscriber<String> mTextSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            mTvSimple.setText(s);
        }
    };

    //订阅者：接收字符串，提示信息
    private Subscriber<String> mToastSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Toast.makeText(SimpleActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        //注册观察活动
        Observable<String> observable = Observable.create(mOnSubscribe);

        //分发订阅信息
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(mTextSubscriber);
        observable.subscribe(mToastSubscriber);
    }


    private String sayHello() {
        return "Hello,this is a begin of RxAndroid";
    }
}
