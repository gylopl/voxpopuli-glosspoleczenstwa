package makdroid.voxpopuli.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.interactors.impl.GetCardVotingDetailsInteractor;
import makdroid.voxpopuli.interactors.impl.GetCardVotingInteractor;
import makdroid.voxpopuli.interactors.impl.SendVoteInteractor;
import makdroid.voxpopuli.interactors.impl.SignInGoogleInteractor;

/**
 * Created by Grzecho on 11.05.2016.
 */
@Module
public class InterActorModule {

    private final int mCardVotingId;

    public InterActorModule() {
        mCardVotingId = -1;
    }

    public InterActorModule(int cardVotingId) {
        mCardVotingId = cardVotingId;
    }

    @Provides
    GetCardVotingInteractor providesGetCardVotingInteractor
            (Context applicationContext, Executor executor, MainThread mainThread,
             DatabaseManager databaseManager) {
        return new GetCardVotingInteractor(applicationContext, executor, mainThread, databaseManager);
    }

    @Provides
    GetCardVotingDetailsInteractor providesGetCardVotingDetailsInteractor
            (Context applicationContext, Executor executor, MainThread mainThread,
             DatabaseManager databaseManager, UserPrefMaster userPrefMaster) {
        return new GetCardVotingDetailsInteractor(mCardVotingId, applicationContext, executor,
                mainThread, databaseManager, userPrefMaster);
    }

    @Provides
    SendVoteInteractor providesSendVoteInteractor
            (Context applicationContext, Executor executor, MainThread mainThread,
             DatabaseManager databaseManager, UserPrefMaster userPrefMaster) {
        return new SendVoteInteractor(executor, mainThread, applicationContext, databaseManager, userPrefMaster, mCardVotingId);
    }

    @Provides
    SignInGoogleInteractor providesGetProfileImageInteractor
            (Executor executor, MainThread mainThread, Context applicationContext, UserPrefMaster userPrefMaster) {
        return new SignInGoogleInteractor(executor, mainThread, applicationContext, userPrefMaster);
    }
}
