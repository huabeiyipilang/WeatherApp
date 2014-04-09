package cn.kli.weather.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.kli.weather.R;
import cn.kli.weather.engine.Weather;

public class WeatherIconUtils {
    
    public static Drawable getDrawable(Context context, int weather) {
        int resId = R.drawable.w_none;
        switch(weather){
        case Weather.W_QING:
            resId = R.drawable.w_sun;
            break;
            
        case Weather.W_DUOYUN:
        case Weather.W_WU:
            resId = R.drawable.w_cloud;
            break;

        case Weather.W_YIN:
            resId = R.drawable.w_cloudy;
            break;
            
        case Weather.W_MAI:
        case Weather.W_SHACHENBAO:
        case Weather.W_FUCHEN:
        case Weather.W_YANGSHA:
        case Weather.W_QIANGSHACHENBAO:
            resId = R.drawable.w_cloudy2;
            break;
            
        case Weather.W_ZHENYU:
        case Weather.W_LEIZHENYU:
        case Weather.W_XIAOYU:
        case Weather.W_ZHONGYU:
        case Weather.W_XIAOYUZHONGYU:
        case Weather.W_ZHONGYUDAYU:
            resId = R.drawable.w_rainy;
            break;
            
        case Weather.W_DAYU:
        case Weather.W_BAOYU:
        case Weather.W_DABAOYU:
        case Weather.W_TEDABAOYU:
        case Weather.W_DONGYU:
        case Weather.W_DAYUBAOYU:
        case Weather.W_BAOYUDABAOYU:
        case Weather.W_DABAOYUTEDABAOYU:
        case Weather.W_LEIZHENYUBINGBANYOUBINGBAO:
            resId = R.drawable.w_rainy2;
            break;
            
        case Weather.W_YUJIAXUE:
        case Weather.W_ZHENXUE:
        case Weather.W_XIAOXUE:
        case Weather.W_ZHONGXUE:
        case Weather.W_XIAOXUEZHONGXUE:
        case Weather.W_ZHONGXUEDAXUE:
            resId = R.drawable.w_snowy;
            break;
            
        case Weather.W_DAXUE:
        case Weather.W_BAOXUE:
        case Weather.W_DAXUEBAOXUE:
            resId = R.drawable.w_snowy2;
            break;
        }
        Drawable drawable = context.getResources().getDrawable(resId);
//        SVG svg = SVGParser.getSVGFromResource(context.getResources(), resId);
//        Drawable drawable = svg.createPictureDrawable();
        return drawable;
    }
}
