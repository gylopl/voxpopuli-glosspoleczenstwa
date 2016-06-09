package makdroid.voxpopuli.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.data.database.DatabaseHelper;
import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.executor.impl.GThreadExecutor;
import makdroid.voxpopuli.executor.impl.MainThreadUI;

/**
 * Created by Grzecho on 10.05.2016.
 */
@Module
public class RootModule {

    VoxPopuliApplication mApplication;

    public RootModule(VoxPopuliApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    VoxPopuliApplication providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    Executor provideGThreadExecutor(GThreadExecutor mGThreadExecutor) {
        return mGThreadExecutor;
    }

    @Provides
    @Singleton
    MainThread provideMainThreadUI(MainThreadUI mMainThreadUI) {
        return mMainThreadUI;
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideDatabaseHelper(DatabaseHelper databaseHelper) {
        return databaseHelper;
    }

    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(SQLiteOpenHelper databaseHelper) {
        return new DatabaseManager(databaseHelper);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(VoxPopuliApplication mApplication) {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }
}
