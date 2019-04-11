package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import com.example.forekast.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.Objects;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragments extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String NEW_WARDROBE = "add_wardrobe";
    public static final String WARDROBE_LIST = "user_list";
    public static final String WARDROBES = "wardrobes";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext()).registerOnSharedPreferenceChangeListener(preferenceChangeListener);


        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals(NEW_WARDROBE)) {
                    SharedPreferences.Editor shedit = sharedPreferences.edit();
                    EditTextPreference name_field = ((EditTextPreference) SettingsFragments.this.findPreference(key));
                    String new_wardrobe = name_field.getText();

                    Set<String> wardrobes = sharedPreferences.getStringSet(WARDROBES, new HashSet<>());
                    wardrobes.add(new_wardrobe);

                    shedit.putStringSet(WARDROBES, wardrobes);
                    name_field.setText("");

                    shedit.commit();
                }

                updateWardrobeList();
                Log.d("Current Wardrobe", sharedPreferences.getString(WARDROBE_LIST, "DEFAULT"));
            }
        };
    }
    private void updateWardrobeList() {
        ListPreference wardrobes = (ListPreference) SettingsFragments.this.findPreference(WARDROBE_LIST);
        Set<String> names = getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBES, new HashSet<>(Arrays.asList("Default (No personal wardrobes added)")));

        CharSequence[] entries = new CharSequence[names.size()];
        int i = 0;

        for (String name : names) {
            entries[i++] = name;
        }

        wardrobes.setEntryValues(entries);
        wardrobes.setEntries(entries);
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        updateWardrobeList();
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
