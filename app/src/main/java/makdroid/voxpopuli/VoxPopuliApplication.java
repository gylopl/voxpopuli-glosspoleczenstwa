package makdroid.voxpopuli;

import android.app.Application;

import makdroid.voxpopuli.dagger.components.DaggerRootComponent;
import makdroid.voxpopuli.dagger.components.RootComponent;
import makdroid.voxpopuli.dagger.modules.RootModule;

/**
 * Created by Grzecho on 10.05.2016.
 */
public class VoxPopuliApplication extends Application {
    private RootComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
//        LeakCanary.install(this);
    }

    private void initializeDependencyInjector() {
        component = DaggerRootComponent.builder()
                .rootModule(new RootModule(this))
                .build();
        component.inject(this);

    }

    public RootComponent getComponent() {
        return component;
    }
}
