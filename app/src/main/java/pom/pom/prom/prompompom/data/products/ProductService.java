package pom.pom.prom.prompompom.data.products;

import android.util.Log;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pom.pom.prom.prompompom.network.responses.GetProductsResponse;
import pom.pom.prom.prompompom.network.retrofit.PromService;
import retrofit2.Retrofit;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductService {

    private PromService service;

    public ProductService(Retrofit retrofit) {
        service = retrofit.create(PromService.class);
    }

    public Flowable<GetProductsResponse> getCatalog(int amount) {
        return service.getProducts(amount, 0, 35402, "price",
                RequestBody.create(MediaType.parse("application/json"),
                        "[{:catalog [:possible_sorts {:results [:id :name :price_currency " +
                        ":discounted_price :price :url_main_image_200x200 ]}]}]"));

    }

}
