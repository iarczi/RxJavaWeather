package pl.thecodeside.rxjavaweather.prototypes;

import rx.Scheduler;

/**
 * Created by Artur Latoszewski on 05.12.2016.
 */

public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();

}
