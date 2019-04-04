package com.example.forekast;

import android.Manifest;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class Forekast extends AppCompatActivity implements Wardrobe.OnFragmentInteractionListener {
    private NavigationView navigationView;
    public Location currentLocation;
    private FusedLocationProviderClient fl;
    private LocationRequest req;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.main_activity);

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
                    fragment = Wardrobe.newInstance();
                    tag = "wardrobe";
                    // Switch to wardrobe fragment
                    break;
                case (R.id.nav_settings):
                    fragment = new SettingsFragments();
                    tag = "settings";
                    break;
            }

            if (fragment != null) {
                FragmentTransaction transaction = Forekast.this.getSupportFragmentManager().beginTransaction();
                if (onBackstack(tag)) {
                    getSupportFragmentManager().popBackStack(fragment.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                transaction.replace(R.id.content_area, fragment).addToBackStack(tag).commit();
            }

            return true;
        });

        init();
    }

    private void init() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_area, HomeScreen.newInstance()).addToBackStack("home").commit();
        Repository.initDB(getApplicationContext());
        HomeScreenViewModelInterface vm = ViewModelProviders.of(Forekast.this).get(HomeScreenViewModel.class);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    vm.updateWeather(location);
                }
            };
        };

        fl = new FusedLocationProviderClient(getApplicationContext());

        req = createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(req);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                startLocationUpdates(req);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
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
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("live_location", false)) {
            startLocationUpdates(req);
        }
    }

    private LocationRequest createLocationRequest() {
        LocationRequest req = LocationRequest.create();
        req.setInterval(300000); // Five minutes
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int index = getSupportFragmentManager().getBackStackEntryCount() - 2;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();

            if (onBackstack(tag)) {
                getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_area, new HomeScreen()).addToBackStack("home").commit();
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    private boolean onBackstack(String fragment_tag) {
        FragmentManager fm = getSupportFragmentManager();

        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            String name = fm.getBackStackEntryAt(i).getName();

            if (name != null && name.equals(fragment_tag)) return true;
        }

        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
