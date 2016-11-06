package sparkboot;

import com.google.inject.Injector;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static spark.Spark.port;
import static sparkboot.util.Util.getEnv;

/**
 * Created by misha on 04.11.2016.
 */
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        logger.info("Environment: " + getEnv());

        Injector injector = null;
        AbstractBootstrap abstractBootstrap = null;

        Reflections reflections = new Reflections("app.bootstrap");
        Set<Class<? extends AbstractBootstrap>> bootstrap = reflections.getSubTypesOf(AbstractBootstrap.class);

        if (bootstrap.size() > 1) {
            throw new RuntimeException("Should be only one subclass of AbstractBootstrap");
        }

        if (bootstrap.size() != 0) {
            abstractBootstrap = (AbstractBootstrap) Configurator.createInstance(bootstrap.iterator().next());
            injector = abstractBootstrap.getInjector();
            port(abstractBootstrap.getPort());
            abstractBootstrap.onStartup();
            Configurator.setInjector(injector);
            Configurator.configureFilters();
            Configurator.configureControllers();
            Configurator.configureSchedulers();
        } else {
            throw new RuntimeException("You must provide a subclass of AbstractBootstrap");
        }
    }
}
