package pom.pom.prom.prompompom.data.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vararg on 18.02.2017.
 */

public class Catalog {

    @SerializedName("possible_sorts")
    private List<String> possibleSorts;

    @SerializedName("results")
    private List<Product> products;

    public List<String> getPossibleSorts() {
        return possibleSorts;
    }

    public List<Product> getProducts() {
        return products;
    }
}
