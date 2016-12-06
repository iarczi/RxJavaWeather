package pl.thecodeside.rxjavaweather.data.source;

import pl.thecodeside.rxjavaweather.data.json.ForecastSourceModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public interface ForecastApi {

    @GET("/data/2.5/forecast")
    Observable<ForecastSourceModel> getForecast(
            @Query("q") String city,
            @Query("cnt") String days,
            @Query("appid") String appid,
            @Query("mode") String mode,
            @Query("units") String units
    );
}
