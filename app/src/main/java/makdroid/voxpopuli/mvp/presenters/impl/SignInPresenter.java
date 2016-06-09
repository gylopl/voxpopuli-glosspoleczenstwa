package makdroid.voxpopuli.mvp.presenters.impl;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.interactors.ISignInGoogle;
import makdroid.voxpopuli.interactors.impl.SignInGoogleInteractor;
import makdroid.voxpopuli.mvp.presenters.IPresenter;
import makdroid.voxpopuli.mvp.views.SignInView;

/**
 * Created by Grzecho on 20.05.2016.
 */
public class SignInPresenter implements IPresenter<SignInView> {

    private SignInView view;
    private SignInGoogleInteractor signInGoogleInteractor;
    private UserPrefMaster userPrefMaster;

    @Inject
    public SignInPresenter(SignInGoogleInteractor signInGoogleInteractor, UserPrefMaster userPrefMaster) {
        this.signInGoogleInteractor = signInGoogleInteractor;
        this.userPrefMaster = userPrefMaster;
    }

    @Override
    public void setView(SignInView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        if (userPrefMaster.isLoggged()) {
            this.view.showMainActivity();
        }
    }

    public void logInGoogle(GoogleSignInAccount account) {
        checkViewAlreadySetted();
        loadProfileImage(account);
    }

    private void checkViewAlreadySetted() {
        if (this.view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    private void loadProfileImage(GoogleSignInAccount account) {
        this.view.showLoading();
        signInGoogleInteractor.setAccount(account);
        signInGoogleInteractor.execute(new ISignInGoogle.Callback() {
            @Override
            public void onSignInGoogleSuccessful() {
                startMainActivity();
            }

            @Override
            public void onError(String title, String error) {
                showErrorDialog(title, error);
            }
        });
    }

    private void startMainActivity() {
        this.view.hideLoading();
        this.view.showMainActivity();
    }

    private void showErrorDialog(String title, String error) {
        this.view.hideLoading();
        this.view.showDialog(title, error);
    }
}
