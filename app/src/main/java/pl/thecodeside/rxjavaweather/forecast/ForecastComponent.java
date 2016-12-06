package pl.thecodeside.rxjavaweather.forecast;

import dagger.Component;
import pl.thecodeside.rxjavaweather.data.source.ForecastRepositoryComponent;
import pl.thecodeside.rxjavaweather.prototypes.FragmentScoped;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
@FragmentScoped
@Component(dependencies = ForecastRepositoryComponent.class, modules = ForecastPresenterModule.class)
public interface ForecastComponent {
    void inject(ForecastActivity activity);

}
