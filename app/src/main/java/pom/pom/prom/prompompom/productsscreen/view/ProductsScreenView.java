package pom.pom.prom.prompompom.productsscreen.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collection;

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

    private ProductScreenViewState currentState;

    private OnLoadMoreListener onLoadMoreListener;
    private OnSortChangeListener onSortChangeListener;
    private OnChangeViewStateListener onChangeViewStateListen;

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
        this.currentSort = currentSort;
    }

    @Override
    public void onViewStateChanged(ProductScreenViewState newState) {
        initList(newState, getContext());
    }

    @Inject
    void setPresenter(ProductsScreenPresenter presenter) {

        onChangeViewStateListen = presenter::changeViewState;

        binding.refreshLayout.setOnRefreshListener(
                () -> presenter.fetchData(DEFAULT_LIST_AMOUNT, currentSort));

        onSortChangeListener =
                sort -> presenter.fetchData(DEFAULT_LIST_AMOUNT, sort);

        onLoadMoreListener = offset ->
                presenter.fetchNewData(DEFAULT_LIST_AMOUNT, currentSort, offset);

        productsAdapter = new ProductsAdapter();
        sortsAdapter = new SortTypesAdapter(binding.getRoot().getContext());
        binding.recyclerView.setAdapter(productsAdapter);
    }

    private void initToolbar(ToolbarMenuActivity activity) {
        //TODO find better solution for init toolbar menu with viper
        activity.setOptionsMenuListener((menuInflater, menu) -> {
            menuInflater.inflate(R.menu.screen_products_toolbar_menu, menu);

            MenuItem itemSort = menu.findItem(R.id.action_sort);
            sortsSpinner = (Spinner) MenuItemCompat.getActionView(itemSort);

            sortsSpinner.setAdapter(sortsAdapter);

            //Set listener before setSelection method
            sortsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onSortChangeListener.onSortChanged(sortsAdapter.getItem(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Set selection after setOnItemSelectedListener method
            sortsSpinner.setSelection(sortsAdapter.getPosition(currentSort), false);

            MenuItem itemChangeState = menu.findItem(R.id.action_change_state);
            itemChangeState.setOnMenuItemClickListener(item -> {
                onChangeViewStateListen.onChangeViewState(currentState);
                return true;
            });

            return true;
        });

        activity.setSupportActionBar(binding.toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initList(ProductScreenViewState viewState, Context context) {
        RecyclerView.LayoutManager layoutManager;

        currentState = viewState;

        if (viewState == ProductScreenViewState.GRID) layoutManager = new GridLayoutManager(context, 2);
        else layoutManager = new LinearLayoutManager(context);

        binding.recyclerView.clearOnScrollListeners();

        binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                onLoadMoreListener.onLoadMore(totalItemsCount);
            }
        });
        binding.recyclerView.setLayoutManager(layoutManager);
        if (productsAdapter != null) {
            binding.recyclerView.setAdapter(productsAdapter);
        }
    }

    private interface OnLoadMoreListener {
        void onLoadMore(int offset);
    }

    private interface OnSortChangeListener {
        void onSortChanged(String sort);
    }

    private interface OnChangeViewStateListener {
        void onChangeViewState(ProductScreenViewState currentState);
    }
}
