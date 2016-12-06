package pl.thecodeside.rxjavaweather.data;

import pl.thecodeside.rxjavaweather.utils.Constants;

/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

public class Forecast {
    private double temperatureMax;
    private double temperatureMin;
    private double pressure;
    private double humidity;

    public Forecast() {
    }

    private String weatherBasic;

    private String weatherDetail;
    private String weatherDate;
    private String weatherIcon;

    public Forecast(double temperatureMax, double temperatureMin,
                    double pressure, double humidity, String weatherBasic, String weatherDetail, String weatherDate, String weatherIcon) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherBasic = weatherBasic;
        this.weatherDetail = weatherDetail;
        this.weatherDate = weatherDate;
        this.weatherIcon = weatherIcon;
    }


    public void setWeatherDetail(String weatherDetail) {
        this.weatherDetail = weatherDetail;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public void setWeatherIco(String weatherIco) {
        this.weatherIcon = weatherIco;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getWeatherBasic() {
        return weatherBasic;
    }

    public String getWeatherDetail() {
        return weatherDetail;
    }

    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherBasic(String weatherBasic) {
        this.weatherBasic = weatherBasic;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherUrl() {
        return Constants.BASE_WEATHER_ICON_URL + weatherIcon + ".png";
    }
}
