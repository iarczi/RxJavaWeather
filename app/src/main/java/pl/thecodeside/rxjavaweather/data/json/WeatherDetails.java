package pl.thecodeside.rxjavaweather.data.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class WeatherDetails {
    @SerializedName("main")
    String basicWeatherDescription;

    @SerializedName("description")
    String detailedWeatherDescription;

    @SerializedName("icon")
    String weatherIcon;

    public WeatherDetails() {
    }

    public String getBasicWeatherDescription() {
        return basicWeatherDescription;
    }

    public String getDetailedWeatherDescription() {
        return detailedWeatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
