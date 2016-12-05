package pl.thecodeside.rxjavaweather.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;
import pl.thecodeside.rxjavaweather.json.WeatherListModel;
import pl.thecodeside.rxjavaweather.services.WeatherClient;
import pl.thecodeside.rxjavaweather.utils.Constants;
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
    private DatabaseReference databaseReference;
    private Subscription weatherSubscription;
    private ValueEventListener listener;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences appPreferences = getActivity().getSharedPreferences(Constants.APP_ID_PREFERENCES, Context.MODE_PRIVATE);

        String appId = appPreferences.getString(Constants.APP_ID, "");
        if (appId.isEmpty()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().push();
            appPreferences.edit().putString(Constants.APP_ID, databaseReference.getKey()).apply();
        } else {
            databaseReference = FirebaseDatabase.getInstance()
                    .getReference(Constants.DATABASE_FIREBASE_PATH + appId);
        }

        String city = sharedPreferences.getString(Constants.LOCATION_PREFERENCE, getString(R.string.location_default));
        String units = sharedPreferences.getString(Constants.UNIT_PREFERENCE, getString(R.string.units_metric_values));
        adapter = new WeatherAdapter(getActivity(), this);
        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvForecast.setAdapter(adapter);

        getWeather(city, units, appId);

    }

    private void getWeather(String city, final String units, final String appId) {
        weatherSubscription = WeatherClient.getInstance()
                .getWeather(city, units)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherListModel, List<Weather>>() {
                    @Override
                    public List<Weather> call(WeatherListModel weatherListModel) {
                        return WeatherClient.getInstance().weatherConverter(weatherListModel, appId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Weather>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener = WeatherClient.getInstance().readFromFirebase(databaseReference, adapter, getActivity());
                        databaseReference.addValueEventListener(listener);
                        Toast.makeText(getContext(), R.string.offilne_mode_toast, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Weather> weathers) {
                        //adapter.setWeatherList(weathers);
                        listener = WeatherClient.getInstance().readFromFirebase(databaseReference, adapter, getActivity());
                        databaseReference.addValueEventListener(listener);
                        adapter.setMetric(isMetricUnits(units));

                    }
                });
    }

    private boolean isMetricUnits(String units) {
        return units.equals(getContext().getString(R.string.units_metric_values));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weatherSubscription != null && !weatherSubscription.isUnsubscribed()) {
            weatherSubscription.unsubscribe();
        }
        if (listener != null) {
            databaseReference.removeEventListener(listener);
        }
    }

    @Override
    public void weatherClicked(Weather weather) {

    }
}
