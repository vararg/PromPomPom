package pom.pom.prom.prompompom.productsscreen.presenter;

import java.util.Collection;

import pom.pom.prom.prompompom.productsscreen.domain.GetCatalogInteractor;
import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouter;
import pom.pom.prom.prompompom.productsscreen.view.ProductsScreenCallbacks;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenPresenter {

    private GetCatalogInteractor interactor;
    private Collection<ProductViewModel> cachedData;

    //TODO add router
    private ProductsScreenRouter router;

    private ProductsScreenCallbacks view;

    public ProductsScreenPresenter(GetCatalogInteractor interactor) {
        this.interactor = interactor;
    }

    public void dropView() {
        this.view = null;
    }

    protected void onDropView() {
        interactor.unsubscribe();
    }

    public void takeView(ProductsScreenCallbacks view) {
        this.view = view;
        onTakeView(view);
    }

    protected void onTakeView(ProductsScreenCallbacks view) {
        if (cachedData != null) {
            view.onProductsReceived(cachedData);
        }
    }

    public void takeRouter(ProductsScreenRouter router) {
        this.router = router;
        onTakeRouter(router);
    }

    public void dropRouter() {
        this.router = null;
        onDropRouter();
    }

    protected void onDropRouter() {

    }

    protected void onTakeRouter(ProductsScreenRouter router) {

    }

    public void fetchData(int count) {
        view.showProgress();
        interactor.execute(products ->
                {
                    cachedData = products;
                    view.hideProgress();
                    view.onProductsReceived(products);
                },
                sorts -> {
                },
                throwable -> {
                    view.hideProgress();
                    view.showError();
                }, count);
    }

    public void fetchNewData(int count, int offset) {
        view.showProgress();
        interactor.execute(products ->
                {
                    cachedData.addAll(products);
                    view.hideProgress();
                    view.onNewProductsReceived(products);
                },
                sorts -> {
                },
                throwable -> {
                    view.hideProgress();
                    view.showError();
                }, count);
    }
}
