package com.example.forekast.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.forekast.R;

public class SettingsFragments extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
