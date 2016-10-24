package com.rxjavademo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rxjavademo.R;
import com.rxjavademo.model.ElementaryImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Travis1022 on 2016/10/24.
 */
public class ElementaryListAdapter extends RecyclerView.Adapter {
    private List<ElementaryImage> mElementaryImages;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ElementaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ElementaryViewHolder eleViewHolder = (ElementaryViewHolder) holder;
        ElementaryImage image = mElementaryImages.get(position);
        //使用图片加载框架 compile 'com.github.bumptech.glide:glide:3.7.0'
        Glide.with(holder.itemView.getContext()).load(image.image_url).into(eleViewHolder.mIvShow);

        eleViewHolder.mTvDesc.setText(image.description);
    }

    @Override
    public int getItemCount() {
        return mElementaryImages == null ? 0 : mElementaryImages.size();

    }


    public void setImages(List<ElementaryImage> images) {
        this.mElementaryImages = images;
        notifyDataSetChanged();
    }

    //对应于gridView中item的ViewHolder
    static class ElementaryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_show)
        ImageView mIvShow;
        @Bind(R.id.tv_desc)
        TextView mTvDesc;

        public ElementaryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
