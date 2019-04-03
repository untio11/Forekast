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
import android.widget.SeekBar;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomeScreenViewModelInterface vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        init(savedInstanceState);

    }

    private void init(Bundle savedInstance) {
        vm = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        Repository.initDB(getApplicationContext());

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveWeather().observe(
                this,
                newWeather -> Log.d("WeatherUpdate", (newWeather != null ? newWeather.toString() : "No weather")));

        if (savedInstance != null) {
            vm.setComfort(savedInstance.getInt("Comfortsl"));
            vm.setFormality(savedInstance.getInt("Formalsl"));
            vm.setWarmth(savedInstance.getInt("Warmthsl"));
        }

        SeekBar comfort_slider = findViewById(R.id.slider_comfort);
        comfort_slider.setProgress(vm.getComfort());
        comfort_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());

        SeekBar warmth_slider = findViewById(R.id.slider_warmth);
        warmth_slider.setProgress(vm.getWarmth());
        warmth_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());

        SeekBar formality_slider = findViewById(R.id.slider_formality);
        formality_slider.setProgress(vm.getFormality());
        formality_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());


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
        //vm.newOutfit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setChecked(true);
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_wardrobe) {
            Intent start_wardrobe = new Intent(getApplicationContext(), Wardrobe.class);
            start_wardrobe.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start_wardrobe);
        } else if (id == R.id.nav_settings) {
            Intent start_settings = new Intent(getApplicationContext(), Settings.class);
            start_settings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start_settings);
        } else if (id == R.id.nav_switchwardrobe) {
            Intent start_wardrobe = new Intent(getApplicationContext(), SwitchWardrobe.class);
            start_wardrobe.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start_wardrobe);
        }

        return true;
    }

  @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putInt("Warmthsl", vm.getWarmth());
        savedInstanceState.putInt("Comfortsl", vm.getComfort());
        savedInstanceState.putInt("Formalsl", vm.getFormality());
    }

    private class updateCriteriaSeekbar implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String category = seekBar.getTag().toString();
            Log.d("SeekbarListener", "Updating " + category + " to: " + progress);
            switch (category) {
                case "warmth":
                    vm.setWarmth(progress);
                    break;
                case "formality":
                    vm.setFormality(progress);
                    break;
                case "comfort":
                    vm.setComfort(progress);
                    break;
                default:
                    break;
            }
        }
    }
}
