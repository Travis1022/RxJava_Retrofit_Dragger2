package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.rxandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义View中Fragment基类
 * Created by Travis1022 on 2017/10/9.
 */
public class PageFragment extends Fragment {

    @Bind(R.id.sample_stub)
    ViewStub mSampleStub;
    @Bind(R.id.view_stub)
    ViewStub mViewStub;

    @LayoutRes
    int sampleLayoutRes;
    @LayoutRes
    int myViewLayoutRes;

    public static PageFragment newInstance(@LayoutRes int sampleLayoutRes, @LayoutRes int myViewLayoutRes) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sampleLayoutRes", sampleLayoutRes);
        bundle.putInt("myViewLayoutRes", myViewLayoutRes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, rootView);
        mSampleStub.setLayoutResource(sampleLayoutRes);
        //ViewStub加载Layout文件
        mSampleStub.inflate();

        mViewStub.setLayoutResource(myViewLayoutRes);
        mViewStub.inflate();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            sampleLayoutRes = args.getInt("sampleLayoutRes");
            myViewLayoutRes = args.getInt("myViewLayoutRes");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
