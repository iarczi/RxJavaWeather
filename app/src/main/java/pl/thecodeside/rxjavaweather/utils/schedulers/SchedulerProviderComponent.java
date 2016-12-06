package pl.thecodeside.rxjavaweather.utils.schedulers;

import javax.inject.Singleton;

import dagger.Component;
import pl.thecodeside.rxjavaweather.application.ApplicationModule;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

@Singleton
@Component(modules = {SchedulerProviderModule.class, ApplicationModule.class})
public interface SchedulerProviderComponent {
    SchedulerProvider getSchedulerProvider();
}