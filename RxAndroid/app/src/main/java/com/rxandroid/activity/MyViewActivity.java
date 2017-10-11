package com.rxandroid.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rxandroid.R;
import com.rxandroid.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义view
 * Create by Travis1022 on 2017-10-09
 */
public class MyViewActivity extends BasicActivity {

    @Bind(R.id.tab_view)
    TabLayout mTabView;    //Tab
    @Bind(R.id.vp_view)
    ViewPager mVpView;     //viewPager

    List<PageModel> mPageModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        ButterKnife.bind(this);

        // ArrayList
        mPageModelList.add(new PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color));
        mPageModelList.add(new PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle));
        mPageModelList.add(new PageModel(R.layout.sample_rect, R.string.title_draw_rect, R.layout.practice_rect));
        mPageModelList.add(new PageModel(R.layout.sample_point, R.string.title_draw_point, R.layout.practice_point));
        mPageModelList.add(new PageModel(R.layout.sample_oval, R.string.title_draw_oval, R.layout.practice_oval));
        mPageModelList.add(new PageModel(R.layout.sample_line, R.string.title_draw_line, R.layout.practice_line));
        mPageModelList.add(new PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect, R.layout.practice_round_rect));
        mPageModelList.add(new PageModel(R.layout.sample_arc, R.string.title_draw_arc, R.layout.practice_arc));
        mPageModelList.add(new PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path));
        mPageModelList.add(new PageModel(R.layout.sample_histogram, R.string.title_draw_histogram, R.layout.practice_histogram));
        mPageModelList.add(new PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart, R.layout.practice_pie_chart));


        mVpView.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = mPageModelList.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.myViewLayoutRes);
            }

            @Override
            public int getCount() {
                return mPageModelList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(mPageModelList.get(position).titleRes);
            }
        });
        //TabLayout 与 ViewPager 联动
        mTabView.setupWithViewPager(mVpView);
    }

    private class PageModel {
        @LayoutRes
        int sampleLayoutRes;
        @StringRes
        int titleRes;
        @LayoutRes
        int myViewLayoutRes;

        public PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes, @LayoutRes int myViewLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.myViewLayoutRes = myViewLayoutRes;
        }
    }
}
