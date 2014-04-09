package cn.kli.weather.detail;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.kli.utils.CalendarUtils;
import cn.kli.weather.R;
import cn.kli.weather.engine.Weather;
import cn.kli.weather.utils.WeatherIconUtils;

public class WeatherItemView extends LinearLayout {

    public WeatherItemView(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_weather_item, this);
    }

    public void setWeather(Weather weather){
		TextView tvWeek = (TextView)findViewById(R.id.tv_prv_week);
		TextView tvDate = (TextView)findViewById(R.id.tv_prv_date);
		TextView tvWeather = (TextView)findViewById(R.id.tv_prv_weather);
		TextView tvMaxTemp = (TextView)findViewById(R.id.tv_prv_maxtemp);
		TextView tvMinTemp = (TextView)findViewById(R.id.tv_prv_mintemp);
		ImageView ivIcon = (ImageView)findViewById(R.id.iv_prv_pic);

		String degree = getContext().getString(R.string.sheshidu);
		SimpleDateFormat sdf = new SimpleDateFormat(getContext().getString(R.string.date_format));
		
		String day = null;
		if(CalendarUtils.isSameDay(weather.calendar, Calendar.getInstance())){
			day = "今天";
		}else{
			int i = weather.calendar.get(Calendar.DAY_OF_WEEK);
			String[] days = getContext().getResources().getStringArray(R.array.weekday);
			day = days[i-1];
		}
		tvWeek.setText(day);
		
		tvDate.setText(sdf.format(weather.calendar.getTimeInMillis()));
		
		tvWeather.setText(weather.getFormatWeatherName());
		tvMaxTemp.setText(weather.maxTemp + degree);
		tvMinTemp.setText(weather.minTemp + degree);
		ivIcon.setImageDrawable(WeatherIconUtils.getDrawable(getContext(), weather.weather[0]));
	}
}
