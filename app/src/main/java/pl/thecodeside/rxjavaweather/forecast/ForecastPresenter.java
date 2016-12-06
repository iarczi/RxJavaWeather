package pl.thecodeside.rxjavaweather.forecast;

import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Forecast;
import pl.thecodeside.rxjavaweather.data.json.ForecastSourceModel;
import pl.thecodeside.rxjavaweather.data.source.ForecastRepository;
import pl.thecodeside.rxjavaweather.prototypes.BaseSchedulerProvider;
import pl.thecodeside.rxjavaweather.utils.Constants;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public class ForecastPresenter implements ForecastContract.Presenter {

    private final ForecastRepository forecastRepository;

    private final ForecastContract.View forecastView;

    private final SharedPreferences sharedPreferences;
    private final BaseSchedulerProvider schedulerProvider;
    private CompositeSubscription subscriptions;

    @Inject
    ForecastPresenter(ForecastRepository forecastRepository
            , ForecastContract.View forecastView
            , BaseSchedulerProvider schedulerProvider, SharedPreferences preferences) {
        this.forecastRepository = forecastRepository;
        this.forecastView = forecastView;
        this.schedulerProvider = schedulerProvider;
        subscriptions = new CompositeSubscription();
        this.sharedPreferences = preferences;
    }


    @Override
    public void loadForecast(String city, final String units) {

        subscriptions.clear();
        Subscription subscription = forecastRepository
                .getForecast(city, units)
                .map(new Func1<ForecastSourceModel, List<Forecast>>() {
                    @Override
                    public List<Forecast> call(ForecastSourceModel weatherListModel) {
                        return ForecastRepository.getInstance().forecastConverter(weatherListModel);
                    }
                })
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())

                .subscribe(new Observer<List<Forecast>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO analyze error
                        forecastView.showForecastOffline();
                        forecastView.setUnits(isMetricUnits(units));
                    }

                    @Override
                    public void onNext(List<Forecast> forecastList) {
                        processForecast(forecastList);
                        forecastView.setUnits(isMetricUnits(units));
                    }
                });
        subscriptions.add(subscription);
    }

    private boolean isMetricUnits(String units) {
        return units.equals(forecastView.getResources().getString(R.string.units_metric_values));
    }

    private void processForecast(List<Forecast> forecastList) {
        if (forecastList.isEmpty()) {
            forecastView.showNoForecast();
        } else {
            forecastView.showForecasts(forecastList);
        }
    }


    @Override
    public void subscribe() {
        String city = sharedPreferences.getString(Constants.LOCATION_PREFERENCE,
                forecastView.getResources().getString(R.string.location_default));
        String units = sharedPreferences.getString(Constants.UNIT_PREFERENCE,
                forecastView.getResources().getString(R.string.units_metric_values));
        loadForecast(city, units);
    }

    @Inject
    void setupListeners() {
        forecastView.setPresenter(this);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }
}
