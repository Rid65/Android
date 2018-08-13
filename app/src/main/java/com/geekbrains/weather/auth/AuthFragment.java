package com.geekbrains.weather.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geekbrains.weather.R;
import com.geekbrains.weather.ui.base.BaseFragment;
import com.geekbrains.weather.ui.weather.WeatherFragment;

public class AuthFragment extends BaseFragment {

    EditText editText_SmsCode;
    Button button_SignUp;

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        editText_SmsCode = view.findViewById(R.id.et_sms_code);
        button_SignUp = view.findViewById(R.id.btn_sign_up);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String smsCode = intent.getStringExtra("SMS_CODE");
                editText_SmsCode.setText(smsCode);
            }
        };
        IntentFilter intentFilter = new IntentFilter("SMS_INTENT");
        getBaseActivity().registerReceiver(receiver, intentFilter);

        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, WeatherFragment.newInstance("Иркутск"))
                .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

}
