package pom.pom.prom.prompompom.productsscreen.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.inject.Inject;

import pom.pom.prom.prompompom.R;
import pom.pom.prom.prompompom.databinding.ScreenProductsBinding;
import pom.pom.prom.prompompom.productsscreen.domain.ProductViewModel;
import pom.pom.prom.prompompom.productsscreen.presenter.ProductsScreenPresenter;
import pom.pom.prom.prompompom.widgets.EndlessRecyclerViewOnScrollListener;
import pom.pom.prom.prompompom.widgets.ToolbarMenuActivity;

/**
 * Created by vararg on 18.02.2017.
 */

public class ProductsScreenView extends ConstraintLayout implements ProductsScreenCallbacks {

    private static final int DEFAULT_LIST_AMOUNT = 20;

    private ScreenProductsBinding binding;

    private ProductsAdapter productsAdapter;

    private Spinner sortsSpinner;
    private SortTypesAdapter sortsAdapter;
    private String currentSort;

    private OnLoadMoreListener onLoadMoreListener;
    private OnSortChangeListener onSortChangeListener;

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
            ToolbarMenuActivity activity = (ToolbarMenuActivity) getContext();

            binding = ScreenProductsBinding.bind(this);
            GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
            binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    onLoadMoreListener.onLoadMore(totalItemsCount);
                }
            });
            binding.recyclerView.setLayoutManager(layoutManager);

            initToolbar(activity);
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
        productsAdapter.setProducts(products);
    }

    @Override
    public void onNewProductsReceived(Collection<ProductViewModel> products) {
        productsAdapter.addProducts(products);
    }

    @Override
    public void onSortTypesReceived(Collection<String> sorts, String currentSort) {
        sortsAdapter.mergeAll(sorts);

        if (sortsSpinner == null) {
            this.currentSort = currentSort;
        }
    }

    @Inject
    void setPresenter(ProductsScreenPresenter presenter) {
        binding.refreshLayout.setOnRefreshListener(
                () -> presenter.fetchData(DEFAULT_LIST_AMOUNT, sortsSpinner.getSelectedItem().toString()));

        onSortChangeListener =
                sort -> presenter.fetchData(DEFAULT_LIST_AMOUNT, sort);

        onLoadMoreListener =
                offset -> presenter.fetchNewData(DEFAULT_LIST_AMOUNT, sortsSpinner.getSelectedItem().toString(), offset);

        productsAdapter = new ProductsAdapter();
        sortsAdapter = new SortTypesAdapter(binding.getRoot().getContext(),
                Arrays.asList(binding.getRoot().getContext().getResources().getStringArray(R.array.default_sorts)));
        binding.recyclerView.setAdapter(productsAdapter);
    }

    private void initToolbar(ToolbarMenuActivity activity) {

        activity.setOptionsMenuListener((menuInflater, menu) -> {
            menuInflater.inflate(R.menu.screen_products_toolbar_menu, menu);

            MenuItem item = menu.findItem(R.id.action_sort);
            sortsSpinner = (Spinner) MenuItemCompat.getActionView(item);

            sortsSpinner.setAdapter(sortsAdapter);

            if (!TextUtils.isEmpty(currentSort)) {
                sortsSpinner.setSelection(sortsAdapter.getPosition(currentSort), false);
                currentSort = null;
            }

            //Set listener after setSelection method
            sortsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSortChangeListener.onSortChanged(sortsAdapter.getItem(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            return true;
        });

        activity.setSupportActionBar(binding.toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private interface OnLoadMoreListener {
        void onLoadMore(int offset);
    }

    private interface OnSortChangeListener {
        void onSortChanged(String sort);
    }
}
