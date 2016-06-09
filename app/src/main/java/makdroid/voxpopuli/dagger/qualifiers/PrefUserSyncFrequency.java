package makdroid.voxpopuli.dagger.qualifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Grzecho on 06.06.2016.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PrefUserSyncFrequency {
}
