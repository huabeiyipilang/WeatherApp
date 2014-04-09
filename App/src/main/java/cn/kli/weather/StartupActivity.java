package cn.kli.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import cn.kli.weather.base.BaseActivity;
import cn.kli.weather.engine.WeatherEngine;
import weathersource.webxmlcomcn.SourceWebXml;

/**
 * Created by carl on 14-4-3.
 */
public class StartupActivity extends BaseActivity{

    private final static int MSG_INIT_FINISHED = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_INIT_FINISHED:
                    startActivity(new Intent(StartupActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InitAsyncTask().execute("");
    }

    private class InitAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            WeatherEngine.getInstance(StartupActivity.this).init(new SourceWebXml(StartupActivity.this));
            mHandler.sendEmptyMessage(MSG_INIT_FINISHED);
            return null;
        }
    }
}
