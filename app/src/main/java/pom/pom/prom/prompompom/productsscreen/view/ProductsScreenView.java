package pom.pom.prom.prompompom.productsscreen.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.widget.Toast;

import java.util.Collection;

import javax.inject.Inject;

import pom.pom.prom.prompompom.R;
import pom.pom.prom.prompompom.databinding.ScreenProductsBinding;
import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;
import pom.pom.prom.prompompom.productsscreen.presenter.ProductsScreenPresenter;
import pom.pom.prom.prompompom.widgets.EndlessRecyclerViewOnScrollListener;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenView extends ConstraintLayout implements ProductsScreenCallbacks {

    private ScreenProductsBinding binding;
    private ProductsAdapter adapter;
    private OnLoadMoreListener onLoadMoreListener;

    public ProductsScreenView(Context context) {
        this(context, null);
    }

    public ProductsScreenView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductsScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            binding = ScreenProductsBinding.bind(this);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    onLoadMoreListener.onLoadMore(totalItemsCount);
                }
            });
            binding.recyclerView.setLayoutManager(layoutManager);

        }
    }

    @Override
    public void showProgress() {
        binding.refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        binding.refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.request_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductsReceived(Collection<ProductViewModel> products) {
        adapter.setProducts(products);
    }

    @Override
    public void onNewProductsReceived(Collection<ProductViewModel> products) {
        adapter.addProducts(products);
    }

    @Inject
    void setPresenter(ProductsScreenPresenter presenter) {
        binding.refreshLayout.setOnRefreshListener(() -> presenter.fetchData(20));
        onLoadMoreListener = offset -> presenter.fetchNewData(20, offset);
        adapter = new ProductsAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    private interface OnLoadMoreListener {
        void onLoadMore(int offset);
    }
}
