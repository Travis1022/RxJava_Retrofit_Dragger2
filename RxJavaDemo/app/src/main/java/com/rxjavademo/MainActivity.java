package com.rxjavademo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rxjavademo.fragment.AdvancedTokenFragment;
import com.rxjavademo.fragment.CacheFragment;
import com.rxjavademo.fragment.ElementaryFragment;
import com.rxjavademo.fragment.MapFragment;
import com.rxjavademo.fragment.TokenFragment;
import com.rxjavademo.fragment.ZipFragment;

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

         //setSupportActionBar(mToolbarMain);

        mVpMain.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ElementaryFragment();
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new ZipFragment();
                    case 3:
                        return new TokenFragment();
                    case 4:
                        return new AdvancedTokenFragment();
                    case 5:
                        return new CacheFragment();
                    default:
                        return new ElementaryFragment();
                }

            }

            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_elementary);
                    case 1:
                        return getString(R.string.title_map);
                    case 2:
                        return getString(R.string.title_zip);
                    case 3:
                        return getString(R.string.title_token);
                    case 4:
                        return getString(R.string.title_advanced_token);
                    case 5:
                        return getString(R.string.title_cache);
                    default:
                        return getString(R.string.title_elementary);

                }
            }
        });

        mTabsMain.setupWithViewPager(mVpMain);
    }
}
