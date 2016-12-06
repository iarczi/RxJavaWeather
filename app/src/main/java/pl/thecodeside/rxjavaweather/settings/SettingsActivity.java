package pl.thecodeside.rxjavaweather.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Artur Latoszewski on 04.12.2016.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsPreferencesFragment())
                .commit();
    }


}
