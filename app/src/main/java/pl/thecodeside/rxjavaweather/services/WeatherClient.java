package pl.thecodeside.rxjavaweather.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.thecodeside.rxjavaweather.data.Weather;
import pl.thecodeside.rxjavaweather.json.WeatherDetails;
import pl.thecodeside.rxjavaweather.json.WeatherListModel;
import pl.thecodeside.rxjavaweather.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Artur Latoszewski on 03.12.2016.
 */

public class WeatherClient {
    private static WeatherClient weatherClient;
    private APIWeather apiWeather;

    private WeatherClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_WEATHER_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiWeather = retrofit.create(APIWeather.class);
    }


    //TODO reorganize with Dagger
    public static WeatherClient getInstance() {
        if (weatherClient == null) {
            weatherClient = new WeatherClient();
        }
        return weatherClient;
    }

    public Observable<WeatherListModel> getWeather(String city, String units) {
        return apiWeather.getWeather(city, "7", Constants.BASE_WEATHER_API_KEY, "json", units);
    }

    public List<Weather> weatherConverter(WeatherListModel weatherListModel) {
        List<Weather> weatherList = new ArrayList<>();
        int position = 0;

        for (WeatherListModel.WeatherMain main : weatherListModel.weatherMainList) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(GregorianCalendar.DATE, position++);
            Date time = gregorianCalendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd");

            Weather weather = new Weather(
                    main.fullDetails.tempMax
                    , main.fullDetails.tempMin
                    , main.fullDetails.pressure
                    , main.fullDetails.humidity
                    , ""
                    , ""
                    , sdf.format(time));
            for (WeatherDetails weatherDetails : main.weatherDetailsList
                    ) {
                weather.setWeatherBasic(weatherDetails.getBasicWeatherDescription());
                weather.setWeatherDetail(weatherDetails.getDetailedWeatherDescription());

            }

            weatherList.add(weather);
        }


        return weatherList;
    }

}
