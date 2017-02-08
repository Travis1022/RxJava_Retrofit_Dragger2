package com.rxandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RxAndroid的使用
 * Created by Travis1022 on 2017/2/7.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_simple, R.id.btn_more, R.id.btn_lambda, R.id.btn_network, R.id.btn_safe, R.id.btn_binding})
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
        }
    }


    //简单使用
    public void SimpleModule() {
        startActivity(new Intent(this, SimpleActivity.class));
    }

    //更多使用
    public void MoreModule() {
        startActivity(new Intent(this, MoreActivity.class));
    }

    //Lambda使用
    public void LambdaModule() {
        startActivity(new Intent(this, LambdaActivity.class));
    }

    //网络使用
    public void NetworkModule() {
        startActivity(new Intent(this, NetWorkActivity.class));
    }

    //线程安全
    public void SafeModule() {
        startActivity(new Intent(this, SafeActivity.class));
    }

    //Binding RxAndroid
    private void BindModule() {
        startActivity(new Intent(this, BindingActivity.class));
    }


}
