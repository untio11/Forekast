package com.example.forekast.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.storage.StorageManager;

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
    public static final String DEFAULT_STRING = "something_went_wrong";
    public static final String STORAGE = "storage";
    public static final String EMPTY_STRING = "";
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(Arrays.asList("Default1"));

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext()).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);
        wardrobes.setEntries(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(STORAGE, DEFAULT_STRING_SET)));


        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(NEW_WARDROBE)) {

                    // Get relevant preferences
                    EditTextPreference new_wardrobe = (EditTextPreference) findPreference(NEW_WARDROBE);
                    ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);

                    // Initialize, copy and add new entry to entries of list preference
                    CharSequence[] entries = new CharSequence[wardrobes.getEntries().length +1];
                    entries = copyOldSequence(entries, wardrobes);
                    entries[wardrobes.getEntries().length] = sharedPreferences.getString(NEW_WARDROBE, DEFAULT_STRING);

                    // Set entries and input text to desired entries
                    new_wardrobe.setText(EMPTY_STRING);
                    setNewEntries(entries, wardrobes);

                    // Update storage preference
                    SharedPreferences prefs = getPreferenceScreen().getSharedPreferences();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.remove(STORAGE);
                    editor.putStringSet(STORAGE, charSequenceToStringSet(entries)).apply();
                }
            }
        };
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

    public CharSequence[] stringSetToCharSequence (Set<String> set){
        CharSequence[] entries = new CharSequence[set.size()];
        String[] stringArray = new String[set.size()];
        set.toArray(stringArray);
        for (int i = 0; i < set.size(); i++) {
            entries[i] = stringArray[i];
        }
        return entries;
    }

    public Set<String> charSequenceToStringSet (CharSequence[] seq){
        Set<String> set = new HashSet<>();
        for(int i = 0; i < set.size(); i++) {
            set.add(seq[i].toString());
        }
        return set;
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        ListPreference wardrobes = (ListPreference) findPreference(WARDROBE_LIST);
        wardrobes.setEntries(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(STORAGE, DEFAULT_STRING_SET)));
        wardrobes.setEntryValues(stringSetToCharSequence(getPreferenceScreen().getSharedPreferences().getStringSet(STORAGE, DEFAULT_STRING_SET)));
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
