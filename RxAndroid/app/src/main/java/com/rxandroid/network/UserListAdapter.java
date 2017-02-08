package com.rxandroid.network;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxandroid.NetWorkActivity;
import com.rxandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis1022 on 2017/2/8.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {

    private List<GitHubUser> mGitHubUserList;

    private NetWorkActivity.UserClickCallBack mUserClickCallBack;

    public UserListAdapter(NetWorkActivity.UserClickCallBack userClickCallBack) {
        mGitHubUserList = new ArrayList<>();
        mUserClickCallBack = userClickCallBack;
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_network, parent, false);
        return new UserListViewHolder(item, mUserClickCallBack);
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mGitHubUserList.size();
    }
}
