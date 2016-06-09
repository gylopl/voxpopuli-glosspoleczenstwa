package makdroid.voxpopuli.entities.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.CardVoting;

/**
 * Created by Grzecho on 14.05.2016.
 */
public class CardVotingDao extends Dao<CardVoting> {
    interface Table {
        String COLUMN_ID = "id";
        String COLUMN_TITLE = "title";
        String COLUMN_CONTENT = "content";
        String COLUMN_ADD_DATE = "addDate";
        String COLUMN_END_DATE = "endDate";
    }

    private SQLiteDatabase dB;
    private Context ctx;

    public CardVotingDao(SQLiteDatabase database, Context context) {
        dB = database;
        ctx = context;
    }

    public static String getCreateTable(Context context) {
        return context.getString(R.string.create_table_cards);
    }

    public static String getDropTable(Context context) {
        return context.getString(R.string.drop_table_cards);
    }

    public void insert(CardVoting card) {
        Object[] args = {
                card.getTitle(),
                card.getContent(),
                card.getAddDate(),
                card.getEndDate()
        };
        dB.execSQL(ctx.getString(R.string.insert_card), args);
    }

    public void insert(List<CardVoting> cardVotingList) {

        for (CardVoting card : cardVotingList) {
            Object[] args = {
                    card.getTitle(),
                    card.getContent(),
                    card.getAddDate(),
                    card.getEndDate()
            };
            dB.execSQL(ctx.getString(R.string.insert_card), args);
        }
    }

    public List<CardVoting> selectAll() {
        Cursor cursor = dB.rawQuery(ctx.getString(R.string.select_all_cards), null);

        List<CardVoting> dataList = manageCursor(cursor);

        closeCursor(cursor);

        return dataList;
    }

    protected List<CardVoting> manageCursor(Cursor cursor) {
        List<CardVoting> dataList = new ArrayList<CardVoting>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CardVoting card = cursorToData(cursor);
                dataList.add(card);
                cursor.moveToNext();
            }
        }
        return dataList;
    }

    protected CardVoting cursorToData(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Table.COLUMN_ID);
        int titleIndex = cursor.getColumnIndex(Table.COLUMN_TITLE);
        int contentIndex = cursor.getColumnIndex(Table.COLUMN_CONTENT);
        int addDateIndex = cursor.getColumnIndex(Table.COLUMN_ADD_DATE);
        int endDateIndex = cursor.getColumnIndex(Table.COLUMN_END_DATE);

        return new CardVoting(cursor.getInt(idIndex), cursor.getString(titleIndex),
                cursor.getString(contentIndex), cursor.getLong(addDateIndex),
                cursor.getLong(endDateIndex));
    }

    public static void populateData(SQLiteDatabase db, Context context) {
        List<CardVoting> cardVotingCollection = new ArrayList<CardVoting>();
        cardVotingCollection.add(new CardVoting("Referendum nr1", (new Date(1462275352709L)).getTime(), (new Date(1492288952709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr2", (new Date(1462275352709L)).getTime(), (new Date(1492288952709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr3", (new Date(1462275352709L)).getTime(), (new Date(1492288952709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr4", (new Date(1462275352709L)).getTime(), (new Date(1492288952709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr5 end", (new Date(1462275352709L)).getTime(), (new Date(1462275352709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr6 end", (new Date(1462275352709L)).getTime(), (new Date(1462275352709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr7 end", (new Date(1462275352709L)).getTime(), (new Date(1462275352709L)).getTime()));
        cardVotingCollection.add(new CardVoting("Referendum nr8 end", (new Date(1462275352709L)).getTime(), (new Date(1462275352709L)).getTime()));

        for (CardVoting card : cardVotingCollection) {
            Object[] args = {
                    card.getTitle(),
                    card.getContent(),
                    card.getAddDate(),
                    card.getEndDate()
            };
            db.execSQL(context.getString(R.string.insert_card), args);
        }
        Log.d("SqLiteDataBase", "Wpisz dane do bazy...");
    }

}
