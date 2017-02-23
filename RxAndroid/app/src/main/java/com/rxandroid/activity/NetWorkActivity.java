package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rxandroid.R;
import com.rxandroid.network.NetworkWrapper;
import com.rxandroid.network.UserListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RxAndroid在网络请求上的应用
 * Created by Travis1022 on 2017/2/7.
 */
public class NetWorkActivity extends AppCompatActivity {

    @Bind(R.id.rv_show)
    RecyclerView mRvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        //设置layout管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvShow.setLayoutManager(layoutManager);

        //设置适配器
        UserListAdapter userListAdapter = new UserListAdapter(new UserClickCallBack() {
            @Override
            public void onItemClicked(String name) {
                gotoDetailPage(name);
            }
        });
        NetworkWrapper.getUserInfo(userListAdapter);
        mRvShow.setAdapter(userListAdapter);
    }

    private void gotoDetailPage(String name) {
        startActivity(NetWorkDetailActivity.from(NetWorkActivity.this, name));
    }

    //点击回调
    public interface UserClickCallBack {
        void onItemClicked(String name);
    }
}
