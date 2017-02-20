package pom.pom.prom.prompompom.data;

import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import pom.pom.prom.prompompom.data.products.ProductService;
import pom.pom.prom.prompompom.data.sorts.DefaultSortsStorage;
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

    @Provides
    DefaultSortsStorage provideDefaultSortsStorage(Resources resources) {
        return new DefaultSortsStorage(resources);
    }
}
