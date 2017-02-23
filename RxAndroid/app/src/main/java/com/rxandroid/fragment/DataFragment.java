package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxandroid.R;

/**
 * Android:数据存储
 * 1>SharedPreference
 * 2>Sqlite
 * 3>File
 * Created by Travis1022 on 2017/2/23.
 */
public class DataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }
}
