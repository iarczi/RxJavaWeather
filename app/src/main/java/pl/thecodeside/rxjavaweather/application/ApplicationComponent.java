package pl.thecodeside.rxjavaweather.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Artur Latoszewski on 06.12.2016.
 */
@Component(
        modules = ApplicationModule.class
)
@Singleton
public interface ApplicationComponent {
    Context getContext();

    Application getApplication();

    Resources getResources();

}
