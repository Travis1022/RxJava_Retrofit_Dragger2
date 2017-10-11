package com.rxandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rxandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RxAndroid的使用
 * Created by Travis1022 on 2017/2/7.
 */
public class MainActivity extends BasicActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_simple, R.id.btn_more, R.id.btn_lambda,
            R.id.btn_network, R.id.btn_safe, R.id.btn_binding,
            R.id.btn_android, R.id.btn_view})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simple:
                SimpleModule();
                break;
            case R.id.btn_more:
                MoreModule();
                break;
            case R.id.btn_lambda:
                LambdaModule();
                break;
            case R.id.btn_network:
                NetworkModule();
                break;
            case R.id.btn_safe:
                SafeModule();
                break;
            case R.id.btn_binding:
                BindModule();
                break;
            case R.id.btn_android:
                LearnAndroid();
                break;

            case R.id.btn_view:
                showMyView();
                break;
        }
    }

    //自定义View
    private void showMyView() {
        startActivity(new Intent(this, MyViewActivity.class));
    }


    //简单使用
    private void SimpleModule() {
        startActivity(new Intent(this, SimpleActivity.class));
    }

    //更多使用
    private void MoreModule() {
        startActivity(new Intent(this, MoreActivity.class));
    }

    //Lambda使用
    private void LambdaModule() {
        startActivity(new Intent(this, LambdaActivity.class));
    }

    //网络使用
    private void NetworkModule() {
        startActivity(new Intent(this, NetWorkActivity.class));
    }

    //线程安全
    private void SafeModule() {
        startActivity(new Intent(this, SafeActivity.class));
    }

    //Binding RxAndroid
    private void BindModule() {
        startActivity(new Intent(this, BindingActivity.class));
    }

    //android基础知识
    private void LearnAndroid() {
        startActivity(new Intent(this, LearnAndroidActivity.class));
    }

}
