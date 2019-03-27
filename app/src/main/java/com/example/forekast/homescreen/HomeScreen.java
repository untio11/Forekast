package com.example.forekast.homescreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Settings.SwitchWardrobe;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private HomeScreenViewModelInterface vm;
    public Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(
                view, "Replace with your own action",
                Snackbar.LENGTH_LONG
        ).setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        vm = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        Repository.initDB(getApplicationContext());

        final Observer<Weather> weathereObserver = newWeather -> Log.d("WeatherUpdate", (newWeather != null ? newWeather.toString() : "No weather"));

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveWeather().observe(this, weathereObserver);

        /**
         * Checks the permissions for location
         * Then attempts to get the last known location
         * If successful will set the global variable currentLocation to querried location
         */
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


    }

    public void refreshClothing(View v) {
        vm.newOutfit();
        vm.updateWeather();
    }

    public void nextClothing(View v) {
        vm.nextClothing(v.getTag().toString());
        Log.d("Next", v.getTag().toString());
    }

    public void prevClothing(View v) {
        vm.previousClothing(v.getTag().toString());
        Log.d("Prev", v.getTag().toString());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_wardrobe) {
            startActivity(new Intent(getApplicationContext(), Wardrobe.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
        } else if (id == R.id.nav_switchwardrobe) {
            startActivity(new Intent(getApplicationContext(), SwitchWardrobe.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
