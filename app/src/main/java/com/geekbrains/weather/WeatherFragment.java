package com.geekbrains.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends BaseFragment implements /*Observer,*/ CreateActionFragment.OnHeadlineSelectedListener {


    private static final String ARG_CITY = "ARG_CITY";
    private String country;
    private TextView textView;


    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(String country) {
        Bundle args = new Bundle();
        args.putString(ARG_CITY, country);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getString(ARG_CITY);
        }
        if (savedInstanceState != null) {
            country = savedInstanceState.getString(ARG_CITY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), "onAttachWeather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getContext(), "onDetachWeather", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_layout, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        /*textView = getBaseActivity().findViewById(R.id.tv_country);
        if (country != null && country.length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(country);
        } else {
            textView.setVisibility(View.GONE);
        }*/



        //-- в зависимости от ориентации будем менять параметры элементов view, чтобы смотрелось красиво
        if (isHorizontalOrientation()) {
            //-- уменьшим ширину и высоту изображения
            /*int widthDim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
            int heightDim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
            ImageView ivWeather = view.findViewById(R.id.ivWeather);
            ivWeather.getLayoutParams().height = heightDim;
            ivWeather.getLayoutParams().width = widthDim;
            ivWeather.requestLayout();

            //-- уменьшим размер текста
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
*/
            //-- уменьшим размер текста
            TextView temperature = view.findViewById(R.id.temperature);
            temperature.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        }
    }

    @Override
    public BaseActivity getBaseActivity() {
        return super.getBaseActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(ARG_CITY, textView.getText().toString().trim());
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "onStartWeather", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "onResumeWeather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "onPauseWeather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(), "onStopWeather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(), "onDestroyWeather", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void updateText(String text) {
//        country = text;
//        Log.d(ARG_COUNTRY, country);
//        if (textView == null) {
//            Log.d(ARG_COUNTRY, "null");
//        }
//    }

    @Override
    public void onArticleSelected(String position) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(position);
    }

}
