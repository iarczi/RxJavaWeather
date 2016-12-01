package pl.thecodeside.rxjavaweather.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Artur Latoszewski on 01.12.2016.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();

    Application application();
}