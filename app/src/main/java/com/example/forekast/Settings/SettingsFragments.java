package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;

import com.example.forekast.R;

import java.util.HashSet;
import java.util.Set;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragments extends PreferenceFragmentCompat {
    private static final String NEW_WARDROBE = "add_wardrobe";
    private static final String WARDROBE_LIST = "user_list";
    private static final String WARDROBES = "wardrobes";

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        /**
         * Ensures that a new wardrobe is added to the shared preferences when added through the menu.
         * @param sharedPreferences Shared Preferences object of our app
         * @param key The key of the preference item that was pressed.
         */
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

    /**
     * Ensures the wardrobes that are shown in the preference menu correspond with the actual wardrobes
     * that are stored by the user.
     */
    private void updateWardrobeList() {
        ListPreference wardrobes = (ListPreference) SettingsFragments.this.findPreference(WARDROBE_LIST);
        Set<String> names = getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBES, new HashSet<>());

        CharSequence[] entries = new CharSequence[names.size()];
        int i = 0;

        for (String name : names) {
            entries[i++] = name;
        }

        wardrobes.setEntryValues(entries);
        wardrobes.setEntries(entries);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        setPreferencesFromResource(R.xml.preferences, rootkey);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        updateWardrobeList();
    }
}
