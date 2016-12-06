package pl.thecodeside.rxjavaweather.prototypes;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
