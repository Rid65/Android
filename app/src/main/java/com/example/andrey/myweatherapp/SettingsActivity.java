package com.example.andrey.myweatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingsActivity extends BaseActivity {

    private EditText city;
    private CheckBox humidity;
    private CheckBox speed;
    private CheckBox pressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_settings);

        city = findViewById(R.id.city);
        humidity = findViewById(R.id.humidity);
        speed = findViewById(R.id.speed);
        pressure = findViewById(R.id.pressure);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });*/

    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("city", city.getText().toString());
        intent.putExtra("humidity", humidity.isChecked());
        intent.putExtra("speed", speed.isChecked());
        intent.putExtra("pressure", pressure.isChecked());
        startActivity(intent);
        finish();
    }
}
