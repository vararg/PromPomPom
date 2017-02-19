package pom.pom.prom.prompompom.productsscreen.domain;

import java.util.Collection;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import pom.pom.prom.prompompom.data.products.ProductService;
import pom.pom.prom.prompompom.network.responses.GetProductsResponse;

/**
 * Created by vararg on 18.02.2017.
 */

public class GetCatalogInteractor {

    private ProductService service;
    private ProductMapper mapper;

    private Scheduler subscribeScheduler;
    private Scheduler observeScheduler;
    private CompositeDisposable compositeDisposable;

    public GetCatalogInteractor(Scheduler subscribeOn, Scheduler observeOn, ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;

        subscribeScheduler = subscribeOn;
        observeScheduler = observeOn;

        compositeDisposable = new CompositeDisposable();
    }

    public void execute(Consumer<Collection<ProductViewModel>> onNextProducts,
                        Consumer<Collection<String>> onNextSorts, Consumer<Throwable> onError,
                        int amount, int offset, String sortType) {
        Flowable<GetProductsResponse> flowable = getFlowable(amount, offset, sortType);
        compositeDisposable.add(createProductsFlowable(flowable).subscribe(onNextProducts, onError));
        compositeDisposable.add(createSortsFlowable(flowable).subscribe(onNextSorts, onError));
    }

    protected Flowable<GetProductsResponse> createFlowable(int amount, int offset, String sortType) {
        return service.getCatalog(amount, offset, sortType);
    }

    protected Flowable<Collection<ProductViewModel>> createProductsFlowable(Flowable<GetProductsResponse> flowable) {
        return flowable.map(response -> mapper.map(response.getCatalog().getProducts()));
    }

    protected Flowable<Collection<String>> createSortsFlowable(Flowable<GetProductsResponse> flowable) {
        return flowable.map(response -> response.getCatalog().getPossibleSorts());
    }

    public final void unsubscribe() {
        // call clear() instead of unsubscribe() to be able to manage new subscriptions
        compositeDisposable.clear();
    }

    private Flowable<GetProductsResponse> getFlowable(int amount, int offset, String sortType) {
        return createFlowable(amount, offset, sortType)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
