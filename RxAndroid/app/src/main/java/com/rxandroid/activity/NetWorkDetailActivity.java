package com.rxandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rxandroid.R;
import com.rxandroid.network.DetailListAdapter;
import com.rxandroid.network.NetworkWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Travis1022 on 2017/2/8.
 */
public class NetWorkDetailActivity extends BasicActivity {
    @Bind(R.id.rv_show)
    RecyclerView mRvShow;
    private static final String userNameTag = "user_name";

    public static Intent from(Context context, String username){
        Intent intent=new Intent(context,NetWorkDetailActivity.class);
        intent.putExtra(userNameTag,username);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        //设置布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvShow.setLayoutManager(layoutManager);

        //设置Adapter
        DetailListAdapter adapter = new DetailListAdapter();
        NetworkWrapper.getRepoData(adapter, getIntent().getStringExtra(userNameTag));
        mRvShow.setAdapter(adapter);

    }
}
