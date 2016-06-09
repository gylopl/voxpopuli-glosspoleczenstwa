package makdroid.voxpopuli.interactors.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import makdroid.voxpopuli.R;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.interactors.ISignInGoogle;
import makdroid.voxpopuli.interactors.Interactor;
import makdroid.voxpopuli.utils.FileUtil;
import makdroid.voxpopuli.utils.NetworkUtils;

/**
 * Created by Grzecho on 20.05.2016.
 */
public class SignInGoogleInteractor implements Interactor, ISignInGoogle {

    private Executor executor;
    private MainThread mainThread;
    private ISignInGoogle.Callback callback;
    private Context context;
    private UserPrefMaster userPrefMaster;
    private GoogleSignInAccount account;

    public SignInGoogleInteractor(Executor executor, MainThread mainThread, Context applicationContext
            , UserPrefMaster userPrefMaster) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.context = applicationContext;
        this.userPrefMaster = userPrefMaster;
    }

    @Override
    public void run() {
        // TODO: 20.05.2016 wydzielic do nowej metody
        if (!NetworkUtils.checkNetworkConnection(this.context)) {
            notifyError(this.context.getString(R.string.error), context.getString(R.string.error_no_internet));
        }
        if (account.getPhotoUrl() != null) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            URL url;
            try {
                url = new URL(account.getPhotoUrl().toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    notifyError(this.context.getString(R.string.error), this.context.getString(R.string.error_sign_in));
                }
                input = connection.getInputStream();
                File fileDir = FileUtil.getDiskCacheDir(this.context);
                File avatar = new File(fileDir, "avatar.jpg");
                output = new FileOutputStream(avatar, false);
                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                userPrefMaster.logInUser(account.getDisplayName(), account.getEmail(), avatar.getAbsolutePath());
                notifySignInSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                notifyError(this.context.getString(R.string.error), this.context.getString(R.string.error_sign_in));
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
        } else {
            userPrefMaster.logInUser(account.getDisplayName(), account.getEmail());
            notifySignInSuccessful();
        }
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor needs to get the response "
                            + "in the callback");
        }
        this.callback = callback;
        this.executor.run(this);
    }

    private void notifySignInSuccessful() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSignInGoogleSuccessful();
            }
        });
    }

    private void notifyError(final String title, final String error) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(title, error);
            }
        });
    }

    public void setAccount(@NonNull GoogleSignInAccount account) {
        this.account = account;
    }
}
