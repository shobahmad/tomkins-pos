package com.erebor.tomkins.pos.viewmodel.base;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.erebor.tomkins.pos.tools.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class BaseTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    protected static Logger logger;

    public static void setUpLogger() {
        logger = new Logger() {
            @Override
            public void info(String tag, String message) {
                System.out.println("[info][" + tag + "] " + message);
            }

            @Override
            public void debug(String tag, String message) {
                System.out.println("[debug][" + tag + "] " + message);

            }

            @Override
            public void warning(String tag, String message) {
                System.out.println("[warning][" + tag + "] " + message);

            }

            @Override
            public void error(String tag, String message, Throwable throwable) {
                throwable.printStackTrace();

            }
        };
    }

    public static void setUpRxSchedulers() {
        final Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(command -> command.run(), true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpRxSchedulers();
        setUpLogger();
    }
}
