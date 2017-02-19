package pom.pom.prom.prompompom.productsscreen.di;

import dagger.Subcomponent;
import pom.pom.prom.prompompom.productsscreen.presenter.ProductsScreenPresenter;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouter;
import pom.pom.prom.prompompom.productsscreen.view.ProductsScreenView;

/**
 * Created by vararg on 18.02.2017.
 */

@ProductsScreenScope
@Subcomponent(modules = ProductsScreenModule.class)
public interface ProductsScreenComponent {

    void inject(ProductsScreenView view);

    ProductsScreenRouter mainRouter();

    ProductsScreenPresenter mainPresenter();

}
