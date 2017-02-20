package pom.pom.prom.prompompom.productsscreen.view;

/**
 * Created by vararg on 20.02.2017.
 */

public enum ProductScreenViewState {
    LINEAR,
    GRID;

    public ProductScreenViewState next() {
        switch (this) {
            case LINEAR:
                return GRID;
            case GRID:
                return LINEAR;
            // any other case return GRID
            default:
                return GRID;
        }
    }
}
