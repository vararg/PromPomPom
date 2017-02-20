package pom.pom.prom.prompompom.app;

import android.app.Application;

import pom.pom.prom.prompompom.app.di.AppComponent;
import pom.pom.prom.prompompom.app.di.AppModule;
import pom.pom.prom.prompompom.app.di.DaggerAppComponent;
import pom.pom.prom.prompompom.network.NetworkModule;

/**
 * Created by vararg on 18.02.2017.
 */

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("http://prom.ua"))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }

}
