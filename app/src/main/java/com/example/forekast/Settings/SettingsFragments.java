package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;

import com.example.forekast.Forekast;
import com.example.forekast.R;

import java.util.Objects;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragments extends PreferenceFragmentCompat {
    private static final String NEW_WARDROBE = "add_wardrobe";
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(NEW_WARDROBE)) {
                EditTextPreference new_wardrobe = (EditTextPreference) SettingsFragments.this.findPreference(key);
                //new_wardrobe.setSummary(sharedPreferences.getString(key, "fuck"));
                ListPreference wardrobes = (ListPreference) SettingsFragments.this.findPreference("user_list");
                CharSequence[] entries = new CharSequence[wardrobes.getEntries().length + 1];
                for (int i = 0; i < wardrobes.getEntries().length; i++) {
                    entries[i] = wardrobes.getEntries()[i];
                }
                entries[wardrobes.getEntries().length] = sharedPreferences.getString(key, "fuck");
                new_wardrobe.setText("");
                wardrobes.setEntries(entries);
                wardrobes.setEntryValues(entries);
            }
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        setPreferencesFromResource(R.xml.preferences, rootkey);
        PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext()).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
