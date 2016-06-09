package makdroid.voxpopuli.interactors.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.data.database.QueryDbExecutor;
import makdroid.voxpopuli.data.prefs.UserPrefMaster;
import makdroid.voxpopuli.entities.Vote;
import makdroid.voxpopuli.entities.VotingDetails;
import makdroid.voxpopuli.entities.dao.VoteDao;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.interactors.IGetCardVotingDetails;
import makdroid.voxpopuli.interactors.Interactor;
import makdroid.voxpopuli.utils.Constants;

/**
 * Created by Grzecho on 12.05.2016.
 */
public class GetCardVotingDetailsInteractor implements Interactor, IGetCardVotingDetails {

    private Context context;
    private Executor executor;
    private MainThread mainThread;
    private IGetCardVotingDetails.Callback callback;
    private DatabaseManager databaseManager;
    private UserPrefMaster userPrefMaster;
    private int mCardVotingId;

    public GetCardVotingDetailsInteractor(int cardVotingId, Context applicationContext, Executor executor,
                                          MainThread mainThread, DatabaseManager databaseManager,
                                          UserPrefMaster userPrefMaster) {
        this.mCardVotingId = cardVotingId;
        this.context = applicationContext;
        this.mainThread = mainThread;
        this.executor = executor;
        this.databaseManager = databaseManager;
        this.userPrefMaster = userPrefMaster;
    }

    @Override
    public void run() {
        databaseManager.executeQuery(new QueryDbExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                VoteDao dao = new VoteDao(database, context);
                List<Vote> votes = dao.selectAllByIdCardVoting(mCardVotingId);
                boolean userVoted = false;
                int userAnswer = 0;
                int yes = 0;
                int no = 0;
                for (Vote vote : votes) {
                    if (vote.getEmail().equalsIgnoreCase(userPrefMaster.getUserMail())) {
                        userVoted = true;
                        userAnswer = vote.getAnswer();
                    }
                    if (vote.getAnswer() == Constants.ANSWER_YES)
                        yes = yes + 1;
                    else
                        no = no + 1;
                }
                float yesPercent = (float) (yes * 100) / votes.size();
                float noPercent = (float) (no * 100) / votes.size();
                nofityVoteCollectionLoaded(VotingDetails.newInstance(yesPercent, noPercent, userVoted, userAnswer));
            }
        });
    }

    @Override
    public void execute(IGetCardVotingDetails.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor needs to get the response "
                            + "in the callback");
        }
        this.callback = callback;
        this.executor.run(this);
    }

    private void nofityVoteCollectionLoaded(final VotingDetails votingDetails) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onVotesCalculated(votingDetails);
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
}
