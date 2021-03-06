package com.linsaya.heima_googleplay.UI.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.linsaya.heima_googleplay.R;
import com.linsaya.heima_googleplay.UI.view.PagerTab;
import com.linsaya.heima_googleplay.UI.fragmentfactory.BaseFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.FragmentFactory;
import com.linsaya.heima_googleplay.utils.UIUtils;


public class MainActivity extends BaseActivity {

    private PagerTab pt_title;
    private ViewPager vp_content;
    private String[] mTabname;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在actionbar中显示应用图标
        initActionBar();
        initUI();
        initData();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        //新建一个ActionBarDrawerToggle，用来打开drawLayout侧边栏
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //绑定顶部返回按键与ActionBarDrawerToggle开关按键
            case android.R.id.home:
                toggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        //设置PagerTab的点击事件
        pt_title.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.creatFragment(position);
                System.out.println("当前状态为：main");
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
