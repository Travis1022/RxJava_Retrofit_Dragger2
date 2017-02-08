package com.rxandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.design.widget.RxSnackbar;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * RxBinding是Rx中处理控件异步调用的方式, 也是由Square公司开发, Jake负责编写.
 * 通过绑定组件, 异步获取事件, 并进行处理. 编码风格非常优雅.
 * Created by Travis1022 on 2017/2/8.
 */
public class BindingActivity extends AppCompatActivity {
    @Bind(R.id.tb_binding)
    Toolbar mTbBinding;
    @Bind(R.id.et_standard)
    EditText mEtStandard;
    @Bind(R.id.et_click)
    EditText mEtClick;
    @Bind(R.id.tv_binding_show)
    TextView mTvBindingShow;
    @Bind(R.id.fb_binding)
    FloatingActionButton mFbBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        ButterKnife.bind(this);

        initToolbar();   //初始化Toolbar
        initFabButton(); //初始化Fab按钮
        initEditText();  //初始化编辑文本
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_binding, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        setSupportActionBar(mTbBinding); //添加菜单按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {            //添加浏览按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        RxToolbar.itemClicks(mTbBinding).subscribe(new Action1<MenuItem>() {
            @Override
            public void call(MenuItem menuItem) {
                onToolbarItemClicked(menuItem);
            }
        });
        RxToolbar.navigationClicks(mTbBinding).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onToolbarNavigationClicked(aVoid);
            }
        });
    }

    //点击Toolbar
    private void onToolbarItemClicked(MenuItem menuItem) {
        String m = "点击\"" + menuItem.getTitle() + "\"";
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

    //浏览点击
    private void onToolbarNavigationClicked(Void aVoid) {
        Toast.makeText(this, "浏览点击", Toast.LENGTH_SHORT).show();
    }


    private void initFabButton() {
        RxView.clicks(mFbBinding).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onFabClicked(aVoid);
            }
        });
    }

    //点击Fab按钮
    private void onFabClicked(Void aVoid) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "点击SnackBar", Snackbar.LENGTH_SHORT);
        snackbar.show();
        RxSnackbar.dismisses(snackbar)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer event) {
                        onSnackbarDismissed(event);
                    }
                });
    }

    //销毁Snackbar
    private void onSnackbarDismissed(Integer event) {
        String text = "Snackbar消失代码：" + event;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    private void initEditText() {
        //正常方式
        mEtStandard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvBindingShow.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Rx方式
        RxTextView.textChanges(mEtClick).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                mEtClick.setText(charSequence);
            }
        });
    }


}
