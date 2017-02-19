package pom.pom.prom.prompompom.productsscreen.router;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenRouterImpl implements ProductsScreenRouter {

    private Activity mainActivity;

    public ProductsScreenRouterImpl(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void navigateToFavorites() {
        Toast.makeText(mainActivity, "Navigated", Toast.LENGTH_SHORT).show();
    }
}
