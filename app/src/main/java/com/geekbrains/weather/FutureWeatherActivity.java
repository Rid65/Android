package com.geekbrains.weather;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class FutureWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_weather);

        initLayout(savedInstanceState);
    }

    private void initLayout(Bundle savedInstanceState) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(getResources());
        final List<WeatherItem> dataSource = dataSourceBuilder.build();
        final WeatherAdapter adapter = new WeatherAdapter(dataSource);
        recyclerView.setAdapter(adapter);

        Button btn_add = findViewById(R.id.add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.add(new WeatherItem(R.drawable.sunny, getResources().getStringArray(R.array.dates)[0], getResources().getStringArray(R.array.temperatures)[0]));
                adapter.notifyDataSetChanged();
            }
        });
    }

}
