package pl.thecodeside.rxjavaweather.forecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.prototypes.BaseFragmentActivity;
import pl.thecodeside.rxjavaweather.settings.SettingsActivity;

public class ForecastActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ForecastFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acttion_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }
}
