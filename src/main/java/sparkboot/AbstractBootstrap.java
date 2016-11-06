package sparkboot;

import com.google.inject.Injector;

/**
 * Created by misha on 04.11.2016.
 */
public abstract class AbstractBootstrap {

    public abstract Injector getInjector();

    public abstract int getPort();

    public void onStartup() {}
}
