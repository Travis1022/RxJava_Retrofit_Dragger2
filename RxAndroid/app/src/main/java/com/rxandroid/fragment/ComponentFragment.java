package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxandroid.R;

/**
 * Android四大组件：
 * Activity,Service,ContentProvider,BroadCast
 * Created by Travis1022 on 2017/2/23.
 */
public class ComponentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_component, container, false);
        return rootView;
    }
}
