package pl.thecodeside.rxjavaweather.settings;

import pl.thecodeside.rxjavaweather.forecast.ForecastContract;
import pl.thecodeside.rxjavaweather.prototypes.BasePresenter;
import pl.thecodeside.rxjavaweather.prototypes.BaseView;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public interface SettingsContract {
    interface View extends BaseView<ForecastContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
