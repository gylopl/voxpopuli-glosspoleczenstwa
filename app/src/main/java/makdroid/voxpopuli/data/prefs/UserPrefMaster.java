package makdroid.voxpopuli.data.prefs;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Grzecho on 24.05.2016.
 */
@Singleton
public class UserPrefMaster {

    private final BooleanPreference userIsLogged;
    private final StringPreference userName;
    private final StringPreference userMail;
    private final StringPreference userAvatarDir;
    private final IntPreference userSyncFrequency;

    @Inject
    public UserPrefMaster(BooleanPreference userIsLogged,
                          StringPreference userName,
                          StringPreference userMail,
                          StringPreference userAvatarDir,
                          IntPreference userSyncFrequency) {
        this.userIsLogged = userIsLogged;
        this.userName = userName;
        this.userMail = userMail;
        this.userAvatarDir = userAvatarDir;
        this.userSyncFrequency = userSyncFrequency;
    }

    public void logInUser(String name, String mail) {
        this.userIsLogged.set(true);
        this.userName.set(name);
        this.userMail.set(mail);
    }

    public void logInUser(String name, String mail, String avatarDir) {
        this.userIsLogged.set(true);
        this.userName.set(name);
        this.userMail.set(mail);
        this.userAvatarDir.set(avatarDir);
    }

    public boolean isLoggged() {
        return userIsLogged.get();
    }

    public String getUserName() {
        return this.userName.get();
    }

    public String getUserAvatarDir() {
        return this.userAvatarDir.get();
    }

    public String getUserMail() {
        return this.userMail.get();
    }

    public int getUserSyncFrequency() {
        return this.userSyncFrequency.get();
    }

    public void setUserSyncFrequency(int value) {
        this.userSyncFrequency.set(value);
    }
}
