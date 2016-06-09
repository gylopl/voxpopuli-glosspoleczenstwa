package makdroid.voxpopuli.data.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by grzegorz.makowski on 2014-06-21.
 */
@Singleton
public class DatabaseManager {

    private SQLiteOpenHelper sqliteDbHelper;
    private SQLiteDatabase sqliteDb;

    private AtomicInteger aiCounter = new AtomicInteger();

    @Inject
    public DatabaseManager(SQLiteOpenHelper helper) {
        sqliteDbHelper = helper;
    }

    private synchronized SQLiteDatabase openDatabase() {
        if (aiCounter.incrementAndGet() == 1) {
            sqliteDb = sqliteDbHelper.getWritableDatabase();
        }
        //L.d("Database open counter: " + mOpenCounter.get());
        return sqliteDb;
    }

    private synchronized void closeDatabase() {
        if (aiCounter.decrementAndGet() == 0) {
            sqliteDb.close();
        }
        // L.d("Database open counter: " + mOpenCounter.get());
    }


    public void executeQuery(QueryDbExecutor executor) {
        SQLiteDatabase dB = openDatabase();
        dB.beginTransaction();
        try {
            executor.run(dB);
            dB.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            dB.endTransaction();
        }

        closeDatabase();
    }


    //    private static DatabaseManager instance;
//    public void executeQueryTask(final QueryDbExecutor executor) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SQLiteDatabase dB = openDatabase();
//                executor.run(dB);
//                closeDatabase();
//            }
//        }).start();
//    }

//    public static synchronized DatabaseManager getInstance() {
//        if (instance == null) {
//            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
//                    " klasa niezainicjalizowana");
//        }
//        return instance;
//    }
//
//    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
//        if (instance == null) {
//            instance = new DatabaseManager(helper);
//        }
//    }
}
