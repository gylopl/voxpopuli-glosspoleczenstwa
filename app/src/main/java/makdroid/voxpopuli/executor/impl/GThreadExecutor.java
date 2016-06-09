package makdroid.voxpopuli.executor.impl;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.interactors.Interactor;

/**
 * Created by Grzecho on 21.04.2016.
 */
@Singleton
public class GThreadExecutor implements Executor {

    private ExecutorService executorService;

    @Inject
    public GThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor(new LoggingThreadFactory());
    }

    @Override
    public void run(final Interactor interactor) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                interactor.run();
            }
        });
    }


}
