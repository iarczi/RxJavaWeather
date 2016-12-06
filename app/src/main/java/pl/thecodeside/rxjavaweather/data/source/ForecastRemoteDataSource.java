package pl.thecodeside.rxjavaweather.data.source;

import pl.thecodeside.rxjavaweather.data.json.ForecastSourceModel;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
public class ForecastRemoteDataSource implements forecastApi {

    @Override
    public Observable<ForecastSourceModel> getWeather(@Query("q") String city, @Query("cnt") String days, @Query("appid") String appid, @Query("mode") String mode, @Query("units") String units) {
        return null;
    }
}
