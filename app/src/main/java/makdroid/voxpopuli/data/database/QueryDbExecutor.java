package makdroid.voxpopuli.data.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by grzegorz.makowski on 2014-06-22.
 */
public interface QueryDbExecutor {

    void run(SQLiteDatabase database);

}