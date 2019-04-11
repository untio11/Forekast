package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import com.example.forekast.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import java.util.Objects;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragments extends PreferenceFragmentCompat {
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String NEW_WARDROBE = "add_wardrobe";
    public static final String WARDROBE_LIST = "user_list";
    public static final String DEFAULT_STRING = "fuck_my_life";
    public static final String STORAGE = "storage";
    public static final String EMPTY_STRING = "";
    private CharSequence[] wardrobe_entries = { "Default" };
    private String currentValue = "Default";
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(Arrays.asList("a", "b"));

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext()).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        resetWardrobeList();


        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(NEW_WARDROBE)) {

                    // Get relevant preferences
                    EditTextPreference new_wardrobe = (EditTextPreference) findPreference(key);
                    ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);
                    MultiSelectListPreference storage = (MultiSelectListPreference) findPreference(STORAGE);
                    new_wardrobe.setSummary(sharedPreferences.getString(key, DEFAULT_STRING));

                    // Initialize, copy and add new entry to entries of list preference
                    CharSequence[] entries = new CharSequence[wardrobes.getEntries().length +1];
                    entries = copyOldSequence(entries, wardrobes);
                    entries[wardrobes.getEntries().length] = sharedPreferences.getString(key, DEFAULT_STRING);

                    //// Set entries and input text to desired entries
                    //new_wardrobe.setText(EMPTY_STRING);
                    setNewEntries(entries, wardrobes);
                    setNewEntries(entries, storage);
//
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
        list.setEntries(entries);
        list.setEntryValues(entries);
    }

    public void setNewEntries(CharSequence[] entries, MultiSelectListPreference list){
        list.setEntries(entries);
        list.setEntryValues(entries);
    }

    public CharSequence[] stringSetToCharSequence (Set<String> set){
        CharSequence[] entries = new CharSequence[set.size()];
        String[] stringArray = new String[set.size()];
        set.toArray(stringArray);
        for (int i = 0; i < set.size(); i++) {
            entries[i] = stringArray[i];
        }
        return entries;
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        Preference new_wardrobe = findPreference(NEW_WARDROBE);
        new_wardrobe.setSummary(getPreferenceScreen().getSharedPreferences().getString(NEW_WARDROBE, DEFAULT_STRING));


        ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);
        //CharSequence[] entries = stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBE_LIST, DEFAULT_STRING_SET));
        //Set<> derpSet = getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBE_LIST, DEFAULT_STRING_SET);


        setNewEntries(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(STORAGE, DEFAULT_STRING_SET)), wardrobes);
        //wardrobes.setEntries(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBE_LIST, DEFAULT_STRING_SET)));
        //wardrobes.setEntryValues(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(WARDROBE_LIST, DEFAULT_STRING_SET)));

        //String[] GPXFILES1 = myset.toArray(new String[myset.size()]);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
