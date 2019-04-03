package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import com.example.forekast.R;

import java.util.prefs.PreferencesFactory;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragments extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String NEW_WARDROBE = "add_wardrobe";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(NEW_WARDROBE)) {
                    EditTextPreference new_wardrobe = (EditTextPreference) findPreference(key);
                    //new_wardrobe.setSummary(sharedPreferences.getString(key, "fuck"));
                    ListPreference wardrobes = (ListPreference) findPreference("user_list");
                    CharSequence[] entries = new CharSequence[wardrobes.getEntries().length+1];
                    for(int i = 0; i < wardrobes.getEntries().length; i++) {
                        entries[i] = wardrobes.getEntries()[i];
                    }
                    entries[wardrobes.getEntries().length] = sharedPreferences.getString(key, "fuck");
                    new_wardrobe.setText("");
                    wardrobes.setEntries(entries);
                    wardrobes.setEntryValues(entries);
                }
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
