package makdroid.voxpopuli.executor.impl;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Grzecho on 21.04.2016.
 */
public class LoggingThreadFactory implements ThreadFactory
{
    private int counter = 0;

    @Override
    public Thread newThread(Runnable r)
    {
        String prefix = "out";
        Thread t = new Thread(r, prefix + "-" + counter++);

        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
//                LoggerFactory.getLogger(t.getName()).error(e.getMessage(), e);
            }
        });

        return t;
    }
}