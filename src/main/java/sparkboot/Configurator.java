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
    private static Injector injector;

    public static void setInjector(Injector inj) {
        injector = inj;
    }

    public static void configureControllers() {
        logger.info("Configure controllers...");
        Reflections reflections = new Reflections("app.controllers");
        reflections.getSubTypesOf(AbstractController.class)
                .stream()
                .forEach(c -> {
                    AbstractController controller = (AbstractController) createInstance(c);
                    if (injector != null) {
                        injector.injectMembers(controller);
                    }
                    controller.init();
                });
    }

    public static void configureFilters() {
        logger.info("Configure filters...");
        Reflections reflections = new Reflections("app.filters");
        reflections.getSubTypesOf(FiltersHolder.class)
                .forEach(fh -> {
                    FiltersHolder filtersHolder = (FiltersHolder) createInstance(fh);
                    if (injector != null) {
                        injector.injectMembers(filtersHolder);
                    }
                    filtersHolder.init();
                    asList(filtersHolder.getFilters()).forEach(f -> {
                        if (injector != null) {
                            injector.injectMembers(f);
                        }
                        f.doFilter();
                    });
                });
    }

    public static void configureSchedulers() {
        logger.info("Configure schedulers...");
        Reflections reflections = new Reflections("app.schedulers");
        reflections.getSubTypesOf(AbstractScheduler.class)
                .forEach(c -> {
                    AbstractScheduler scheduler = (AbstractScheduler) createInstance(c);
                    if (injector != null) {
                        injector.injectMembers(scheduler);
                    }
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
}
