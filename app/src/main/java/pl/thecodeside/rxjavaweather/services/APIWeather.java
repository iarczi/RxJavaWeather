package pl.thecodeside.rxjavaweather.services;

import pl.thecodeside.rxjavaweather.json.WeatherListModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public interface APIWeather {

    @GET("/data/2.5/forecast")
    Observable<WeatherListModel> getWeather(
            @Query("q") String city,
            @Query("cnt") String days,
            @Query("appid") String appid,
            @Query("mode") String mode,
            @Query("units") String units
    );
}
