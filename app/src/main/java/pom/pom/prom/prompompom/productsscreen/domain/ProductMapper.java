package pom.pom.prom.prompompom.productsscreen.domain;

import java.util.ArrayList;
import java.util.Collection;

import pom.pom.prom.prompompom.data.products.Product;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductMapper {

    public Collection<ProductViewModel> map(Collection<Product> in) {
        Collection<ProductViewModel> out = new ArrayList(in.size());

        for (Product product : in) {
            out.add(new ProductViewModel(product.getId(), product.getName(),
                    product.getPrice(), product.getPriceDiscounted(), product.getPriceCurrency(),
                    product.getImageUrl()));
        }

        return out;
    }
}
