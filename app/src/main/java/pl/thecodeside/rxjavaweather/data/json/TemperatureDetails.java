package pl.thecodeside.rxjavaweather.data.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class TemperatureDetails {

    @SerializedName("min")
    double temperatureMin;

    @SerializedName("max")
    double temperatureMax;

    public TemperatureDetails() {
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public double getTemperatureMin() {

        return temperatureMin;
    }
}
