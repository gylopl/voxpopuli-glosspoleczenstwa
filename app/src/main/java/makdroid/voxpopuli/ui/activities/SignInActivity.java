package makdroid.voxpopuli.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import makdroid.voxpopuli.R;
import makdroid.voxpopuli.VoxPopuliApplication;
import makdroid.voxpopuli.dagger.components.DaggerInterActorComponent;
import makdroid.voxpopuli.dagger.modules.InterActorModule;
import makdroid.voxpopuli.google.GooglePlusSignIn;
import makdroid.voxpopuli.mvp.presenters.impl.SignInPresenter;
import makdroid.voxpopuli.mvp.views.SignInView;
import makdroid.voxpopuli.utils.UiUtils;

public class SignInActivity extends AppCompatActivity implements
        GooglePlusSignIn.InfoLoginGoogleCallback, SignInView {

    @Inject
    SignInPresenter signInPresenter;
    GooglePlusSignIn googlePlusSignIn;

    @Bind(R.id.orzelImageView)
    ImageView mImageViewOrzel;
    @Bind(R.id.sign_in_button)
    SignInButton mSignInButton;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
        initUi();
        initializePresenter();
        googlePlusSignIn = new GooglePlusSignIn(this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googlePlusSignIn.resultGoogleLogin(requestCode, resultCode, data);
    }

    @Override
    public void getInfoLoginGoogle(GoogleSignInAccount account) {
        account.getDisplayName();
        signInPresenter.logInGoogle(account);

    }

    @Override
    public void connectionFailedApiClient(ConnectionResult connectionResult) {
        //// TODO: 01.06.2016
    }

    @Override
    public void loginFailed() {
        //// TODO: 01.06.2016
    }

    /*override mvp views*/
    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.answer_ok, null);
        builder.show();
    }

    @Override
    public void showMainActivity() {
        openMainActivity();
    }


    private void initializeDependencyInjector() {
        VoxPopuliApplication voxPopuliApplication = (VoxPopuliApplication) getApplication();
        DaggerInterActorComponent.builder().rootComponent(voxPopuliApplication.getComponent())
                .interActorModule(new InterActorModule()).build().inject(this);
    }

    private void initUi() {
        setContentView(R.layout.activity_sign_in_google_plus);
        ButterKnife.bind(this);
        UiUtils.setSizeOrzelImage(mImageViewOrzel, this);
        initProgressDialog();
        initSignInButton();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(R.string.pb_dialog_title_please_wait);
        mProgressDialog.setMessage(this.getString(R.string.pb_dialog_sign_in_wait));
    }

    private void initSignInButton() {
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        addSignInButtonOnClickListener();
    }

    private void addSignInButtonOnClickListener() {
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                googlePlusSignIn.signIn();
            }
        });
    }

    private void initializePresenter() {
        signInPresenter.setView(this);
        signInPresenter.initialize();
    }

    private void openMainActivity() {
        MainActivity.start(this);
        finish();
    }

}
