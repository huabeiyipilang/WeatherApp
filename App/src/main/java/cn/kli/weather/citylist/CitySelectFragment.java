package cn.kli.weather.citylist;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.kli.weather.R;
import cn.kli.weather.base.BaseLayoutFragment;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.EngineListener;
import cn.kli.weather.engine.WeatherEngine;

/**
 * Created by carl on 14-4-3.
 */
public class CitySelectFragment extends BaseLayoutFragment implements AdapterView.OnItemClickListener {

    //views
    private TextView mTvCityNav;
    private ListView mLvCityList;

    private LinkedList<City> mCityTree = new LinkedList<City>();
    private List<City> mCurrentList;

    private WeatherEngine mEngine;

    private Handler mHandler = new Handler();

    private EngineListener mListener = new EngineListener(){
        @Override
        protected void onCityListResponse(int res, int requestId, final List<City> list) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCurrentList = list;
                    City city = mCityTree.peek();
                    if (mCurrentList.size() == 0 && city != null) {
                        //this means the city unit
                        mEngine.markCity(city);
                        getActivity().finish();
                    }

                    //fresh list view
                    CityListAdapter adapter = new CityListAdapter(getActivity(), mCurrentList);
                    mLvCityList.setAdapter(adapter);

                    //fresh navigation view
                    String nav = "";
                    if (mCityTree.size() >= 1) {
                        for (City _city : mCityTree) {
                            nav += "/" + _city.name;
                        }
                    }
                    mTvCityNav.setText(nav);
                }
            });
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine = WeatherEngine.getInstance(getActivity());
        initFinished();
    }

    @Override
    public void onStart() {
        super.onStart();
        mEngine.addListener(mListener);
        initViews();
    }

    @Override
    public void onStop() {
        super.onStop();
        mEngine.removeListener(mListener);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_city_select;
    }

    private void initViews(){
        mTvCityNav = (TextView)getView().findViewById(R.id.tv_city_navigation);
        mLvCityList = (ListView)getView().findViewById(R.id.lv_city_list);
        mLvCityList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

        City city = mCurrentList.get(pos);
        mCityTree.addFirst(city);
        freshListByCity(city);
    }


    private void initFinished(){
        freshListByCity(null);
    }

    private void freshListByCity(City city){
        //get data
        mEngine.requestCityListByCity(city);
    }

    private class CityListAdapter extends BaseAdapter {
        List<City> list;
        LayoutInflater inflater;
        CityListAdapter(Context context, List<City> cityList){
            inflater = LayoutInflater.from(context);
            list = cityList;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView == null){
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }else{
                view = convertView;
            }

            TextView tv = (TextView)view;
            tv.setText(list.get(pos).name);
            return tv;
        }

    }

    private void backToUpLevel(){
        City toCity = mCityTree.getFirst();
        freshListByCity(toCity);
    }

    @Override
    public boolean onBackKeyDown() {
        if(mCityTree.size() != 0){
            backToUpLevel();
            return true;
        }
        return super.onBackKeyDown();
    }
}
