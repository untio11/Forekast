package com.example.forekast.homescreen;

import android.Manifest;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Settings.SwitchWardrobe;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.clothing.ClothingCriteriaInterface;
import com.example.forekast.clothing.ClothingCriteriaInterface.*;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.clothing.Torso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import android.util.Log;
import android.util.Pair;
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
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomeScreenViewModelInterface vm;
    private Weather weather;
    private Outfit outfit;

    private MutablePair<Integer, Integer> warmth = new MutablePair<>(5, 5);
    private MutablePair<Integer, Integer> formality = new MutablePair<>(5, 5);
    private MutablePair<Integer, Integer> comfort = new MutablePair<>(5, 5);
    private MutablePair<Integer, Integer> preference = new MutablePair<>(10, 10);

    private ClothingCriteria criteria;

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
        vm.updateWeather();
    }

    private void init(Bundle savedInstance) {
        vm = ViewModelProviders.of(this).get(HomeScreenViewModel.class);
        Repository.initDB(getApplicationContext());

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveWeather().observe(
                this,
                newWeather -> initWeather(newWeather));

        vm.getLiveOutfit().observe(
                this,
                newOutfit -> initOutfit(newOutfit));
        

        if (savedInstance != null) {
            vm.setComfort(savedInstance.getInt("Comfortsl"));
            vm.setFormality(savedInstance.getInt("Formalsl"));
            vm.setWarmth(savedInstance.getInt("Warmthsl"));
        }

        SeekBar comfort_slider = findViewById(R.id.slider_comfort);
        comfort_slider.setMax(10);
        comfort_slider.setProgress(vm.getComfort());
        comfort_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());

        SeekBar warmth_slider = findViewById(R.id.slider_warmth);
        warmth_slider.setMax(10);
        warmth_slider.setProgress(vm.getWarmth());
        warmth_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());

        SeekBar formality_slider = findViewById(R.id.slider_formality);
        formality_slider.setMax(10);
        formality_slider.setProgress(vm.getFormality());
        formality_slider.setOnSeekBarChangeListener(new updateCriteriaSeekbar());
    }

    private void initWeather(Weather newWeather) {
        Log.d("WeatherUpdate", (newWeather != null ? newWeather.toString() : "No weather"));
        if (newWeather != null) {
            this.weather = newWeather;
            if (criteria != null) {
                vm.sugg.setCurrentCriteria(criteria, weather);
                vm.newOutfit();
            }
        }
    }

    private void initOutfit(Outfit newOutfit) {
        Log.d("ClothingUpdate", (newOutfit != null ? newOutfit.toString() : "No clothes"));

        if (newOutfit != null) {
            this.outfit = newOutfit;
            if (outfit.torso.torso != null) {
                System.out.println(outfit.torso.torso);
            }
            else {
                System.out.println(outfit.torso.inner);
                System.out.println(outfit.torso.outer);
            }
            System.out.println(outfit.pants);
            System.out.println(outfit.shoes);
            setOutfit();
        }
        accessories();
    }

    public void setOutfit(){
        ImageView innerTorso = findViewById(R.id.innerTorso);
        ImageView outerTorso = findViewById(R.id.outerTorso);
        ImageView bottoms = findViewById(R.id.bottoms);
        ImageView shoes = findViewById(R.id.shoes);

        LinearLayout bottomsLayout = findViewById(R.id.bottomsLayout);

        Bitmap bitmapIT;
        Bitmap bitmapOT;
        Bitmap bitmapP;
        Bitmap bitmapS;

        if (outfit.torso != null){
            if (outfit.torso.torso != null) {
                if (outfit.torso.torso.underwearable) {
                    bitmapIT = BitmapFactory.decodeByteArray(outfit.torso.torso.picture, 0, outfit.torso.torso.picture.length);
                    innerTorso.setImageBitmap(bitmapIT);
                    outerTorso.setVisibility(View.GONE);
                }
                else {
                    bitmapOT = BitmapFactory.decodeByteArray(outfit.torso.torso.picture, 0, outfit.torso.torso.picture.length);
                    outerTorso.setImageBitmap(bitmapOT);
                    innerTorso.setVisibility(View.GONE);
                }
            }
            else { //if (outfit.torso.inner != null && outfit.torso.outer != null) {
                bitmapIT = BitmapFactory.decodeByteArray(outfit.torso.inner.picture, 0, outfit.torso.inner.picture.length);
                innerTorso.setImageBitmap(bitmapIT);
                bitmapOT = BitmapFactory.decodeByteArray(outfit.torso.outer.picture, 0, outfit.torso.outer.picture.length);
                outerTorso.setImageBitmap(bitmapOT);
            }
        }
        /*
        if (outfit.outer_torso != null){
            bitmapOT = BitmapFactory.decodeByteArray(outfit.outer_torso.picture, 0, outfit.outer_torso.picture.length);
            outerTorso.setImageBitmap(bitmapOT);
        }*/
        if (outfit.pants != null){
            if (outfit.torso.torso != null && outfit.torso.torso.type.equals("Dress")){
                bottomsLayout.setVisibility(View.GONE);
            }
            else if (outfit.torso.inner.type.equals("Dress")){
                bottomsLayout.setVisibility(View.GONE);
            }
            else {
                bitmapP = BitmapFactory.decodeByteArray(outfit.pants.picture, 0, outfit.pants.picture.length);
                bottoms.setImageBitmap(bitmapP);
            }
        }
        if (outfit.shoes != null){
            bitmapS = BitmapFactory.decodeByteArray(outfit.shoes.picture, 0, outfit.shoes.picture.length);
            shoes.setImageBitmap(bitmapS);
        }
    }

    public void accessories(){
        vm.sugg.setAccessories();

        ImageView sunglassesView = findViewById(R.id.noti_sunglasses);
        ImageView coatView = findViewById(R.id.noti_coat);
        ImageView glovesView = findViewById(R.id.noti_gloves);
        ImageView umbrellaView = findViewById(R.id.noti_umbrella);
        ImageView leggingsView = findViewById(R.id.noti_leggings);

        boolean[] accessories = vm.getAccessories();

        if (accessories[0]) { // Sunglasses
            sunglassesView.setColorFilter(Color.BLACK);
        }
        else {
            sunglassesView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[1]) { // Coat
            coatView.setColorFilter(Color.BLACK);
        }
        else {
            coatView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[2]) { // Gloves
            glovesView.setColorFilter(Color.BLACK);
        }
        else {
            glovesView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[3]) { // Umbrella
            umbrellaView.setColorFilter(Color.BLACK);
        }
        else {
            umbrellaView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[4]) { // Leggings
            leggingsView.setColorFilter(Color.BLACK);
        }
        else {
            leggingsView.setColorFilter(Color.LTGRAY);
        }
    }

    public void refreshClothing(View v) {
        vm.refreshClothing();
        Log.d("Refresh", v.getTag().toString());
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            warmth = new MutablePair<>(vm.getWarmth(), vm.getWarmth());
            formality = new MutablePair<>(vm.getFormality(), vm.getFormality());
            comfort = new MutablePair<>(vm.getComfort(), vm.getComfort());
            criteria = new ClothingCriteria(warmth, formality, comfort, preference, "General");

            vm.sugg.setCurrentCriteria(criteria, weather);

            System.out.println(criteria);
            System.out.println(weather);
            if (criteria != null && weather != null) {
                vm.newOutfit();
            }
        }
    }
}
