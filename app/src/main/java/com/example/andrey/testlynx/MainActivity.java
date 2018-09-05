package com.example.andrey.testlynx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andrey.testlynx.fragments.NewsDetailFragment;
import com.example.andrey.testlynx.fragments.NewsListFragment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Retrofit retrofit;
    private Fragment fragment;
    private String defaultCategory = "football";
    private NewsListFragment newsListFragment;

    public static final String BASE_URI = "http://mikonatoruri.win/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        initRetorfit();
    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startNewsListFragment(defaultCategory);
    }

    public void startNewsListFragment(String category) {
        fragment = NewsListFragment.newInstance(category);
        startFragment(fragment);
    }

    public void startNewsListFragment(Fragment targetFragment) {
        if (targetFragment == null) {
            Toast.makeText(getApplicationContext(), "Невозможно загрузить список новостей. Передан пустой фрагмент!", Toast.LENGTH_SHORT).show();
            return;
        }
        fragment = targetFragment;
        startFragment(targetFragment);
    }

    public void startNewsDetailFragment(String article) {
        FragmentManager fm = getSupportFragmentManager();
        newsListFragment = (NewsListFragment) fm.findFragmentById(R.id.fragment_container);
        fragment = NewsDetailFragment.newInstance(article);
        startFragment(fragment);
    }

    public void startFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, targetFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof NewsDetailFragment && newsListFragment != null)
            startNewsListFragment(newsListFragment);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        boolean isNewsDetailFragment = false;
        if (fragment instanceof NewsDetailFragment) {
            isNewsDetailFragment = true;
        }

        String category = "football";
        if (id == R.id.nav_football) {
            category = "football";
        } else if (id == R.id.nav_hockey) {
            category = "hockey";
        } else if (id == R.id.nav_tennis) {
            category = "tennis";
        } else if (id == R.id.nav_basketball) {
            category = "basketball";
        } else if (id == R.id.nav_volleyball) {
            category = "volleyball";
        } else if (id == R.id.nav_cybersport) {
            category = "cybersport";
        }

        if (isNewsDetailFragment)
            startNewsListFragment(category);
        else
            ((NewsListFragment)fragment).requestNewList(category);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // используем Retrofit для отправки запросов
    private void initRetorfit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }
}
