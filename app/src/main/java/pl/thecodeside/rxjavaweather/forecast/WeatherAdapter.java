package pl.thecodeside.rxjavaweather.forecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Weather;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherVIewHolder> implements View.OnClickListener {

    private final int VIEW_TYPE_HEADER = 1;
    private final int VIEW_TYPE_BODY = 2;

    private List<Weather> weatherList;
    private LayoutInflater inflater;
    private Context context;
    private WeatherListener listener;
    private boolean isMetric;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        --position;
        if (position < weatherList.size()) {
            return VIEW_TYPE_BODY;
        }
        position -= weatherList.size();
        throw new IllegalArgumentException("We are at end of the list!");
    }

    public void setMetric(boolean metric) {
        isMetric = metric;
    }

    public WeatherAdapter(Context context, WeatherListener listener) {
        this.context = context;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
        weatherList = new ArrayList<>();
        isMetric = true;

    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList.clear();
        this.weatherList.addAll(weatherList);
        notifyDataSetChanged();
    }

    @Override
    public WeatherVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = inflater.inflate(R.layout.list_weather_today, parent, false);
        View bodyView = inflater.inflate(R.layout.list_weather_item, parent, false);
        if (viewType == VIEW_TYPE_HEADER) {
            headerView.setOnClickListener(this);
            return new WeatherVIewHolder(headerView);
        } else {
            bodyView.setOnClickListener(this);
            return new WeatherVIewHolder(bodyView);
        }
    }

    @Override
    public void onBindViewHolder(WeatherVIewHolder holder, int position) {
        holder.populate(weatherList.get(position), position, context, isMetric);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Weather) {
            listener.weatherClicked((Weather) view.getTag());
        }
    }

    public interface WeatherListener {
        void weatherClicked(Weather weather);
    }
}
