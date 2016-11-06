package sparkboot;

import spark.Request;

import static org.javalite.common.Collections.list;
import static org.javalite.common.Collections.map;
import static spark.Spark.halt;
import static sparkboot.util.Util.toJson;

/**
 * Created by misha on 03.11.2016.
 */
public  abstract class AbstractController {

    public abstract void init();

    protected boolean blank(Request req, String... params) {
        return list(params)
                .stream()
                .allMatch((param) -> req.params(param) == null && req.queryParams(param) == null);
    }

    protected void isExists(Object o, String msg) {
        if (o == null) {
            halt(404, respond(404, msg));
        }
    }

    protected String respond(int status, String messageText) {
        return toJson(map("status", status, "message", messageText));
    }
}
