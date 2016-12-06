package pl.thecodeside.rxjavaweather.forecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Forecast;

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

    public void populate(Forecast forecast, int position, Context context, boolean isMetric) {
        itemView.setTag(forecast);
        String date;

        if (position == 0) {
            date = "Today";
        } else if (position == 1) {
            date = "Tomorrow";
        } else {
            date = forecast.getWeatherDate();
        }

        weatherDate.setText(date);
        if (isMetric) {
            weatherMax.setText(context.getString(R.string.format_temperatue
                    , roundTemperature(forecast.getTemperatureMax()), context.getString(R.string.celsius_sign)));
            weatherMin.setText(context.getString(R.string.format_temperatue
                    , roundTemperature(forecast.getTemperatureMin()), context.getString(R.string.celsius_sign)));
        } else {
            weatherMax.setText(context.getString(R.string.format_temperatue
                    , roundTemperature(forecast.getTemperatureMax()), context.getString(R.string.fahrenheit_sign)));
            weatherMin.setText(context.getString(R.string.format_temperatue
                    , roundTemperature(forecast.getTemperatureMin()), context.getString(R.string.fahrenheit_sign)));
        }

        weatherDescription.setText(forecast.getWeatherDetail());

        Picasso.with(context).load(forecast.getWeatherUrl()).into(weatherImage);
    }

    private String roundTemperature(double temperature) {
        return Double.toString(Math.round(temperature));

    }
}
