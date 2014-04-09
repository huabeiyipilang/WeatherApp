package cn.kli.weather.citylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cn.kli.weather.BlankActivity;
import cn.kli.weather.R;
import cn.kli.weather.base.BaseLayoutFragment;
import cn.kli.weather.detail.DetailFragment;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.EngineListener;
import cn.kli.weather.engine.ErrorCode;
import cn.kli.weather.engine.WeatherEngine;

/**
 * Created by carl on 14-4-2.
 */
public class CityListFragment extends BaseLayoutFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private GridView mListView;
    private CityListAdapter mAdapter;

    private List<City> mCityList = new ArrayList<City>();
    private WeatherEngine mEngine;

    private EngineListener mListener = new EngineListener(){
        @Override
        protected void onRequestStateChanged(boolean isRequesting) {
            updateList(false);
        }

        @Override
        protected void onWeatherResponse(int res, int requestId, City city) {
            if(res == ErrorCode.SUCCESS){
                updateList(false);
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_city_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine = WeatherEngine.getInstance(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mEngine.addListener(mListener);
        initViews();
        updateList(true);
    }

    @Override
    protected void onActivityStart() {
        super.onActivityStart();
        updateList(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEngine.removeListener(mListener);
    }

    private void initViews(){
        mListView = (GridView) getView().findViewById(R.id.lv_city_list);
        mAdapter = new CityListAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    private synchronized void updateList(boolean request){
        List<City> list = mEngine.getMarkCity();
        if(request){
            for(City city : list){
                if (city.weathers == null || city.weathers.size() == 0
                        || TextUtils.isEmpty(city.weathers.get(0).currentTemp)
                        || city.weathers.get(0).currentTemp.toLowerCase().equals("null")) {
                    mEngine.requestWeatherByCity(city);
                }
            }
        }

        mCityList.clear();
        mCityList.addAll(list);

        if(mAdapter != null){
            mListView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        City city = mAdapter.getItem(i);
        if(city == null){
            return;
        }
        BlankActivity.startActivity(getActivity(), DetailFragment.class, null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final City city = mAdapter.getItem(i);
        if(city.name != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("是否要删除" + city.name + "?");
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    WeatherEngine.getInstance(getActivity()).removeCity(city);
                    updateList(false);
                }
            });
            builder.show();
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_city_select, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            BlankActivity.startActivity(getActivity(), CitySelectFragment.class, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CityListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mCityList.size();
        }

        @Override
        public City getItem(int i) {
            return mCityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CityItemView cityView;
            if(view == null){
                cityView = new CityItemView(getActivity());
            }else{
                cityView = (CityItemView)view;
            }
            cityView.setData(getItem(i));
            return cityView;
        }
    }
}
