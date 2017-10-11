package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Android四大组件：
 * Activity,Service,ContentProvider,BroadCast
 * Created by Travis1022 on 2017/2/23.
 */
public class ComponentFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_component, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_activity, R.id.btn_cast, R.id.btn_service, R.id.btn_provider})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Activity
            case R.id.btn_activity:
                break;
            //BroadCast
            case R.id.btn_cast:
                //静态广播、动态广播
                break;
            //Service
            case R.id.btn_service:
                break;
            //Provider
            case R.id.btn_provider:
                break;

        }
    }
}
