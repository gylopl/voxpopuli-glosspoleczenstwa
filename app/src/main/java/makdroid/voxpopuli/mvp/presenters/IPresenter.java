package makdroid.voxpopuli.mvp.presenters;

/**
 * Created by Grzecho on 06.06.2016.
 */
public interface IPresenter<T> {

    void setView(T view);

    void initialize();

}
