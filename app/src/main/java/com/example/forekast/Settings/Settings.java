package com.example.forekast.Settings;

import android.content.Intent;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.homescreen.HomeScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public boolean useActualLocation;
    public boolean useAvailabilitySystem;
    public String manualLocation;
    public List<String> owners;
    public int unavailabilityDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        } else if (id == R.id.nav_wardrobe) {
            startActivity(new Intent(getApplicationContext(), Wardrobe.class));
        } else if (id == R.id.nav_settings) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_switchwardrobe) {
            startActivity(new Intent(getApplicationContext(), SwitchWardrobe.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Settings methods
     */
    private void switchLocationSetting() {
        if(useActualLocation) {
            useActualLocation = false;
        } else {
            useActualLocation = true;
        }
    }

    private void switchAvailabilitySystem() {
        if(useAvailabilitySystem) {
            useAvailabilitySystem = false;
        } else {
            useAvailabilitySystem = true;
        }
    }

    private void setUnavailabilityDuration(int duration) {
        this.unavailabilityDuration = duration;
    }

    private void setManualLocation(String mlocation) {
        this.manualLocation = mlocation;
    }

    private void newOwner(String newOwnerName) {
        owners.add(newOwnerName);
    }
}
