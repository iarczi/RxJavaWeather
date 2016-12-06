package pl.thecodeside.rxjavaweather.utils.schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.thecodeside.rxjavaweather.prototypes.BaseSchedulerProvider;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */
@Singleton
public class SchedulerProvider implements BaseSchedulerProvider {

    @Inject
    public SchedulerProvider() {
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
