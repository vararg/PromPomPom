package pom.pom.prom.prompompom.app.di;

import javax.inject.Singleton;

import dagger.Component;
import pom.pom.prom.prompompom.network.NetworkModule;
import pom.pom.prom.prompompom.productsscreen.di.ProductsScreenComponent;
import pom.pom.prom.prompompom.productsscreen.di.ProductsScreenModule;

/**
 * Created by vararg on 18.02.2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ProductsScreenComponent plus(ProductsScreenModule mainScreenModule);
}
