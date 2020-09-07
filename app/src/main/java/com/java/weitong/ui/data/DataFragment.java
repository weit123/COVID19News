package com.java.weitong.ui.data;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.java.weitong.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFragment extends Fragment {

    TabLayout mytab;
    ViewPager mViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_datamain, container, false);

        mytab = root.findViewById(R.id.mytab);
        mViewPager = root.findViewById(R.id.mViewPager);

        String[] title = getResources().getStringArray(R.array.data_tab_name);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), Arrays.asList(title)));
        mytab.setupWithViewPager(mViewPager);

        return root;
    }
}

class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> title;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new InternationalFragment();
        }
        return new DomesicFragment();
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}