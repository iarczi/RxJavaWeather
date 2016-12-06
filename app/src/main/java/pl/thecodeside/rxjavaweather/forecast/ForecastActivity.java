package pl.thecodeside.rxjavaweather.forecast;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.application.WeatherApplication;
import pl.thecodeside.rxjavaweather.settings.SettingsActivity;
import pl.thecodeside.rxjavaweather.utils.ActivityUtils;

public class ForecastActivity extends AppCompatActivity {


    @Inject
    ForecastPresenter forecastPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ForecastFragment forecastFragment =
                (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_container);
        if (forecastFragment == null) {
            // Create the fragment
            forecastFragment = ForecastFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), forecastFragment, R.id.activity_main_container);
        }

        // Create the presenter
        DaggerForecastComponent.builder()
                .forecastRepositoryComponent(((WeatherApplication) getApplication()).getForecastRepositoryComponent())
                .forecastPresenterModule(
                        (new ForecastPresenterModule(forecastFragment,
                                ((WeatherApplication) getApplication())
                                        .getSchedulerProviderComponent().getSchedulerProvider(),
                                PreferenceManager.getDefaultSharedPreferences(this))))
                .build()
                .inject(this);
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
