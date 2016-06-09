package makdroid.voxpopuli.mvp.views;

/**
 * Created by Grzecho on 06.06.2016.
 */
public interface PreferenceView {

    void setRadioButtonCheckedById(int checkedId);

    void onRadioButtonChanged(int checkedId);

    void showToastMessageSavedSuccessfull();
}
