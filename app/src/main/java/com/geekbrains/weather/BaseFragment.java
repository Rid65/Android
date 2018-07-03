package com.geekbrains.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shkryaba on 30/06/2018.
 */

abstract class BaseFragment extends Fragment implements BaseView.View {

    private BaseActivity baseActivity;
    private static final String TAG = "AppWeather";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view, savedInstanceState);
        Log.d(TAG, "Fragment onViewCreated()");
    }

    protected abstract void initLayout(View view, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
        baseActivity.onFragmentAttached();
        Log.d(TAG, "Fragment onAttach()");
    }

    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
        Log.d(TAG, "Fragment onDetach()");
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


    interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Fragment onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Fragment onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment onResume()");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Fragment onPause()");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Fragment onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Fragment onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "Fragment onDestroyView()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Fragment onSaveInstanceState()");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "Fragment onViewStateRestored()");
    }
}
