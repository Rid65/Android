package com.geekbrains.weather;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by shkryaba on 30/06/2018.
 */

abstract class BaseFragment extends Fragment implements BaseView.View {

    private BaseActivity baseActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view, savedInstanceState);
    }

    protected abstract void initLayout(View view, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
        baseActivity.onFragmentAttached();
    }

    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public Boolean inNetworkAvailable() {
        if (baseActivity != null) {
            return baseActivity.inNetworkAvailable();
        }
        return false;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {
    }

    public boolean isHorizontalOrientation(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }


    interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
