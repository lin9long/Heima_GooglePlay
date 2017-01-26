package com.linsaya.heima_googleplay.fragmentfactory;

import com.linsaya.heima_googleplay.fragmentfactory.fragment.AppFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.CategoryFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.GameFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.HomeFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.HotFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.RecommendFragment;
import com.linsaya.heima_googleplay.fragmentfactory.fragment.SubjectFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/26.
 */

public class FragmentFactory {
    public static HashMap<Integer, BaseFragment> fragmentMap;

    public static BaseFragment creatFragment(int position) {

        fragmentMap = new HashMap<>();
        BaseFragment baseFragment = fragmentMap.get(position);
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
            }
        }
        fragmentMap.put(position, baseFragment);
        return baseFragment;
    }
}
