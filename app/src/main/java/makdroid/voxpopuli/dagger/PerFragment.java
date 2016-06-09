package makdroid.voxpopuli.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Grzecho on 11.05.2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {}
