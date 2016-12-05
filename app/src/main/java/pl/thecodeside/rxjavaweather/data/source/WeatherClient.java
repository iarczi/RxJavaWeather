package pl.thecodeside.rxjavaweather.data.source;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;
import pl.thecodeside.rxjavaweather.data.json.WeatherDetails;
import pl.thecodeside.rxjavaweather.data.json.WeatherListModel;
import pl.thecodeside.rxjavaweather.forecast.WeatherAdapter;
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

    public List<Weather> weatherConverter(WeatherListModel weatherListModel, String appId) {
        final List<Weather> weatherList = new ArrayList<>();
        int position = 0;

        for (WeatherListModel.WeatherMain main : weatherListModel.weatherMainList) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(GregorianCalendar.DATE, position);
            Date time = gregorianCalendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd");

            Weather weather = new Weather(
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
                weather.setWeatherIco(weatherDetails.getWeatherIcon());
                weather.setWeatherBasic(weatherDetails.getBasicWeatherDescription());
                weather.setWeatherDetail(weatherDetails.getDetailedWeatherDescription());

            }

            weatherList.add(weather);
            ++position;
        }

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_FIREBASE_PATH + appId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    int index = 0;
                    for (Weather weather : weatherList) {
                        reference.child(Integer.toString(index)).setValue(weather);
                        ++index;
                    }
                } else {
                    int index = 0;
                    for (Weather weather : weatherList) {
                        Map newWeatherData = new HashMap();

                        newWeatherData.put("humidity", weather.getHumidity());
                        newWeatherData.put("temperatureMax", weather.getTemperatureMax());
                        newWeatherData.put("temperatureMin", weather.getTemperatureMin());
                        newWeatherData.put("pressure", weather.getPressure());
                        newWeatherData.put("weatherBasic", weather.getWeatherBasic());
                        newWeatherData.put("weatherDetail", weather.getWeatherDetail());
                        newWeatherData.put("weatherDate", weather.getWeatherDate());
                        newWeatherData.put("weatherIcon", weather.getWeatherIcon());
                        newWeatherData.put("weatherUrl", weather.getWeatherUrl());

                        reference.child(Integer.toString(index)).updateChildren(newWeatherData);
                        ++index;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return weatherList;
    }


    public ValueEventListener readFromFirebase(DatabaseReference reference, final WeatherAdapter adapter, final Context context) {
        return reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Weather> weatherList = new ArrayList<Weather>();
                if (dataSnapshot != null && dataSnapshot.hasChildren()) {
                    for (DataSnapshot weatherData : dataSnapshot.getChildren()) {
                        Weather weather = weatherData.getValue(Weather.class);
                        weatherList.add(weather);
                    }
                }
                if (weatherList.isEmpty()) {
                    Toast.makeText(context, R.string.check_internet_toast, Toast.LENGTH_LONG).show();

                }
                adapter.setWeatherList(weatherList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
