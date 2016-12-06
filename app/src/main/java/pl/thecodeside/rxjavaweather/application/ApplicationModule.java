package pl.thecodeside.rxjavaweather.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

@Module
public class ApplicationModule {

    private final Context context;

    ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    Application provideApplication() {
        return (WeatherApplication) context;
    }

    @Provides
    Resources provideResources() {
        return context.getResources();
    }


}