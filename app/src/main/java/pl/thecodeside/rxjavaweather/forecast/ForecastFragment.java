package pl.thecodeside.rxjavaweather.forecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Forecast;


public class ForecastFragment extends Fragment implements ForecastContract.View, WeatherAdapter.WeatherListener {



    @BindView(R.id.rvForecast)
    RecyclerView rvForecast;

    private WeatherAdapter adapter;
    /*private DatabaseReference databaseReference;
    private ValueEventListener listener;*/

    private ForecastContract.Presenter presenter;

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

        adapter = new WeatherAdapter(getActivity(), this);
        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvForecast.setAdapter(adapter);

        presenter.subscribe();

    }

    /*

    String appId = sharedPreferences.getString(Constants.APP_ID, "");
    if (appId.isEmpty()) {
        databaseReference = FirebaseDatabase.getInstance().getReference().push();
        sharedPreferences.edit().putString(Constants.APP_ID, databaseReference.getKey()).apply();
    } else {
        databaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_FIREBASE_PATH + appId);
    }*/

    /*private void getWeather(String city, final String units, final String appId) {
        weatherSubscription = ForecastRepository.getInstance()
                .getForecast(city, units)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ForecastSourceModel, List<Forecast>>() {
                    @Override
                    public List<Forecast> call(ForecastSourceModel weatherListModel) {
                        return ForecastRepository.getInstance().forecastConverter(weatherListModel, appId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Forecast>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener = ForecastRepository.getInstance().readFromFirebase(databaseReference, adapter, getActivity());
                        databaseReference.addValueEventListener(listener);
                        Toast.makeText(getContext(), R.string.offilne_mode_toast, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Forecast> forecasts) {
                        //adapter.setForecastList(forecasts);
                        listener = ForecastRepository.getInstance().readFromFirebase(databaseReference, adapter, getActivity());
                        databaseReference.addValueEventListener(listener);


                    }
                });
    }*/



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        /*if (weatherSubscription != null && !weatherSubscription.isUnsubscribed()) {
            weatherSubscription.unsubscribe();
        }
        if (listener != null) {
            databaseReference.removeEventListener(listener);
        }*/
    }

    @Override
    public void weatherClicked(Forecast forecast) {

    }

    @Override
    public void showForecasts(List<Forecast> forecastList) {
        adapter.setForecastList(forecastList);
    }

    @Override
    public void showForecastOffline() {
        Toast.makeText(getContext(), R.string.offilne_mode_toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoForecast() {
        Toast.makeText(getContext(), R.string.check_internet_toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setUnits(boolean isMetric) {
        adapter.setUnits(isMetric);
    }

    @Override
    public void setPresenter(ForecastContract.Presenter presenter) {
        this.presenter = presenter;
    }




}
