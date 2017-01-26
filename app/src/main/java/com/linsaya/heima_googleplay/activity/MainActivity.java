package com.linsaya.heima_googleplay.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.fragmentfactory.FragmentFactory;
import com.linsaya.heima_googleplay.ui.PagerTab;
import com.linsaya.heima_googleplay.utils.UIUtils;


public class MainActivity extends BaseActivity {

    private PagerTab pt_title;
    private ViewPager vp_content;
    private String[] mTabname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在actionbar中显示应用图标
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initUI();
        initData();
    }

    private void initData() {

    }

    private void initUI() {
        pt_title = (PagerTab) findViewById(R.id.pt_title);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setAdapter(new MyAdapter(getSupportFragmentManager()));
        pt_title.setViewPager(vp_content);
    }

    //设置FragmentPagerAdapter，往viewpager里面填充fragment
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabname = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabname[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.creatFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabname.length;
        }
    }
}
