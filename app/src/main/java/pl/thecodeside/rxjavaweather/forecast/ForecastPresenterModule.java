package pl.thecodeside.rxjavaweather.forecast;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import pl.thecodeside.rxjavaweather.prototypes.BaseSchedulerProvider;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
@Module
public class ForecastPresenterModule {
    private final ForecastContract.View view;
    private final BaseSchedulerProvider schedulerProvider;
    private final SharedPreferences sharedPreferences;

    public ForecastPresenterModule(ForecastContract.View view, BaseSchedulerProvider schedulerProvider, SharedPreferences sharedPreferences) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.sharedPreferences = sharedPreferences;
    }

    @Provides
    ForecastContract.View provideForecastContractView() {
        return view;
    }

    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return schedulerProvider;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return sharedPreferences;
    }
}

