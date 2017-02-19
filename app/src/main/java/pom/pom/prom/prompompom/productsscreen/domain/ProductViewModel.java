package pom.pom.prom.prompompom.productsscreen.domain;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductViewModel {

    private long id;
    private String name;
    private float price;
    private float priceDiscounted;
    private String priceCurrency;
    private String imageUrl;

    public ProductViewModel(long id, String name, float price, float priceDiscounted,
                            String priceCurrency, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceDiscounted = priceDiscounted;
        this.priceCurrency = priceCurrency;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getPriceDiscounted() {
        return priceDiscounted;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductViewModel that = (ProductViewModel) o;

        if (id != that.id) return false;
        if (Float.compare(that.price, price) != 0) return false;
        if (Float.compare(that.priceDiscounted, priceDiscounted) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (priceCurrency != null ? !priceCurrency.equals(that.priceCurrency) : that.priceCurrency != null)
            return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (priceDiscounted != +0.0f ? Float.floatToIntBits(priceDiscounted) : 0);
        result = 31 * result + (priceCurrency != null ? priceCurrency.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductViewModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", priceDiscounted=" + priceDiscounted +
                ", priceCurrency='" + priceCurrency + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
