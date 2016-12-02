package pl.thecodeside.rxjavaweather.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.thecodeside.rxjavaweather.R;
import pl.thecodeside.rxjavaweather.fragments.ForecastFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ForecastFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
