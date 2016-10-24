package com.rxjavademo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rxjavademo.fragment.ElementaryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @Bind(R.id.tabs_main)
    TabLayout mTabsMain;
    @Bind(R.id.vp_main)
    ViewPager mVpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarMain);

        mVpMain.setAdapter(new android.support.v13.app.FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ElementaryFragment();
                }

                return null;
            }

            @Override
            public int getCount() {
                return 6;
            }
        });


    }
}
