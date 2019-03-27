package com.example.forekast.external_data;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

class Location {

    // Reference to tye system location manager
    LocationManager locationManager = (LocationManager)
            this.getSystemService(Context.LOCATION_SERVICE);

    // Location listener that responds to updates
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    // Link listener to location manager to reciever upodates
    LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
}
