package makdroid.voxpopuli.interactors.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.data.database.QueryDbExecutor;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.entities.Vote;
import makdroid.voxpopuli.entities.dao.VoteDao;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.interactors.ISendVote;
import makdroid.voxpopuli.interactors.Interactor;

/**
 * Created by Grzecho on 26.05.2016.
 */
public class SendVoteInteractor implements Interactor, ISendVote {

    private Executor executor;
    private MainThread mainThread;
    private ISendVote.Callback callback;
    private Context context;
    private DatabaseManager databaseManager;
    private UserPrefMaster userPrefMaster;
    private int mCardVotingId;
    private int mAnswer;

    public SendVoteInteractor(Executor executor, MainThread mainThread, Context context,
                              DatabaseManager databaseManager, UserPrefMaster userPrefMaster, int cardVotingId) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.context = context;
        this.databaseManager = databaseManager;
        this.userPrefMaster = userPrefMaster;
        this.mCardVotingId = cardVotingId;
    }

    @Override
    public void run() {
        databaseManager.executeQuery(new QueryDbExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                VoteDao dao = new VoteDao(database, context);
                Vote vote = new Vote(mCardVotingId, userPrefMaster.getUserMail(), mAnswer);
                dao.insert(vote);
                notifySeendVoteSuccessful();
            }
        });
    }

    @Override
    public void execute(ISendVote.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor needs to get the response "
                            + "in the callback");
        }
        this.callback = callback;
        this.executor.run(this);
    }


    private void notifySeendVoteSuccessful() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSendVoteSuccessful();
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

    public void setmAnswer(@NonNull int mAnswer) {
        this.mAnswer = mAnswer;
    }
}
