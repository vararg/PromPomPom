package pom.pom.prom.prompompom.productsscreen.presenter;

import android.text.TextUtils;
import android.util.Log;

import java.util.Collection;

import pom.pom.prom.prompompom.productsscreen.domain.GetCatalogInteractor;
import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouter;
import pom.pom.prom.prompompom.productsscreen.view.ProductScreenViewState;
import pom.pom.prom.prompompom.productsscreen.view.ProductsScreenCallbacks;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenPresenter {

    public static final String TAG = ProductsScreenPresenter.class.getSimpleName();

    private GetCatalogInteractor interactor;
    private Collection<ProductViewModel> cachedProducts;
    private Collection<String> cachedSorts;
    private String cachedSort;
    private ProductScreenViewState cachedViewState = ProductScreenViewState.GRID;

    //TODO add router
    private ProductsScreenRouter router;

    private ProductsScreenCallbacks view;

    public ProductsScreenPresenter(GetCatalogInteractor interactor) {
        this.interactor = interactor;
    }

    public void dropView() {
        this.view = null;
        onDropView();
    }

    protected void onDropView() {
        interactor.unsubscribe();
    }

    public void takeView(ProductsScreenCallbacks view) {
        this.view = view;
        onTakeView(view);
    }

    protected void onTakeView(ProductsScreenCallbacks view) {

        view.onViewStateChanged(cachedViewState);

        if (cachedProducts != null) {
            view.onProductsReceived(cachedProducts);
        }

        if (cachedSorts != null && !TextUtils.isEmpty(cachedSort) ) {
            view.onSortTypesReceived(cachedSorts, cachedSort);
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

    public void changeViewState(ProductScreenViewState currentState) {
        cachedViewState = getNextState(currentState);
        view.onViewStateChanged(cachedViewState);
    }

    public void fetchData(int count, String sortType) {
        view.showProgress();
        cachedSort = sortType;
        interactor.execute(products ->
                {
                    view.hideProgress();
                    cachedProducts = products;
                    view.onProductsReceived(products);
                },
                sorts -> {
                    cachedSorts = sorts;
                    view.onSortTypesReceived(sorts, sortType);
                },
                throwable -> {
                    view.hideProgress();
                    view.showError();
                    Log.e(TAG, "Error while downloading data", throwable);
                }, count, 0, sortType);
    }

    public void fetchNewData(int count, String sortType, int offset) {
        view.showProgress();
        cachedSort = sortType;
        interactor.execute(products ->
                {
                    view.hideProgress();
                    cachedProducts.addAll(products);
                    view.onNewProductsReceived(products);
                },
                sorts -> {
                    cachedSorts = sorts;
                    view.onSortTypesReceived(sorts, sortType);
                },
                throwable -> {
                    view.hideProgress();
                    view.showError();
                    Log.e(TAG, "Error while downloading data", throwable);
                }, count, offset, sortType);
    }

    private ProductScreenViewState getNextState(ProductScreenViewState viewState) {
        return viewState.next();
    }
}
