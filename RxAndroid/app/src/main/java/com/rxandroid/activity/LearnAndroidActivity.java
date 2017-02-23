package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.rxandroid.R;
import com.rxandroid.fragment.ComponentFragment;
import com.rxandroid.fragment.DataFragment;
import com.rxandroid.fragment.GeneralFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 动态Fragment
 * 根据数据来确定Fragment的个数
 * Created by Travis on 2017/2/23.
 */
public class LearnAndroidActivity extends AppCompatActivity {
    @Bind(R.id.tab_show)
    TabLayout mTabShow;
    @Bind(R.id.vp_show)
    ViewPager mVpShow;

    private PagerAdapter mAdapter;

    private List<String> titleList;
    private SparseArray<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        ButterKnife.bind(this);

        initFragment();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return Fragment.instantiate(LearnAndroidActivity.this, ComponentFragment.class.getName());
                } else if (position == 1) {
                    return Fragment.instantiate(LearnAndroidActivity.this, DataFragment.class.getName());
                } else {
                    return fragmentList.get(position - 2);
                }
            }

            @Override
            public int getCount() {
                return 2 + fragmentList.size();
            }
        };
        mVpShow.setAdapter(mAdapter);
        mVpShow.setOffscreenPageLimit(fragmentList.size() + 2);
        mTabShow.setupWithViewPager(mVpShow);
        //设置tab名
        mTabShow.getTabAt(0).setText("四大组件");
        mTabShow.getTabAt(1).setText("数据存储");
        for (int i = 0; i < titleList.size(); i++) {
            mTabShow.getTabAt(i + 2).setText(titleList.get(i));
        }
    }

    private void initFragment() {
        titleList = new ArrayList<>();
        titleList.add("测试数据1");
        titleList.add("测试数据2");
        titleList.add("测试数据3");
        titleList.add("测试数据4");
        fragmentList = new SparseArray<>();
        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.put(i, Fragment.instantiate(LearnAndroidActivity.this, GeneralFragment.class.getName()));
        }
    }
}
