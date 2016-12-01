package pl.thecodeside.rxjavaweather.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

@Module
public class AppModule {

    protected final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application;
    }
}