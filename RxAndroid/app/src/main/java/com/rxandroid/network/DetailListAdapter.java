package com.rxandroid.network;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rxandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Travis1022 on 2017/2/8.
 */

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {

    private List<GitHubRepo> mGitHubRepoList;

    public DetailListAdapter() {
        mGitHubRepoList = new ArrayList<>();
    }

    public void addDetail(GitHubRepo githubRepo) {
        mGitHubRepoList.add(githubRepo);
        notifyItemInserted(mGitHubRepoList.size() - 1);
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        holder.bindTo(mGitHubRepoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGitHubRepoList.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_detail)
        TextView mTvDetail;

        public DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(GitHubRepo gitHubRepo) {
            mTvName.setText(gitHubRepo.name);
            mTvDetail.setText(String.valueOf("description: " + gitHubRepo.description
                    + ", language: " + gitHubRepo.language));
        }
    }

}
