package pl.thecodeside.rxjavaweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class WeatherFullDetails {
    @SerializedName("pressure")
    double pressure;

    @SerializedName("speed")
    double speed;

    @SerializedName("humidity")
    double humidity;

    @SerializedName("temp")
    TemperatureDetails temperatureDetails;

    @SerializedName("weather")
    List<WeatherDetails> weatherDetails;

    public double getPressure() {
        return pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHumidity() {
        return humidity;
    }

    public TemperatureDetails getTemperatureDetails() {
        return temperatureDetails;
    }

    public List<WeatherDetails> getWeatherDetails() {
        return weatherDetails;
    }

    public WeatherFullDetails() {

    }
}
