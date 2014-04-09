package cn.kli.weather.detail;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.kli.utils.klilog;
import cn.kli.weather.R;
import cn.kli.weather.base.BaseLayoutFragment;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.Weather;
import cn.kli.weather.engine.WeatherEngine;

/**
 * Created by carl on 14-4-3.
 */
public class WeatherDetailFragment extends BaseLayoutFragment {
    private TextView mTestView;
    private TextView mCurrentTemp;
    private ListView mWeatherList;

    private City mCity;
    private WeatherAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        updateView();
    }

    private void initViews(){
        mTestView = (TextView)getView().findViewById(R.id.tv_test);
        mCurrentTemp = (TextView)getView().findViewById(R.id.tv_current_temp);
        mWeatherList = (ListView)getView().findViewById(R.id.lv_weathers);
    }

    public void setCity(City city){
        mCity = city;
        if (this.isVisible()){
            updateView();
        }
    }

    private void updateView(){
        if(mCity == null){
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(mCity.toString()+"\n");
        if(mCity.weathers != null){
            for(Weather weather : mCity.weathers){
                sb.append(weather+"\n");
            }
        }
        mTestView.setText(sb.toString());

        String degree = getActivity().getString(R.string.sheshidu);

        if(mCity.weathers != null && mCity.weathers.size() > 0){
            mCurrentTemp.setText(mCity.weathers.get(0).currentTemp + degree);
            mWeatherList.setAdapter(new WeatherAdapter(mCity.weathers));
        }
    }

    private class WeatherAdapter extends BaseAdapter{
        List<Weather> weatherList = new ArrayList<Weather>();

        WeatherAdapter(List<Weather> list){
            if(list != null){
                weatherList.addAll(list);
            }
        }

        @Override
        public int getCount() {
            return weatherList.size();
        }

        @Override
        public Weather getItem(int i) {
            return weatherList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            WeatherItemView itemView = null;
            if(view == null){
                itemView = new WeatherItemView(getActivity());
            }else{
                itemView = (WeatherItemView)view;
            }
            itemView.setWeather(getItem(i));
            return itemView;
        }
    }
}
