package pl.thecodeside.rxjavaweather.forecast;

import android.content.res.Resources;

import java.util.List;

import pl.thecodeside.rxjavaweather.data.Forecast;
import pl.thecodeside.rxjavaweather.prototypes.BasePresenter;
import pl.thecodeside.rxjavaweather.prototypes.BaseView;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public interface ForecastContract {
    interface View extends BaseView<Presenter> {

        void showForecasts(List<Forecast> forecastList);

        void showForecastOffline();

        void showNoForecast();

        void setUnits(boolean isMetric);

        Resources getResources();

    }

    interface Presenter extends BasePresenter {

        void loadForecast(String city, String units);

    }

}
