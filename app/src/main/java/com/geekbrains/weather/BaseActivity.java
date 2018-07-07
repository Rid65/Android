package com.geekbrains.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity
        implements BaseView.View, BaseFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_NAME = "ARG_NAME";
    private static final String TEXT = "TEXT";

    private FloatingActionButton fab;
    private TextView textView;
    boolean isExistAction;  // Можно ли расположить рядом фрагмент
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initLayout(savedInstanceState);
    }

    private void initLayout(Bundle savedInstanceState) {
        WeatherFragment weatherFrag = new WeatherFragment();
        CreateActionFragment editTextFrag = new CreateActionFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvUsername = navigationView.getHeaderView(0).findViewById(R.id.tvUsername);
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NAME)) {
            setName(savedInstanceState.getString(ARG_NAME));
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // addFragment(new CreateActionFragment());
            }
        });

        View detailsFrame = findViewById(R.id.fl_cont);
        isExistAction = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        if (isExistAction) {
            // Проверим, что фрагмент существует в активити
            CreateActionFragment detail = (CreateActionFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fl_cont);
            // выводим фрагмент
            if (detail == null) {
                startActionFragment();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_NAME, tvUsername.getText().toString().trim());
    }

    //
    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_cont, fragment)
                .commit();
    }

//    private void getCurrentFragment() {
//        getSupportFragmentManager().findFragmentById(R.id.fl_cont);
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            // Handle the camera action
        } else if (id == R.id.nav_info) {
            // Handle the camera action
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Boolean inNetworkAvailable() {
        return true;
    }

    @Override
    public void initDrawer(String username, Bitmap profileImage) {

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void startActionFragment() {
        addFragment(new CreateActionFragment());
    }

    public void startWeatherFragment(String country) {
        //запускаем WeatherFragment и передаем туда country
        addFragment(WeatherFragment.newInstance(country));
    }

    public Fragment getAnotherFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.cities);
    }

    public void setName(String name) {
        tvUsername.setText(name);
    }
}