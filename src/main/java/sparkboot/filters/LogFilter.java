package sparkboot.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sparkboot.Filter;

import static spark.Spark.before;
import static sparkboot.util.Util.isStaticResourceRequest;

/**
 * Created by misha on 05.11.2016.
 */
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter() {
        before((request, response) -> {
            if (!isStaticResourceRequest(request)) {
                logger.info("[" + request.requestMethod() + "] " + request.url() + ", params: " + request.queryMap().toString());
            }
        });
    }
}
