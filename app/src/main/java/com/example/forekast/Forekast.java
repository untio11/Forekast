package com.example.forekast;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.example.forekast.EditScreen.EditScreen;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Settings.SettingsFragments;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.homescreen.HomeScreen;
import com.google.android.material.internal.NavigationMenuItemView;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class Forekast extends AppCompatActivity implements Wardrobe.OnFragmentInteractionListener {
    private NavigationView navigationView;
    private int nav_home_id;
    private int nav_wardrobe_id;
    private int nav_settings_id;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            public void onDrawerClosed(View view) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav_home_id = R.id.nav_home;
        nav_settings_id = R.id.nav_settings;
        nav_wardrobe_id = R.id.nav_wardrobe;

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
                transaction.replace(R.id.content_area, fragment).addToBackStack(tag).commit();
            }

            return true;
        });

        Fragment fragment = HomeScreen.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_area, fragment).addToBackStack("home").commit();

        Repository.initDB(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int index = getSupportFragmentManager().getBackStackEntryCount() - 2;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            String tag = backEntry.getName();

            int menu_index = 0;

            switch (tag) {
                case ("home"):
                    menu_index = 0;
                    break;
                case ("wardrobe"):
                    menu_index = 1;
                    break;
                case ("settings"):
                    menu_index = 2;
                    break;
            }

            navigationView.getMenu().getItem(menu_index).setChecked(true);
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
