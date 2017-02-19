package pom.pom.prom.prompompom.network.responses;

import com.google.gson.annotations.SerializedName;

import pom.pom.prom.prompompom.data.products.Catalog;

/**
 * Created by vararg on 18.02.2017.
 */

public class GetProductsResponse {

    @SerializedName("catalog")
    private Catalog catalog;

    public Catalog getCatalog() {
        return catalog;
    }

}
