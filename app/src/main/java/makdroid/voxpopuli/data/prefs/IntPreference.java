package makdroid.voxpopuli.data.prefs;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by Grzecho on 24.05.2016.
 */
public class IntPreference {
    private final SharedPreferences preferences;
    private final String key;
    private final int defaultValue;


    public IntPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
        this(preferences, key, 0);
    }

    @Inject
    public IntPreference(@NonNull SharedPreferences preferences, @NonNull String key,
                         int defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public int get() {
        return preferences.getInt(key, defaultValue);
    }

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void set(int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}