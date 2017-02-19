package pom.pom.prom.prompompom.data;

import dagger.Module;
import dagger.Provides;
import pom.pom.prom.prompompom.data.products.ProductService;
import retrofit2.Retrofit;

/**
 * Created by vararg on 18.02.2017.
 */

@Module
public class DataModule {

    @Provides
    ProductService provideProductService(Retrofit retrofit) {
        return new ProductService(retrofit);
    }
}
