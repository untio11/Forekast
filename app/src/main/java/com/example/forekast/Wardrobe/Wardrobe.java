package com.example.forekast.Wardrobe;

import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.forekast.homescreen.HomeScreen;
import com.example.forekast.R;
import com.example.forekast.Settings.Settings;
import com.example.forekast.Settings.SwitchWardrobe;

import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

public class Wardrobe extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WardrobeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Fragment fragment = WardrobeFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.wardrobefragment, fragment).commit();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        int id = item.getItemId();
        item.setChecked(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_home) {
            //Intent start_homescreen = new Intent(getApplicationContext(), HomeScreen.class);
            //start_homescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(start_homescreen);
            super.onBackPressed();
        } else if (id == R.id.nav_settings) {
            Intent start_settings = new Intent(getApplicationContext(), Settings.class);
            start_settings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start_settings);
        } else if (id == R.id.nav_switchwardrobe) {
            Intent start_wardrobeswitch = new Intent(getApplicationContext(), SwitchWardrobe.class);
            start_wardrobeswitch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start_wardrobeswitch);
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
