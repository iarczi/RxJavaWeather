package pl.thecodeside.rxjavaweather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class WeatherListModel {
    @SerializedName("list")
    WeatherListModel weatherListModel;

    public WeatherListModel getWeatherListModel() {
        return weatherListModel;
    }

    public WeatherListModel() {

    }
}
