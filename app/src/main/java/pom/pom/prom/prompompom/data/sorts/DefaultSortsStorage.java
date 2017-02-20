package pom.pom.prom.prompompom.data.sorts;

import android.content.res.Resources;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.Flowable;
import pom.pom.prom.prompompom.R;

/**
 * Created by vararg on 20.02.2017.
 */

public class DefaultSortsStorage {

    private Resources resources;

    private List<String> defaultSorts;

    public DefaultSortsStorage(Resources resources) {
        this.resources = resources;

        defaultSorts = Arrays.asList(resources.getStringArray(R.array.default_sorts));
    }

    public Flowable<Collection<String>> getSorts() {
        return Flowable.just(defaultSorts);
    }
}
