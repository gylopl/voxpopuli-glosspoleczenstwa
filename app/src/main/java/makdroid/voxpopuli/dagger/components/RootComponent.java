package makdroid.voxpopuli.dagger.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.dagger.modules.PrefsModule;
import makdroid.voxpopuli.dagger.modules.RootModule;
import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.ui.base.BaseContainerImpl;
import makdroid.voxpopuli.ui.fragments.PreferenceFragment;

/**
 * Created by Grzecho on 10.05.2016.
 */
@Singleton
@Component(modules = {RootModule.class, PrefsModule.class})
public interface RootComponent {
    void inject(VoxPopuliApplication application);

    void inject(BaseContainerImpl baseContainerImpl);

    void inject(PreferenceFragment preferenceFragment);

    VoxPopuliApplication getApplication();

    Context context();

    Executor executor();

    MainThread mainThread();

    DatabaseManager databaseManager();

    UserPrefMaster userPrefMaster();

//    SQLiteOpenHelper sQLiteOpenHelper();
//    SharedPreferences sharedPreferences();
}
