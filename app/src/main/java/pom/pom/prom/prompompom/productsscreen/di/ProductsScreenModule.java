package pom.pom.prom.prompompom.productsscreen.di;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import pom.pom.prom.prompompom.app.di.Job;
import pom.pom.prom.prompompom.app.di.Main;
import pom.pom.prom.prompompom.data.DataModule;
import pom.pom.prom.prompompom.data.products.ProductService;
import pom.pom.prom.prompompom.productsscreen.ProductsScreenActivity;
import pom.pom.prom.prompompom.productsscreen.domain.GetCatalogInteractor;
import pom.pom.prom.prompompom.productsscreen.domain.ProductMapper;
import pom.pom.prom.prompompom.productsscreen.presenter.ProductsScreenPresenter;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouter;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouterImpl;

/**
 * Created by vararg on 18.02.2017.
 */

@Module(includes = DataModule.class)
public class ProductsScreenModule {

    private WeakReference<ProductsScreenActivity> weakActivity;

    public void setMainActivity(ProductsScreenActivity activity) {
        this.weakActivity = new WeakReference<>(activity);
    }

    @Provides
    ProductsScreenRouter provideProductsScreenRouter() {
        ProductsScreenActivity mainActivity = weakActivity.get();
        if (mainActivity != null) return new ProductsScreenRouterImpl(mainActivity);
        else return null;
    }

    @Provides
    @ProductsScreenScope
    static GetCatalogInteractor provideGetCatalogInteractor(@Job Scheduler jobScheduler, @Main Scheduler mainScheduler,
                                                          ProductService service, ProductMapper mapper) {
        return new GetCatalogInteractor(jobScheduler, mainScheduler, service, mapper);
    }

    @Provides
    @ProductsScreenScope
    static ProductMapper provideProductMapper() {
        return new ProductMapper();
    }

    @Provides
    @ProductsScreenScope
    static ProductsScreenPresenter provideMainPresenter(GetCatalogInteractor getCatalogInteractor) {
        return new ProductsScreenPresenter(getCatalogInteractor);
    }
}
