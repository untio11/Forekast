package com.example.forekast.Settings;

import android.os.Bundle;

import com.example.forekast.R;

import androidx.preference.PreferenceFragment;

public class SettingsFragments extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.preferences);
    }
}
