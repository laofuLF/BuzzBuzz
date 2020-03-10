package com.weihuan.buzzbuzz.fragment;

import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<RecipeListFragment> fragments = new ArrayList<>();
    private RecipeListFragment currentFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(RecipeListFragment.newInstance(0));
        fragments.add(RecipeListFragment.newInstance(1));
        fragments.add(RecipeListFragment.newInstance(2));
    }

    @Override
    public RecipeListFragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object){
        if (getCurrentFragment() != object) {
            currentFragment = ((RecipeListFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }


    public RecipeListFragment getCurrentFragment() {
        return currentFragment;
    }
}
