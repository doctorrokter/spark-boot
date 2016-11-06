package sparkboot.util;

import spark.Request;

import java.util.Arrays;

/**
 * Created by misha on 05.11.2016.
 */
public class Util {

    private static String[] ext = {".png", ".woff", ".js", ".css", ".ico"};

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
}
