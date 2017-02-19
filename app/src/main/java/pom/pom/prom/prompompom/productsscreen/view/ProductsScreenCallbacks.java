package pom.pom.prom.prompompom.productsscreen.view;

import java.util.Collection;

import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;

/**
 * Created by vararg on 18.02.2017.
 */

public interface ProductsScreenCallbacks {

    void showProgress();

    void hideProgress();

    void showError();

    void onProductsReceived(Collection<ProductViewModel> products);

    void onNewProductsReceived(Collection<ProductViewModel> products);

}
