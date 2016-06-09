package makdroid.voxpopuli.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.mvp.presenters.impl.PreferencePresenter;
import makdroid.voxpopuli.mvp.views.PreferenceView;

public class PreferenceFragment extends Fragment implements PreferenceView {

    public static final String FRAGMENT_TAG = "PREFS_FRAGMENT_TAG";

    public PreferenceFragment() {
    }

    @Inject
    PreferencePresenter preferencePresenter;

    @Bind(R.id.rbg_pref_sync)
    RadioGroup mRbgPrefSync;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_preference, container, false);
        ButterKnife.bind(this, rootView);
        setUpPresenter();
        setUpUI();
        return rootView;
    }

    @Override
    public void setRadioButtonCheckedById(int checkedId) {
        RadioButton b = (RadioButton) rootView.findViewById(checkedId);
        b.setChecked(true);
    }

    @Override
    public void onRadioButtonChanged(int checkedId) {
        preferencePresenter.saveSyncFrequency(checkedId);
    }

    @Override
    public void showToastMessageSavedSuccessfull() {
        Toast.makeText(getActivity(), R.string.pref_saved, Toast.LENGTH_SHORT).show();
    }

    private void initializeDependencyInjector() {
        VoxPopuliApplication voxPopuliApplication = (VoxPopuliApplication) getActivity().getApplication();
        voxPopuliApplication.getComponent().inject(this);
    }

    private void setUpPresenter() {
        preferencePresenter.setView(this);
        preferencePresenter.initialize();
    }

    private void setUpUI() {
        addRadioGroupListener();
    }

    private void addRadioGroupListener() {
        mRbgPrefSync.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onRadioButtonChanged(checkedId);
            }
        });
    }

}
