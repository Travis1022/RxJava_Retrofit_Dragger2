package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.rxandroid.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Travis1022 on 2017/2/8.
 */
public class LambdaActivity extends AppCompatActivity {
    @Bind(R.id.tv_simple)
    TextView mTvSimple;
    final String[] mStrings = {"Hello", "I", "am", "your", "friend", "Travis1022"};
    final List<String> mStringList = Arrays.asList(mStrings);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        //文本框显示字符串
        Observable<String> observable = Observable.just(sayHello());
        observable.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase)
                .subscribe(mTvSimple::setText);
        //Toast显示字符
        Observable<String> observableToast = Observable.from(mStringList);
        observableToast.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase)
                .subscribe(this::showToast);
        //优化后的代码
        Observable.just(mStringList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .reduce(this::connectString)
                .subscribe(this::showToast);

    }

    private String sayHello() {
        return "Hello, I am your friend, Travis1022";
    }

    private void showToast(String s) {
        Toast.makeText(LambdaActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private String connectString(String s1, String s2) {
        return String.format("%s %s", s1, s2);
    }
}
