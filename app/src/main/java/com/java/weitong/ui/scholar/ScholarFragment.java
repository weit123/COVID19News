package com.java.weitong.ui.scholar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.weitong.R;
import com.java.weitong.db.ScholarList;

import java.util.Arrays;
import java.util.List;

public class ScholarFragment extends Fragment {

    TabLayout mytab;
    ViewPager mViewPager;
    ScholarList scholarList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_scholarmain, container, false);

        mytab = root.findViewById(R.id.scholar_tab);
        mViewPager = root.findViewById(R.id.scholar_view_pager);

        String[] title = getResources().getStringArray(R.array.scholar_tab_name);
        mViewPager.setAdapter(new ScholarFragmentPagerAdapter(getFragmentManager(), Arrays.asList(title)));
        mytab.setupWithViewPager(mViewPager);

        scholarList = new ScholarList();

        return root;
    }
}

class ScholarFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> title;

    public ScholarFragmentPagerAdapter(FragmentManager fm, List<String> title) {
        super(fm);

        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("lqq", "xxbb");
        if (position == 1) {
            return new DeadScholarFragment();
        }
        return new AliveScholarFragment();
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