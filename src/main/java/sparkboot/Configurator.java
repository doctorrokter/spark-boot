package sparkboot;

import com.google.inject.Injector;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

/**
 * Created by misha on 03.11.2016.
 */
public class Configurator {

    private static final Logger logger = LoggerFactory.getLogger(Configurator.class);

    protected static void configureControllers(Injector injector) {
        logger.info("Configure controllers...");
        Reflections reflections = new Reflections("app.controllers");
        reflections.getSubTypesOf(AbstractController.class)
                .stream()
                .forEach(c -> {
                    AbstractController controller = (AbstractController) createInstance(c);
                    configureInstance(c, injector);
                    controller.init();
                });
    }

    protected static void configureFilters(Injector injector) {
        logger.info("Configure filters...");
        Reflections reflections = new Reflections("app.filters");
        reflections.getSubTypesOf(FiltersHolder.class)
                .forEach(fh -> {
                    FiltersHolder filtersHolder = (FiltersHolder) createInstance(fh);
                    configureInstance(filtersHolder, injector);
                    filtersHolder.init();
                    asList(filtersHolder.getFilters()).forEach(f -> {
                        configureInstance(f, injector);
                        f.doFilter();
                    });
                });
    }

    protected static void configureSchedulers(Injector injector) {
        logger.info("Configure schedulers...");
        Reflections reflections = new Reflections("app.schedulers");
        reflections.getSubTypesOf(AbstractScheduler.class)
                .forEach(c -> {
                    AbstractScheduler scheduler = (AbstractScheduler) createInstance(c);
                    configureInstance(scheduler, injector);
                    scheduler.schedule();
                });
    }

    protected static Object createInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void configureInstance(Object o, Injector injector) {
        if (injector != null) {
            injector.injectMembers(o);
        }
    }
}
