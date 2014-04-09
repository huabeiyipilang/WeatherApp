package cn.kli.weather.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.kli.weather.R;
import cn.kli.weather.base.BaseLayoutFragment;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.WeatherEngine;

/**
 * Created by carl on 14-4-5.
 */
public class DetailFragment extends BaseLayoutFragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private List<City> mCityList = new ArrayList<City>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_detail_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityList = WeatherEngine.getInstance(getActivity()).getMarkCity();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) getView().findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            WeatherDetailFragment fragment = (WeatherDetailFragment) Fragment.instantiate(getActivity(), WeatherDetailFragment.class.getName(), null);
            fragment.setCity(mCityList.get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return mCityList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCityList.get(position).name;
        }
    }
}
