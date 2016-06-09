package makdroid.voxpopuli.dagger.modules;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import makdroid.voxpopuli.dagger.qualifiers.PrefUserAvatarDir;
import makdroid.voxpopuli.dagger.qualifiers.PrefUserIsLogged;
import makdroid.voxpopuli.dagger.qualifiers.PrefUserMail;
import makdroid.voxpopuli.dagger.qualifiers.PrefUserName;
import makdroid.voxpopuli.dagger.qualifiers.PrefUserSyncFrequency;
import makdroid.voxpopuli.data.prefs.BooleanPreference;
import makdroid.voxpopuli.data.prefs.IntPreference;
import makdroid.voxpopuli.data.prefs.StringPreference;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.utils.Constants;

/**
 * Created by Grzecho on 24.05.2016.
 */
@Module(includes = RootModule.class)
public class PrefsModule {

    @Provides
    @Singleton
    @PrefUserIsLogged
    BooleanPreference providePrefUserISLogged(SharedPreferences preferences) {
        return new BooleanPreference(preferences, Constants.PREF_USER_IS_LOGGED);
    }

    @Provides
    @Singleton
    @PrefUserName
    StringPreference providePrefUserName(SharedPreferences preferences) {
        return new StringPreference(preferences, Constants.PREF_USER_NAME);
    }

    @Provides
    @Singleton
    @PrefUserMail
    StringPreference providePrefUserMail(SharedPreferences preferences) {
        return new StringPreference(preferences, Constants.PREF_USER_MAIL);
    }

    @Provides
    @Singleton
    @PrefUserAvatarDir
    StringPreference providePrefUserAvatarDir(SharedPreferences preferences) {
        return new StringPreference(preferences, Constants.PREF_USER_AVATAR_DIR);
    }

    @Provides
    @Singleton
    @PrefUserSyncFrequency
    IntPreference providePrefUserSyncFrequency(SharedPreferences preferences) {
        return new IntPreference(preferences, Constants.PREF_USER_SYNC_FREQUENCY, 4);
    }


    @Provides
    @Singleton
    UserPrefMaster provideUserPrefMaster(@PrefUserIsLogged BooleanPreference userIsLogged,
                                         @PrefUserName StringPreference userName,
                                         @PrefUserMail StringPreference userMail,
                                         @PrefUserAvatarDir StringPreference userAvatarDir,
                                         @PrefUserSyncFrequency IntPreference userSyncFrequency) {
        return new UserPrefMaster(userIsLogged, userName, userMail, userAvatarDir, userSyncFrequency);
    }
}
