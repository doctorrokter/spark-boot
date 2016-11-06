package sparkboot.spec;

import org.javalite.http.Http;
import org.javalite.http.Post;
import org.javalite.http.Request;
import sparkboot.util.Util;

import java.util.HashMap;
import java.util.Map;

import static sparkboot.spec.HttpBuilder.Method.*;

/**
 * Created by misha on 24.05.2016.
 */
public class HttpBuilder {

    private Map<String, Object> params = new HashMap<>();
    private int id = 0;
    private HttpBuilderResult result;

    public HttpBuilder(HttpBuilderResult result) {
        this.result = result;
    }

    public HttpBuilder param(String param, Object value) {
        params.put(param, value);
        return this;
    }

    public HttpBuilder params(Object...paramsValues) {
        String key = null;
        for (int i = 0; i < paramsValues.length; i++) {
            if (i == 0 || (i % 2 == 0)) {
                key = paramsValues[i].toString();
            } else {
                params.put(key, paramsValues[i]);
            }
        }
        return this;
    }

    public HttpBuilder id(int id) {
        this.id = id;
        return this;
    }

    public void get(String action) {
        build(GET, action);
    }

    public void post(String action) {
        build(POST, action);
    }

    public void put(String action) {
        build(PUT, action);
    }

    public void delete(String action) {
        build(DELETE, action);
    }

    private void build(Method method, String action) {
        String url = "http://localhost:9999";
        if (action != null && !action.isEmpty()) {
            url += "/" + action;
        }
        if (id != 0) {
            url += "/" + id;
        }
        Request req = method.request(url, params);
        result.setResponseContent(req.text());
        result.setResponseCode(req.responseCode());
    }

    public enum Method {
        GET {
            @Override
            public Request request(String subUrl, Map<String, Object> params) {
                return Http.get(getUrl(subUrl, params));
            }
        },
        POST {
            @Override
            public Request request(String subUrl, Map<String, Object> params) {
                Post post = Http.post(subUrl);
                params.keySet().forEach(key -> post.param(key, params.get(key).toString()));
                return post;
            }
        },
        PUT {
            @Override
            public Request request(String subUrl, Map<String, Object> params) {
                return Http.put(getUrl(subUrl, params), "");
            }
        },
        DELETE {
            @Override
            public Request request(String subUrl, Map<String, Object> params) {
                return Http.delete(getUrl(subUrl, params));
            }
        };

        public abstract Request request(String subUrl, Map<String, Object> params);

        private static String getUrl(String subUrl, Map<String, Object> params) {
            String url = subUrl;
            if (!params.isEmpty()) {
                url += "?" + Util.stringify(params);
            }
            return url;
        }
    }
}
