package pom.pom.prom.prompompom.productsscreen.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pom.pom.prom.prompompom.databinding.ItemListProductBinding;
import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<ProductViewModel> products;

    public ProductsAdapter() {
        products = new ArrayList<>();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(ItemListProductBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(Collection<ProductViewModel> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public void addProducts(Collection<ProductViewModel> newProducts) {
        int start = getItemCount();
        this.products.addAll(newProducts);
        notifyItemRangeInserted(start, newProducts.size());
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private ItemListProductBinding binding;

        public ProductViewHolder(ItemListProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductViewModel model){
            binding.setModel(model);
        }

    }
}
