package makdroid.voxpopuli.interactors.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;

import makdroid.voxpopuli.data.database.DatabaseManager;
import makdroid.voxpopuli.data.database.QueryDbExecutor;
import makdroid.voxpopuli.entities.CardVoting;
import makdroid.voxpopuli.entities.dao.CardVotingDao;
import makdroid.voxpopuli.executor.Executor;
import makdroid.voxpopuli.executor.MainThread;
import makdroid.voxpopuli.interactors.IGetCardVoting;
import makdroid.voxpopuli.interactors.Interactor;

/**
 * Created by Grzecho on 20.04.2016.
 */
public class GetCardVotingInteractor implements IGetCardVoting, Interactor {

    private final Executor executor;
    private MainThread mainThread;
    private IGetCardVoting.Callback callback;
    private Context context;
    private DatabaseManager databaseManager;

    public GetCardVotingInteractor(Context applicationContext, Executor executor, MainThread mainThread, DatabaseManager databaseManager) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.context = applicationContext;
        this.databaseManager = databaseManager;
    }

    @Override
    public void run() {
        waitToDoThisSampleMoreInteresting();
        databaseManager.executeQuery(new QueryDbExecutor() {
            @Override
            public void run(SQLiteDatabase database) {
                CardVotingDao dao = new CardVotingDao(database, context);
                nofityCardVotingCollectionLoaded(dao.selectAll());
            }
        });
    }

    private void waitToDoThisSampleMoreInteresting() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            //Empty
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

    private void nofityCardVotingCollectionLoaded(final Collection<CardVoting> cardVotingCollection) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onCardsLoaded(cardVotingCollection);
            }
        });
    }

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }

}
