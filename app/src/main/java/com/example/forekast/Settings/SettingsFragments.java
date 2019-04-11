package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import com.example.forekast.R;

import java.util.List;

public class SettingsFragments extends PreferenceFragment {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String NEW_WARDROBE = "add_wardrobe";
    public static final String WARDROBE_LIST = "user_list";
    public static final String DEFAULT_STRING = "fuck_my_life";
    public static final String EMPTY_STRING = "";
    private CharSequence[] wardrobe_entries = { "Default" };
    private String currentValue = "Default";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        resetWardrobeList();

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(NEW_WARDROBE)) {

                    // Get relevant preferences
                    EditTextPreference new_wardrobe = (EditTextPreference) findPreference(key);
                    ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);

                    // Initialize, copy and add new entry to entries of list preference
                    CharSequence[] entries = new CharSequence[wardrobes.getEntries().length +1];
                    entries = copyOldSequence(entries, wardrobes);
                    entries[wardrobes.getEntries().length] = sharedPreferences.getString(key, DEFAULT_STRING);

                    // Set entries and input text to desired entries
                    new_wardrobe.setText(EMPTY_STRING);
                    setNewEntries(entries, wardrobes);

                    wardrobe_entries = entries;
                    currentValue = wardrobes.getValue();
                }
            }
        };
    }

    public void resetWardrobeList() {
        ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);
        setNewEntries(wardrobe_entries, wardrobes);
        wardrobes.setValue(currentValue);
    }

    public CharSequence[] copyOldSequence(CharSequence[] seq, ListPreference list){
        for (int i = 0; i < list.getEntries().length; i++) {
            seq[i] = list.getEntries()[i];
        }
        return seq;
    }

    public void setNewEntries(CharSequence[] entries, ListPreference list){
        list.setPersistent(true);
        list.setEntries(entries);
        list.setEntryValues(entries);
    }

    @Override
    public void onResume() {
        super.onResume();
        resetWardrobeList();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
