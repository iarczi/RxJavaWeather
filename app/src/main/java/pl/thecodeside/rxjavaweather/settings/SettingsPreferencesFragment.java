package pl.thecodeside.rxjavaweather.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.utils.Constants;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public class SettingsPreferencesFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_general);
        bindPreferencesSummaryToValue(findPreference(getString(R.string.location_key)));
        bindPreferencesSummaryToValue(findPreference(getString(R.string.units_key)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        setPreferenceSummary(preference, newValue);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (preference instanceof EditTextPreference) {
            editor.putString(Constants.LOCATION_PREFERENCE, newValue.toString()).apply();
        } else if (preference instanceof ListPreference) {
            editor.putString(Constants.UNIT_PREFERENCE, newValue.toString()).apply();
        }
        return true;
    }

    private void bindPreferencesSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        setPreferenceSummary(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private void setPreferenceSummary(Preference preference, Object vale) {
        String stringValue = vale.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int preferenceIndex = listPreference.findIndexOfValue(stringValue);
            if (preferenceIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[preferenceIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            preference.setSummary(stringValue);
        }
    }
}
