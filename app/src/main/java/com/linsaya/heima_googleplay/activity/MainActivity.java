package com.linsaya.heima_googleplay.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.PagerTab;
import com.linsaya.heima_googleplay.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.fragmentfactory.FragmentFactory;
import com.linsaya.heima_googleplay.utils.UIUtils;


public class MainActivity extends AppCompatActivity {

    private PagerTab pt_title;
    private ViewPager vp_content;
    private String[] tabname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
    }

    private void initData() {
        tabname = UIUtils.getStringArray(R.array.tab_names);
    }

    private void initUI() {
        pt_title = (PagerTab) findViewById(R.id.pt_title);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        vp_content.setAdapter(new MyAdapter(getSupportFragmentManager()));
        pt_title.setViewPager(vp_content);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabname[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.creatFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return tabname.length;
        }
    }
}
