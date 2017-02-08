package com.rxandroid.network;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rxandroid.NetWorkActivity;
import com.rxandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Travis1022 on 2017/2/8.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

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
        holder.bindTo(mGitHubUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGitHubUserList.size();
    }

    public void addUser(GitHubUser user) {
        mGitHubUserList.add(user);
        notifyItemInserted(mGitHubUserList.size() - 1);
    }


    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_first_name)
        TextView mTvFirstName;
        @Bind(R.id.tv_second_name)
        TextView mTvSecondName;
        @Bind(R.id.iv_user)
        ImageView mIvUser;
        @Bind(R.id.tv_user_link)
        TextView mTvUserLink;

        public UserListViewHolder(View itemView, NetWorkActivity.UserClickCallBack clickCallBack) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClicked(mTvSecondName.getText().toString());
                }
            });
        }

        //绑定数据
        public void bindTo(GitHubUser user) {
            mTvFirstName.setText(user.name);
            mTvSecondName.setText(user.login);
            mTvUserLink.setText(user.repos_url);

            //Picasso:开源Android图形缓存库,实现图片的异步加载
            /*Picasso不仅实现了图片异步加载的功能，还解决了android中加载图片时需要解决的一些常见问题：
            1.在adapter中需要取消已经不在视野范围的ImageView图片资源的加载，否则会导致图片错位，Picasso已经解决了这个问题。
            2.使用复杂的图片压缩转换来尽可能的减少内存消耗
            3.自带内存和硬盘二级缓存功能*/
            Picasso.with(mIvUser.getContext())
                    .load(user.avatar_url)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(mIvUser);
        }
    }
}
