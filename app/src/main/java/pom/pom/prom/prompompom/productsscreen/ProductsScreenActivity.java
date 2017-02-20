package pom.pom.prom.prompompom.productsscreen;

import android.os.Bundle;

import pom.pom.prom.prompompom.R;
import pom.pom.prom.prompompom.app.App;
import pom.pom.prom.prompompom.productsscreen.di.ProductsScreenComponent;
import pom.pom.prom.prompompom.productsscreen.di.ProductsScreenModule;
import pom.pom.prom.prompompom.productsscreen.presenter.ProductsScreenPresenter;
import pom.pom.prom.prompompom.productsscreen.router.ProductsScreenRouter;
import pom.pom.prom.prompompom.productsscreen.view.ProductsScreenView;
import pom.pom.prom.prompompom.widgets.ToolbarMenuActivity;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenActivity extends ToolbarMenuActivity {

    private ProductsScreenView screenView;
    private ProductsScreenModule screenModule;
    private ProductsScreenComponent screenComponent;
    private ProductsScreenPresenter presenter;
    private ProductsScreenRouter router;
    private ScopeHolder scopeHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_products);
        screenView = (ProductsScreenView) findViewById(R.id.screen_products);

        scopeHolder = (ScopeHolder) getLastCustomNonConfigurationInstance();
        if (scopeHolder == null) {
            screenModule = new ProductsScreenModule();
            screenComponent = ((App) getApplication()).getComponent().plus(screenModule);
            scopeHolder = new ScopeHolder(screenModule, screenComponent);
        } else {
            screenModule = scopeHolder.module;
            screenComponent = scopeHolder.component;
        }
        screenModule.setMainActivity(this);
        screenComponent.inject(screenView);

        presenter = screenComponent.mainPresenter();
        router = screenComponent.mainRouter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.takeView(screenView);
        presenter.takeRouter(router);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView();
        presenter.dropRouter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            screenModule.setMainActivity(null);
        }
    }

    @Override
    public ScopeHolder onRetainCustomNonConfigurationInstance() {
        return scopeHolder;
    }

    private static class ScopeHolder {
        final ProductsScreenModule module;
        final ProductsScreenComponent component;

        public ScopeHolder(ProductsScreenModule module, ProductsScreenComponent component) {
            this.module = module;
            this.component = component;
        }
    }
}
