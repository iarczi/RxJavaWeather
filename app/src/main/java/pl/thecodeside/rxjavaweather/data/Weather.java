package pl.thecodeside.rxjavaweather.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

public class Weather implements Parcelable {
    private double temperatureMax;
    private double temperatureMin;
    private double pressure;
    private double humidity;
    private String weatherBasic;
    private String weatherDetail;
    private String weatherDate;

    public Weather() {
    }

    public Weather(double temperatureMax, double temperatureMin,
                   double pressure, double humidity, String weatherBasic,
                   String weatherDetail, String weatherDate) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weatherBasic = weatherBasic;
        this.weatherDetail = weatherDetail;
        this.weatherDate = weatherDate;
    }

    protected Weather(Parcel in) {
        temperatureMax = in.readDouble();
        temperatureMin = in.readDouble();
        pressure = in.readDouble();
        humidity = in.readDouble();
        weatherBasic = in.readString();
        weatherDetail = in.readString();
        weatherDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(temperatureMax);
        parcel.writeDouble(temperatureMin);
        parcel.writeDouble(pressure);
        parcel.writeDouble(humidity);
        parcel.writeString(weatherBasic);
        parcel.writeString(weatherDetail);
        parcel.writeString(weatherDate);
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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
}
