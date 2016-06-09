package makdroid.voxpopuli.ui.base;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Grzecho on 18.04.2016.
 * Original https://github.com/JakeWharton/u2020/tree/master/src/main/java/com/jakewharton/u2020/ui
 * https://github.com/rallat/EffectiveAndroid/blob/clean/app/src/main/java/com/israelferrer/effectiveandroid/ui/ScreenContainer.java
 */

/**
 * podstawowy kontener dla aktywnosci, ktore potrzebuja navigation menu
 */
public interface BaseContainer {

    void bindForActivity(AppCompatActivity activity);

    DrawerLayout getDrawerLayout();

    int getmCurrentMenuId();

    void setmCurrentMenuId(int mCurrentMenuId);
}
