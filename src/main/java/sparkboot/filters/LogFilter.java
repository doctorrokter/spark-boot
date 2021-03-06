package sparkboot.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.QueryParamsMap;
import sparkboot.Filter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.javalite.common.Collections.map;
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
                logger.info("[" + request.requestMethod() + "] " + request.url() + ", params: " + queryMapToString(request.queryMap()));
            }
        });
    }

    private static String queryMapToString(QueryParamsMap queryParamsMap) {
        Map<String, String[]> paramsMap = queryParamsMap.toMap();
        return paramsMap.keySet()
                .stream()
                .map(key -> map(key, String.join(",", paramsMap.get(key))))
                .collect(Collectors.toList())
                .toString();
    }
}
