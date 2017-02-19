package pom.pom.prom.prompompom.data.products;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vararg on 18.02.2017.
 */

public class Product {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("price_currency")
    private String priceCurrency;

    @SerializedName("url_main_image_200x200")
    private String imageUrl;

    @SerializedName("discounted_price")
    private float priceDiscounted;

    @SerializedName("price")
    private float price;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getPriceDiscounted() {
        return priceDiscounted;
    }

    public float getPrice() {
        return price;
    }
}
