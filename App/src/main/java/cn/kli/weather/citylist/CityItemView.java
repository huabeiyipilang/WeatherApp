package cn.kli.weather.citylist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.kli.weather.R;
import cn.kli.weather.engine.City;
import cn.kli.weather.engine.Weather;
import cn.kli.weather.utils.WeatherIconUtils;

/**
 * Created by carl on 14-4-3.
 */
public class CityItemView extends FrameLayout{
    private TextView mTitle;
    private TextView mCurrentTemp;
    private ImageView mWeatherIcon;

    public CityItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_city_list_item, this, true);
        initViews();
    }

    private void initViews(){
        mTitle = (TextView)findViewById(R.id.tv_city_name);
        mCurrentTemp = (TextView)findViewById(R.id.tv_current_temp);
        mWeatherIcon = (ImageView)findViewById(R.id.iv_weather_icon);
    }

    public void setData(City city) {
        if (city != null && !TextUtils.isEmpty(city.name)) {
            mTitle.setText(city.name);
        } else {
            return;
        }
        if (city.weathers != null && city.weathers.size() > 0) {
            Weather weather = city.weathers.get(0);
            if (weather != null) {
                mCurrentTemp.setText(weather.currentTemp + "℃");
                mWeatherIcon.setImageDrawable(WeatherIconUtils.getDrawable(getContext(), weather.weather[0]));
            }
        }
    }
}
