package com.example.forekast.homescreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Settings.SwitchWardrobe;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private HomeScreenViewModelInterface vm;
    public Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
        final Observer<Outfit> clothingObserver = newClothing -> Log.d("ClothingUpdate", (newClothing != null ? newClothing.toString() : "No clothes"));

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveWeather().observe(this, weathereObserver);
        vm.getLiveOutfit().observe(this, clothingObserver);
    }

    public void accessories(){
        Boolean sunglasses = false;
        Boolean coat = false;
        Boolean gloves = false;
        Boolean umbrella = false;
        Boolean leggings = false;

        ImageView sunglassesView = findViewById(R.id.noti_sunglasses);
        ImageView coatView = findViewById(R.id.noti_coat);
        ImageView glovesView = findViewById(R.id.noti_gloves);
        ImageView umbrellaView = findViewById(R.id.noti_umbrella);
        ImageView leggingsView = findViewById(R.id.noti_leggings);

        if (sunglasses) {
            sunglassesView.setColorFilter(Color.GRAY);
        }
        else {
            sunglassesView.setColorFilter(Color.BLACK);
        }
        if (coat) {
            coatView.setColorFilter(Color.GRAY);
        }
        else {
            coatView.setColorFilter(Color.BLACK);
        }
        if (gloves) {
            glovesView.setColorFilter(Color.GRAY);
        }
        else {
            glovesView.setColorFilter(Color.BLACK);
        }
        if (umbrella) {
            umbrellaView.setColorFilter(Color.GRAY);
        }
        else {
            umbrellaView.setColorFilter(Color.BLACK);
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
