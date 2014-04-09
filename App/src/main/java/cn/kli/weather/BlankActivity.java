package cn.kli.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.kli.weather.base.BaseActivity;
import cn.kli.weather.base.BaseFragment;

/**
 * Created by carl on 14-4-2.
 */
public class BlankActivity extends BaseActivity {

    private static final String EXTRAS_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class<? extends BaseFragment> fragmentClass = (Class<? extends BaseFragment>) getIntent().getSerializableExtra(EXTRAS_FRAGMENT);
        setContentFragment(fragmentClass, getIntent().getExtras());
    }

    public static void startActivity(Context context, Class<? extends BaseFragment> cls, Bundle args){
        Intent intent = new Intent(context, BlankActivity.class);
        if(args != null){
            intent.putExtras(args);
        }
        intent.putExtra(EXTRAS_FRAGMENT, cls);
        context.startActivity(intent);
    }
}
