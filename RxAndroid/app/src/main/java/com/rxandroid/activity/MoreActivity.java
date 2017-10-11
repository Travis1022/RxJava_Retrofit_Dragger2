package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.rxandroid.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 更多的RxAndroid使用方法:主要是操作符
 * <p>
 * just: 获取输入数据, 直接分发, 更加简洁, 省略其他回调.
 * from: 获取输入数组, 转变单个元素分发.
 * map: 映射, 对输入数据进行转换, 如大写.
 * flatMap: 增大, 本意就是增肥, 把输入数组映射多个值, 依次分发.
 * reduce: 简化, 正好相反, 把多个数组的值, 组合成一个数据.
 * <p>
 * Created by Travis1022 on 2017/2/7.
 */
public class MoreActivity extends BasicActivity {

    @Bind(R.id.tv_more)
    TextView mTvMore;

    final String[] mStrings = {"Hello", "I", "am", "a", "tool", "named", "RxAndroid"};
    final List<String> mStringList = Arrays.asList(mStrings);

    //Action：设置TextView
    private Action1<String> mTextViewAction = new Action1<String>() {
        @Override
        public void call(String s) {
            mTvMore.setText(s);
        }
    };

    //Action: 设置Toast
    private Action1<String> mToastAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Toast.makeText(MoreActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    //映射函数
    private Func1<List<String>, Observable<String>> mListObservableFunc = new Func1<List<String>, Observable<String>>() {
        @Override
        public Observable<String> call(List<String> strings) {
            return Observable.from(strings);
        }
    };

    //设置大写字母
    private Func1<String, String> mUpperLetterFunc = new Func1<String, String>() {
        @Override
        public String call(String s) {
            return s.toUpperCase();
        }
    };

    //连接字符串
    private Func2<String, String, String> mConnectStringFunc = new Func2<String, String, String>() {
        @Override
        public String call(String s, String s2) {
            //空格连接字符串
            return String.format("%s %s", s, s2);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);

        //添加字符串
        Observable<String> observable = Observable.just(sayHello());
        //映射后显示
        observable.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc)
                .subscribe(mTextViewAction);

        //单独显示数组中的每个元素
        Observable<String> observableMap = Observable.from(mStringList);
        //映射后分发
        observableMap.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc)
                .subscribe(mToastAction);

        //优化代码：直接获取数组，再分发，再合并，再显示Toast
        Observable.just(mStringList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(mListObservableFunc)
                .reduce(mConnectStringFunc)
                .subscribe(mToastAction);
    }


    private String sayHello() {
        return "Hello,I am a tool named RxAndroid";
    }

}
