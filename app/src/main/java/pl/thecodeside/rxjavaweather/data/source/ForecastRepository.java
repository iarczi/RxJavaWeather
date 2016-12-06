package pl.thecodeside.rxjavaweather.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import pl.thecodeside.rxjavaweather.forecast.ForecastAdapter;
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
    private ForecastApi ForecastApi;

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

        ForecastApi = retrofit.create(ForecastApi.class);
    }


    public static ForecastRepository getInstance() {
        if (forecastRepository == null) {
            forecastRepository = new ForecastRepository();
        }
        return forecastRepository;
    }

    public Observable<ForecastSourceModel> getForecast(String city, String units) {
        return ForecastApi.getForecast(city, "7", Constants.BASE_WEATHER_API_KEY, "json", units);
    }

    public List<Forecast> forecastConverter(ForecastSourceModel forecastSourceModel) {
        final List<Forecast> forecastList = new ArrayList<>();
        int daysSinceToday = 0;

        for (ForecastSourceModel.ForecastBody main : forecastSourceModel.weatherMainList) {
            Forecast forecast = convertForecastBody(daysSinceToday, main);

            forecastList.add(forecast);
            ++daysSinceToday;
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

    @NonNull
    private Forecast convertForecastBody(int daysSinceToday, ForecastSourceModel.ForecastBody main) {
        Forecast forecast = new Forecast(
                main.fullDetails.tempMax
                , main.fullDetails.tempMin
                , main.fullDetails.pressure
                , main.fullDetails.humidity
                , ""
                , ""
                , getFormattedDaySinceToday(daysSinceToday)
                , "");
        convertForecastDetails(main, forecast);
        return forecast;
    }

    @NonNull
    private String getFormattedDaySinceToday(int daysSinceToday) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(GregorianCalendar.DATE, daysSinceToday);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd");
        return sdf.format(gregorianCalendar.getTime());

    }

    private void convertForecastDetails(ForecastSourceModel.ForecastBody main, Forecast forecast) {
        for (WeatherDetails weatherDetails : main.weatherDetailsList
                ) {
            applyDetailsToForecast(forecast, weatherDetails);

        }
    }

    private void applyDetailsToForecast(Forecast forecast, WeatherDetails weatherDetails) {
        forecast.setWeatherIco(weatherDetails.getWeatherIcon());
        forecast.setWeatherBasic(weatherDetails.getBasicWeatherDescription());
        forecast.setWeatherDetail(weatherDetails.getDetailedWeatherDescription());
    }


    public ValueEventListener readFromFirebase(DatabaseReference reference, final ForecastAdapter adapter, final Context context) {
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
