package makdroid.voxpopuli.interactors;

/**
 * Created by Grzecho on 20.05.2016.
 */
public interface ISignInGoogle {

    interface Callback {
        void onSignInGoogleSuccessful();

        void onError(String title, String error);
    }

    void execute(Callback callback);
}
