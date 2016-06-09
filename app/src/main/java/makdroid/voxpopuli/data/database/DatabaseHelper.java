package makdroid.voxpopuli.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import makdroid.voxpopuli.entities.dao.CardVotingDao;
import makdroid.voxpopuli.entities.dao.VoteDao;

/**
 * Created by grzegorz.makowski on 2014-06-22.
 */
@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "cards";
    private Context mContext;

    @Inject
    public DatabaseHelper(Context applicationContext) {
        super(applicationContext, DB_NAME, null, DB_VERSION);
        mContext = applicationContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CardVotingDao.getCreateTable(mContext));
        db.execSQL(VoteDao.getCreateTable(mContext));
        Log.d("SqLiteDataBase", "Tworzenie bazy...");
        CardVotingDao.populateData(db, mContext);
        VoteDao.populateData(db, mContext);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(CardVotingDao.getDropTable(mContext));
            db.execSQL(VoteDao.getDropTable(mContext));
            onCreate(db);
        }
    }
}
