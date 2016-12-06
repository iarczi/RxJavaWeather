package pl.thecodeside.rxjavaweather.forecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.data.Forecast;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class ForecastAdapter extends RecyclerView.Adapter<WeatherVIewHolder> implements View.OnClickListener {

    private final int VIEW_TYPE_HEADER = 1;
    private final int VIEW_TYPE_BODY = 2;

    private List<Forecast> forecastList;
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
        if (position < forecastList.size()) {
            return VIEW_TYPE_BODY;
        }
        position -= forecastList.size();
        throw new IllegalArgumentException("We are at end of the list!");
    }

    public void setUnits(boolean metric) {
        isMetric = metric;
    }

    public ForecastAdapter(Context context, WeatherListener listener) {
        this.context = context;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
        forecastList = new ArrayList<>();
        isMetric = true;

    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList.clear();
        this.forecastList.addAll(forecastList);
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
        holder.populate(forecastList.get(position), position, context, isMetric);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Forecast) {
            listener.weatherClicked((Forecast) view.getTag());
        }
    }

    public interface WeatherListener {
        void weatherClicked(Forecast forecast);
    }
}
