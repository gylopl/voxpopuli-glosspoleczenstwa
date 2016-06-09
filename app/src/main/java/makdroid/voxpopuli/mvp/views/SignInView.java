package makdroid.voxpopuli.mvp.views;

/**
 * Created by Grzecho on 20.05.2016.
 */
public interface SignInView {
    void hideLoading();

    void showLoading();

    void showDialog(String title, String message);

    void showMainActivity();
}
