package makdroid.voxpopuli.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Grzecho on 29.04.2016.
 */
public class FragmentUtils {

    public static void setFragment(@NonNull AppCompatActivity activity, @NonNull Fragment fragment,
                                   @NonNull int containerView, @NonNull String fragmentTag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerView, fragment, fragmentTag).commit();
    }
}
