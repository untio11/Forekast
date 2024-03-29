package com.example.forekast;

import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.forekast.Settings.SettingsFragments;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.external_data.Repository;
import com.example.forekast.homescreen.HomeScreen;
import com.example.forekast.homescreen.HomeScreenViewModel;
import com.example.forekast.homescreen.HomeScreenViewModelInterface;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class Forekast extends AppCompatActivity implements Wardrobe.OnFragmentInteractionListener {
    private NavigationView navigationView;
    private FusedLocationProviderClient fl;
    private LocationRequest req;
    private HomeScreenViewModelInterface vm;
    private Timer static_weather_timer;
    private SharedPreferences.OnSharedPreferenceChangeListener pref_change = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("live_location")) {
                // When we click the button, the last value was the one it isn't now
                if (Forekast.this.usingLiveLocation()) { // Turned on -> stop static weather
                    static_weather_timer.cancel();
                    Forekast.this.startLiveWeather();
                } else { // Turned off -> start static weather, stop location weather
                    fl.removeLocationUpdates(locationCallback);
                    Forekast.this.startStaticWeather();
                }
            } else if (key.equals("user_list")) { // Update the current wardrobe
                vm.setOwner(sharedPreferences.getString("user_list", "default_user"));
            }
        }
    };

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                // Update the weather in the viewmodel
                vm.updateWeather(location);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.main_activity);

        initializeNavigation();

        init();

        // Initialize the homescreen in the content area on startup
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_area, HomeScreen.newInstance())
                .addToBackStack("home")
                .commit();
    }

    private void init() {
        Repository.initDB(getApplicationContext());

        vm = ViewModelProviders.of(Forekast.this).get(HomeScreenViewModel.class);

        locationSetup();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .registerOnSharedPreferenceChangeListener(pref_change);

        // Start the weather polling
        if (usingLiveLocation()) {
            startLiveWeather();
        } else {
            startStaticWeather();
        }
    }

    private void locationSetup() {
        if (fl == null) {
            fl = new FusedLocationProviderClient(getApplicationContext());
        }

        if (req == null) {
            req = createLocationRequest();
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(req);

        SettingsClient client = LocationServices.getSettingsClient(Forekast.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(Forekast.this, locationSettingsResponse -> {
            if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("live_location", false)) {
                startLocationUpdates(req);
            }
        });
        task.addOnFailureListener(Forekast.this, e -> {
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(Forekast.this, 1);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private LocationRequest createLocationRequest() {
        LocationRequest req = LocationRequest.create();
        req.setInterval(5000); // Five minutes
        req.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return req;
    }

    private void startLocationUpdates(LocationRequest req) {
        try {
            fl.requestLocationUpdates(req, locationCallback, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void startLiveWeather() {
        // Start with making location requests and stuff
        if (req == null) {
            req = createLocationRequest();
        }

        startLocationUpdates(req);
    }

    private void startStaticWeather() {
            static_weather_timer = new Timer();
            static_weather_timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    vm.updateWeather(PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext())
                            .getString("manual_location", "Eindhoven"));
                }
            }, 0, 5000);
    }

    private boolean usingLiveLocation() {
        return PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("live_location", false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack("home", 0);
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {
            this.finish();
        }
    }

    private boolean onBackStack(String fragment_tag) {
        FragmentManager fm = getSupportFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            String name = fm.getBackStackEntryAt(i).getName();

            if (name != null && name.equals(fragment_tag)) return true;
        }

        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    private void initializeNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation view item clicks here.
            item.setChecked(true);

            drawer.closeDrawer(GravityCompat.START);

            Fragment fragment = null;
            String tag = null;


            switch (item.getItemId()) {
                case (R.id.nav_home):
                    // Switch to home screen fragment
                    fragment = HomeScreen.newInstance();
                    tag = "home";
                    break;
                case (R.id.nav_wardrobe):
                    // Switch to wardrobe fragment
                    fragment = Wardrobe.newInstance();
                    tag = "wardrobe";
                    break;
                case (R.id.nav_settings):
                    fragment = new SettingsFragments();
                    tag = "settings";
                    break;
            }

            if (fragment != null) {
                FragmentTransaction transaction = Forekast.this.getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().popBackStack("home", 0);
                transaction.replace(R.id.content_area, fragment).addToBackStack(tag).commit();
            }

            return true;
        });
    }

    public void refreshClothing(View v) {
        HomeScreen home = (HomeScreen) Forekast.this.getSupportFragmentManager().findFragmentByTag("home");
        if (home != null) {
            home.refreshClothing(v);
        }
    }

    public void nextClothing(View v) {
        HomeScreen home = (HomeScreen) Forekast.this.getSupportFragmentManager().findFragmentByTag("home");
        if (home != null) {
            home.nextClothing(v);
        }
    }

    public void prevClothing(View v) {
        HomeScreen home = (HomeScreen) Forekast.this.getSupportFragmentManager().findFragmentByTag("home");
        if (home != null) {
            home.prevClothing(v);
        }
    }
}
