package pl.thecodeside.rxjavaweather.data.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Artur Latoszewski on 02.12.2016.
 */

public class ForecastSourceModel {
    @SerializedName("list")
    public List<ForecastBody> weatherMainList;


    public ForecastSourceModel() {

    }

    public class ForecastBody {
        @SerializedName("main")
        public WeatherFullDetails fullDetails;

        @SerializedName("weather")
        @Expose
        public java.util.List<WeatherDetails> weatherDetailsList = null;

        @SerializedName("wind")
        @Expose
        public Wind wind;

        public class Wind {

            @SerializedName("speed")
            @Expose
            public Double speed;
        }
    }
}
