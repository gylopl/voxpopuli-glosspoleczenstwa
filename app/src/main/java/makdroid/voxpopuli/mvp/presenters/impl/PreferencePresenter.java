package makdroid.voxpopuli.mvp.presenters.impl;

import javax.inject.Inject;

import makdroid.voxpopuli.R;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.mvp.presenters.IPresenter;
import makdroid.voxpopuli.mvp.views.PreferenceView;

/**
 * Created by Grzecho on 06.06.2016.
 */
public class PreferencePresenter implements IPresenter<PreferenceView> {

    private PreferenceView view;
    private final UserPrefMaster userPrefMaster;
    private int mSyncValue;

    @Inject
    public PreferencePresenter(UserPrefMaster userPrefMaster) {
        this.userPrefMaster = userPrefMaster;
    }

    @Override
    public void setView(PreferenceView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        checkViewAlreadySetted();
        getSyncFrequency();
    }

    private void checkViewAlreadySetted() {
        if (this.view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    public void saveSyncFrequency(int checkedId) {
        if (checkedId == R.id.rb_pref_sync_frequency_4h)
            this.mSyncValue = 4;
        if (checkedId == R.id.rb_pref_sync_frequency_12h)
            this.mSyncValue = 12;
        if (checkedId == R.id.rb_pref_sync_frequency_24h)
            this.mSyncValue = 24;
        this.userPrefMaster.setUserSyncFrequency(mSyncValue);
        this.view.showToastMessageSavedSuccessfull();
    }

    public void getSyncFrequency() {
        this.mSyncValue = userPrefMaster.getUserSyncFrequency();
        int checkedId = R.id.rb_pref_sync_frequency_4h;
        if (this.mSyncValue == 4)
            checkedId = R.id.rb_pref_sync_frequency_4h;
        if (this.mSyncValue == 12)
            checkedId = R.id.rb_pref_sync_frequency_12h;
        if (this.mSyncValue == 24)
            checkedId = R.id.rb_pref_sync_frequency_24h;
        this.view.setRadioButtonCheckedById(checkedId);
    }
}
