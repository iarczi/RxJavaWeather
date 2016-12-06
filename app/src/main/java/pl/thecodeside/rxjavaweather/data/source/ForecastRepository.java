package pl.thecodeside.rxjavaweather.data.source;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Forecast;
import pl.thecodeside.rxjavaweather.data.json.ForecastSourceModel;
import pl.thecodeside.rxjavaweather.data.json.WeatherDetails;
import pl.thecodeside.rxjavaweather.forecast.WeatherAdapter;
import pl.thecodeside.rxjavaweather.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Artur Latoszewski on 03.12.2016.
 */
@Singleton
public class ForecastRepository {
    private static ForecastRepository forecastRepository;
    private forecastApi forecastApi;

    @Inject
    public ForecastRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_WEATHER_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        forecastApi = retrofit.create(forecastApi.class);
    }


    public static ForecastRepository getInstance() {
        if (forecastRepository == null) {
            forecastRepository = new ForecastRepository();
        }
        return forecastRepository;
    }

    public Observable<ForecastSourceModel> getForecast(String city, String units) {
        return forecastApi.getWeather(city, "7", Constants.BASE_WEATHER_API_KEY, "json", units);
    }

    public List<Forecast> forecastConverter(ForecastSourceModel forecastSourceModel) {
        final List<Forecast> forecastList = new ArrayList<>();
        int position = 0;

        for (ForecastSourceModel.WeatherMain main : forecastSourceModel.weatherMainList) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(GregorianCalendar.DATE, position);
            Date time = gregorianCalendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd");

            Forecast forecast = new Forecast(
                    main.fullDetails.tempMax
                    , main.fullDetails.tempMin
                    , main.fullDetails.pressure
                    , main.fullDetails.humidity
                    , ""
                    , ""
                    , sdf.format(time)
                    , "");
            for (WeatherDetails weatherDetails : main.weatherDetailsList
                    ) {
                forecast.setWeatherIco(weatherDetails.getWeatherIcon());
                forecast.setWeatherBasic(weatherDetails.getBasicWeatherDescription());
                forecast.setWeatherDetail(weatherDetails.getDetailedWeatherDescription());

            }

            forecastList.add(forecast);
            ++position;
        }

        /*final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_FIREBASE_PATH + appId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    int index = 0;
                    for (Forecast forecast : forecastList) {
                        reference.child(Integer.toString(index)).setValue(forecast);
                        ++index;
                    }
                } else {
                    int index = 0;
                    for (Forecast forecast : forecastList) {
                        Map newWeatherData = new HashMap();

                        newWeatherData.put("humidity", forecast.getHumidity());
                        newWeatherData.put("temperatureMax", forecast.getTemperatureMax());
                        newWeatherData.put("temperatureMin", forecast.getTemperatureMin());
                        newWeatherData.put("pressure", forecast.getPressure());
                        newWeatherData.put("weatherBasic", forecast.getWeatherBasic());
                        newWeatherData.put("weatherDetail", forecast.getWeatherDetail());
                        newWeatherData.put("weatherDate", forecast.getWeatherDate());
                        newWeatherData.put("weatherIcon", forecast.getWeatherIcon());
                        newWeatherData.put("weatherUrl", forecast.getWeatherUrl());

                        reference.child(Integer.toString(index)).updateChildren(newWeatherData);
                        ++index;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        return forecastList;
    }


    public ValueEventListener readFromFirebase(DatabaseReference reference, final WeatherAdapter adapter, final Context context) {
        return reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Forecast> forecastList = new ArrayList<Forecast>();
                if (dataSnapshot != null && dataSnapshot.hasChildren()) {
                    for (DataSnapshot weatherData : dataSnapshot.getChildren()) {
                        Forecast forecast = weatherData.getValue(Forecast.class);
                        forecastList.add(forecast);
                    }
                }
                if (forecastList.isEmpty()) {
                    Toast.makeText(context, R.string.check_internet_toast, Toast.LENGTH_LONG).show();

                }
                adapter.setForecastList(forecastList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
