package com.linsaya.heima_googleplay.UI.fragmentfactory;

import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.AppFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.CategoryFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.GameFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.HomeFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.HotFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.RecommendFragment;
import com.linsaya.heima_googleplay.UI.fragmentfactory.fragment.SubjectFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/26.
 */

public class FragmentFactory {
    public static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();;

    public static BaseFragment creatFragment(int position) {

        //从hashmap中获取缓存的fragment，判断如果对象不空时，则新建fragment

        BaseFragment baseFragment = mFragmentMap.get(position);
        if (baseFragment == null) {

            switch (position) {
                case 0:
                    baseFragment = new HomeFragment();
                    break;
                case 1:
                    baseFragment = new AppFragment();
                    break;
                case 2:
                    baseFragment = new GameFragment();
                    break;
                case 3:
                    baseFragment = new SubjectFragment();
                    break;
                case 4:
                    baseFragment = new RecommendFragment();
                    break;
                case 5:
                    baseFragment = new CategoryFragment();
                    break;
                case 6:
                    baseFragment = new HotFragment();
                    break;
                default:
                    break;
            }
            //讲新建的fragment放入HashMap中
            mFragmentMap.put(position, baseFragment);
        }

        return baseFragment;
    }
}
