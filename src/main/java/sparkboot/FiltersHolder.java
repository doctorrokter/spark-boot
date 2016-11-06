package sparkboot;

/**
 * Created by misha on 03.11.2016.
 */
public abstract class FiltersHolder {

    private Filter[] filters;

    public abstract void init();

    public void addFilters(Filter... filters) {
        this.filters = filters;
    }

    protected Filter[] getFilters() {
        return filters;
    }
}
