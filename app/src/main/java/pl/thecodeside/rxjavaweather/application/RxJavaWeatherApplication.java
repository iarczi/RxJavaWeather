package pl.thecodeside.rxjavaweather.application;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

public class RxJavaWeatherApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    public static RxJavaWeatherApplication get(Context context) {
        return ((RxJavaWeatherApplication) context.getApplicationContext());
    }

}
