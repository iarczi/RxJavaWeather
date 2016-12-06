package pl.thecodeside.rxjavaweather.application;

import android.app.Application;

import pl.thecodeside.rxjavaweather.data.source.DaggerForecastRepositoryComponent;
import pl.thecodeside.rxjavaweather.data.source.ForecastRepositoryComponent;
import pl.thecodeside.rxjavaweather.utils.schedulers.DaggerSchedulerProviderComponent;
import pl.thecodeside.rxjavaweather.utils.schedulers.SchedulerProviderComponent;

/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

public class WeatherApplication extends Application {

    private ForecastRepositoryComponent forecastRepositoryComponent;

    private SchedulerProviderComponent schedulerProviderComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        forecastRepositoryComponent = DaggerForecastRepositoryComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();

        schedulerProviderComponent = DaggerSchedulerProviderComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();

    }

    public SchedulerProviderComponent getSchedulerProviderComponent() {
        return schedulerProviderComponent;
    }

    public ForecastRepositoryComponent getForecastRepositoryComponent() {
        return forecastRepositoryComponent;
    }


}
