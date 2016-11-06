package sparkboot.spec;

import com.google.gson.Gson;
import com.google.inject.Injector;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import spark.Spark;
import sparkboot.Configurator;

import java.util.List;
import java.util.Map;

/**
 * Created by misha on 24.05.2016.
 */
public class SparkControllerSpec extends DBSpecHelper {

    private HttpBuilderResult result;

    @BeforeClass
    public static void startServer() {
        Spark.port(9999);
        Spark.before((req, resp) -> openDbConnection());
        Spark.after((req, resp) -> {
            Base.rollbackTransaction();
            if (Base.hasConnection()) {
                Base.close();
            }
        });
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }

    @Before
    public void setUp() {
        result = new HttpBuilderResult();
        Spark.awaitInitialization();
    }

    protected void setInjector(Injector injector) {
        Configurator.setInjector(injector);
    }

    protected HttpBuilder request() {
        return new HttpBuilder(result);
    }

    protected String responseContent() {
        return result.getResponseContent();
    }

    protected int responseCode() {
        return result.getResponseCode();
    }

    protected Object responseJson(Class clazz) {
        return new Gson().fromJson(responseContent(), clazz);
    }

    protected Map responseJsonMap() {
        return (Map) responseJson(Map.class);
    }

    protected List responseJsonList() {
        return (List) responseJson(List.class);
    }
}
