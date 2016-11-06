package sparkboot.util;

import com.google.gson.Gson;
import org.javalite.activejdbc.Model;
import spark.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by misha on 05.11.2016.
 */
public class Util {

    private static String[] ext = {".png", ".woff", ".js", ".css", ".ico"};
    private static Gson gson = new Gson();

    public static boolean isStaticResourceRequest(Request request) {
        return Arrays.stream(ext).anyMatch(e -> request.url().contains(e));
    }

    public static String getEnv() {
        String env = System.getProperty("ACTIVE_ENV");
        if (env == null || env.isEmpty()) {
            return "development";
        } else {
            return env;
        }
    }

    public static String stringify(Map params) {
        final StringBuffer paramsStr = new StringBuffer();
        params.keySet().forEach((key) -> {
            paramsStr.append(key.toString());
            paramsStr.append("=");
            paramsStr.append(encode(params.get(key).toString()));
            paramsStr.append("&");
        });
        paramsStr.deleteCharAt(paramsStr.length() - 1);
        return paramsStr.toString();
    }

    public static String encode(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Model model) {
        return gson.toJson(model.toMap());
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static Map fromJson(String json) {
        return gson.fromJson(json, Map.class);
    }

    public static List fromJsonToList(String json) { return gson.fromJson(json, List.class); }
}
