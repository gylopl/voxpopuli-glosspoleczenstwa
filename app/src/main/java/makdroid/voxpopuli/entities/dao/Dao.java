package makdroid.voxpopuli.entities.dao;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Grzecho on 15.05.2016.
 */
public abstract class Dao<T> {

    abstract protected void insert(T t);

    abstract protected void insert(List<T> list);

    abstract protected List<T> selectAll();

    abstract protected List<T> manageCursor(Cursor cursor);

    protected void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    abstract protected T cursorToData(Cursor cursor);
}
