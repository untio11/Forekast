package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.forekast.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragments extends PreferenceFragment {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String NEW_WARDROBE = "add_wardrobe";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(NEW_WARDROBE)) {
                    Preference new_wardrobe = findPreference(key);
                    //new_wardrobe.setSummary(sharedPreferences.getString(key, "fuck"));
                    ListPreference wardrobes = (ListPreference) findPreference("user_list");
                    CharSequence[] entries = new CharSequence[wardrobes.getEntries().length+1];
                    for(int i = 0; i < wardrobes.getEntries().length; i++) {
                        entries[i] = wardrobes.getEntries()[i];
                    }
                    entries[wardrobes.getEntries().length] = sharedPreferences.getString(key, "fuck");
                    wardrobes.setEntries(entries);
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
