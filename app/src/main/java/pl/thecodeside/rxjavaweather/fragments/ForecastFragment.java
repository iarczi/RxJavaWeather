package pl.thecodeside.rxjavaweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;
import pl.thecodeside.rxjavaweather.json.WeatherListModel;
import pl.thecodeside.rxjavaweather.services.WeatherClient;
import pl.thecodeside.rxjavaweather.views.WeatherAdapter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ForecastFragment extends Fragment implements WeatherAdapter.WeatherListener {

    @BindView(R.id.rvForecast)
    RecyclerView rvForecast;

    private WeatherAdapter adapter;
    private List<Weather> weatherList;

    private Subscription weatherSubscription;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, rootView);
        adapter = new WeatherAdapter(getActivity(), this);
        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvForecast.setAdapter(adapter);

        getWeather("Guardalavaca", "metric");
        return rootView;
    }

    private void getWeather(String city, String units) {
        weatherSubscription = WeatherClient.getInstance()
                .getWeather(city, units)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherListModel, List<Weather>>() {
                    @Override
                    public List<Weather> call(WeatherListModel weatherListModel) {
                        return WeatherClient.getInstance().weatherConverter(weatherListModel);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Weather>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Weather> weathers) {
                        adapter.setWeatherList(weathers);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weatherSubscription != null && !weatherSubscription.isUnsubscribed()) {
            weatherSubscription.unsubscribe();
        }
    }

    @Override
    public void weatherClicked(Weather weather) {

    }
}
