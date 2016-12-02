package pl.thecodeside.rxjavaweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;
import pl.thecodeside.rxjavaweather.views.WeatherAdapter;


public class ForecastFragment extends Fragment implements WeatherAdapter.WeatherListener {

    @BindView(R.id.rvForecast)
    RecyclerView rvForecast;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, rootView);
        WeatherAdapter adapter = new WeatherAdapter(getActivity(), this);
        adapter.setWeatherList(getWeather());
        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvForecast.setAdapter(adapter);
        return rootView;
    }

    private List<Weather> getWeather() {
        List<Weather> weatherList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(GregorianCalendar.DATE, i);

            Date date = gregorianCalendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EE MM dd");
            Weather weather = new Weather(100.0, 80, 25, 36, "clear", "very clear", sdf.format(date));
            weatherList.add(weather);
        }
        return weatherList;
    }

    @Override
    public void weatherClicked(Weather weather) {

    }
}
