package com.rxjavademo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rxjavademo.BaseFragment;
import com.rxjavademo.R;
import com.rxjavademo.adapter.ElementaryListAdapter;
import com.rxjavademo.model.ElementaryImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * Created by xuanwei.tian on 2016/10/24.
 */
public class ElementaryFragment extends BaseFragment {


    @Bind(R.id.rv_ele_grid)
    RecyclerView mRvEleGrid;
    @Bind(R.id.layout_swipe_fresh)
    SwipeRefreshLayout mLayoutSwipeFresh;

    private ElementaryListAdapter mElementaryListAdapter = new ElementaryListAdapter();

    //RxJava
    Observer<List<ElementaryImage>> mObserver = new Observer<List<ElementaryImage>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mLayoutSwipeFresh.setRefreshing(false);
            Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<ElementaryImage> elementaryImages) {
            mLayoutSwipeFresh.setRefreshing(false);
            //给adapter传递数据
            mElementaryListAdapter.setImages(elementaryImages);
        }
    };

    //使用RxJava
    private void search(String key) {
/*//        mSubscription = */
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_elementary, container, false);
        ButterKnife.bind(this, rootView);
        // 设置RecycleView显示GridView的效果,每行两个item
        mRvEleGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRvEleGrid.setAdapter(mElementaryListAdapter);
        mLayoutSwipeFresh.setEnabled(false);
        return rootView;
    }

    @Override
    protected int getDialogRes() {
        return 0;
    }

    @Override
    protected int getTitleRes() {
        return 0;
    }


}
