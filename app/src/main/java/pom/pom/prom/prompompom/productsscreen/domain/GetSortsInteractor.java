package pom.pom.prom.prompompom.productsscreen.domain;

import java.util.Collection;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import pom.pom.prom.prompompom.data.sorts.DefaultSortsStorage;

/**
 * Created by vararg on 20.02.2017.
 */

public class GetSortsInteractor {

    private DefaultSortsStorage storage;

    private Scheduler subscribeScheduler;
    private Scheduler observeScheduler;
    private CompositeDisposable compositeDisposable;

    public GetSortsInteractor(Scheduler subscribeOn, Scheduler observeOn, DefaultSortsStorage storage) {
        this.storage = storage;

        subscribeScheduler = subscribeOn;
        observeScheduler = observeOn;

        compositeDisposable = new CompositeDisposable();
    }

    public void execute(Consumer<Collection<String>> onNext, Consumer<Throwable> onError) {
        compositeDisposable.add(getFlowable().subscribe(onNext, onError));
    }

    protected Flowable<Collection<String>> createFlowable() {
        return storage.getSorts();
    }

    public final void unsubscribe() {
        // call clear() instead of unsubscribe() to be able to manage new subscriptions
        compositeDisposable.clear();
    }

    private Flowable<Collection<String>> getFlowable() {
        return createFlowable()
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }
}
