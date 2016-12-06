package pl.thecodeside.rxjavaweather.data.source;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.thecodeside.rxjavaweather.prototypes.Remote;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
@Module
public class ForecastRepositoryModule {

    @Singleton
    @Provides
    @Remote
    ForecastRepository provideForecastRemoteDataSource() {
        return new ForecastRepository();
    }

}
