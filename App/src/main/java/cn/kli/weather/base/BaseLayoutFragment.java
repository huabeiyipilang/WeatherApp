package cn.kli.weather.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by carl on 14-4-2.
 */
public abstract class BaseLayoutFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResId(), container, false);
        return root;
    }

    protected abstract int getLayoutResId();


}