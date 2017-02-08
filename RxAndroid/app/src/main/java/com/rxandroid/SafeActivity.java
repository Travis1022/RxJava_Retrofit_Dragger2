package com.rxandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Rx的好处之一就是可以防止内存泄露, 即根据页面生命周期, 处理异步线程的结束.
 * 可以使用RxLifecycle库处理生命周期.
 * Activity类继承RxAppCompatActivity, 替换AppCompatActivity.
 * Created by Travis1022 on 2017/2/8.
 */
public class SafeActivity extends RxAppCompatActivity {
    private static final String TAG = "SafeActivity";
    @Bind(R.id.tv_simple)
    TextView mTvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle()) //管理生命周期，防止内存泄漏
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long time) {
                        showTime(time);
                    }
                });
    }

    private void showTime(Long time) {
        mTvSimple.setText(String.valueOf("时间计数： " + time));
        Log.d(TAG, "时间计数器： " + time);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "页面关闭");
    }
}
