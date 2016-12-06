package pl.thecodeside.rxjavaweather.data.source;

import javax.inject.Singleton;

import dagger.Component;
import pl.thecodeside.rxjavaweather.application.ApplicationModule;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
@Singleton
@Component(modules = {ForecastRepositoryModule.class, ApplicationModule.class})
public interface ForecastRepositoryComponent {
    ForecastRepository getForecastRepository();
}

