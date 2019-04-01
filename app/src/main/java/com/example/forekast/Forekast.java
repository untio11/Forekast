package com.example.forekast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.example.forekast.EditScreen.EditScreen;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;
import com.example.forekast.homescreen.HomeScreen;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class Forekast extends AppCompatActivity {
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
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation view item clicks here.
            item.setChecked(true);

            drawer.closeDrawer(GravityCompat.START);

            Fragment fragment = null;

            switch (item.getItemId()) {
                case (R.id.nav_home):
                    // Switch to home screen fragment
                    fragment = HomeScreen.newInstance();
                    break;
                case (R.id.nav_wardrobe):
                    fragment = Wardrobe.newInstance();
                    // Switch to wardrobe fragment
                    break;
                case (R.id.nav_settings):
                    // Open settings activity
                    Intent start_settings = new Intent(getApplicationContext(), Settings.class);
                    startActivity(start_settings);
                    break;
            }

            if (fragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_area, fragment).commit();
            }

            return true;
        });

        Repository.initDB(getApplicationContext());
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
}
