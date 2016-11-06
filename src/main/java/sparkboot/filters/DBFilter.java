package sparkboot.filters;

import org.javalite.activejdbc.Base;
import sparkboot.Filter;

import static spark.Spark.after;
import static spark.Spark.before;
import static sparkboot.util.Util.isStaticResourceRequest;

/**
 * Created by misha on 05.11.2016.
 */
public class DBFilter implements Filter {

    @Override
    public void doFilter() {
        before((request, response) -> {
            if (!isStaticResourceRequest(request)) {
                if (!Base.hasConnection()) {
                    Base.open();
                }
            }
        });
        after((request, response) -> {
            if (!isStaticResourceRequest(request)) {
                if (Base.hasConnection()) {
                    Base.close();
                }
            }
        });
    }
}
