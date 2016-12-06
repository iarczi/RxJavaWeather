package pl.thecodeside.rxjavaweather.utils.schedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.thecodeside.rxjavaweather.prototypes.Remote;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */


@Module
public class SchedulerProviderModule {

    @Singleton
    @Provides
    @Remote
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

}