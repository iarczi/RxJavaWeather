package pl.thecodeside.rxjavaweather.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class WeatherVIewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.weatherDate)
    TextView weatherDate;
    @BindView(R.id.weatherMax)
    TextView weatherMax;
    @BindView(R.id.weatherMin)
    TextView weatherMin;
    @BindView(R.id.weatherDescription)
    TextView weatherDescription;
    @BindView(R.id.weatherImage)
    ImageView weatherImage;


    public WeatherVIewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Weather weather, int position) {
        itemView.setTag(weather);
        String date;

        if (position == 0) {
            date = "Today";
        } else if (position == 1) {
            date = "Tomorrow";
        } else {
            date = weather.getWeatherDate();
        }

        weatherDate.setText(date);
        weatherImage.setImageResource(R.mipmap.ic_launcher);
        weatherMax.setText(roundTemperature(weather.getTemperatureMax()));
        weatherMin.setText(roundTemperature(weather.getTemperatureMin()));

        weatherDescription.setText(weather.getWeatherDetail());
    }

    private String roundTemperature(double temperature) {
        return Double.toString(Math.round(temperature));

    }
}
