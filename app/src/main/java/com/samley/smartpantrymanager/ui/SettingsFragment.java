package com.samley.smartpantrymanager.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.samley.smartpantrymanager.R;

/**
 * Settings screen. Satisfies the minimum-screens requirement (Section 3.1).
 * Holds a toggle for 'expiring-soon alerts' and a units preference, persisted
 * automatically via SharedPreferences by the AndroidX Preference library.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
