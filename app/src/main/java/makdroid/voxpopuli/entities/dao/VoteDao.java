package makdroid.voxpopuli.entities.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import makdroid.voxpopuli.R;
import makdroid.voxpopuli.entities.Vote;

/**
 * Created by Grzecho on 15.05.2016.
 */
public class VoteDao extends Dao<Vote> {

    interface Table {
        String COLUMN_ID = "id";
        String COLUMN_ID_CARD_VOTING = "idCardVoting";
        String COLUMN_EMAIL = "email";
        String COLUMN_ANSWER = "answer";
    }

    private SQLiteDatabase dB;
    private Context ctx;

    public VoteDao(SQLiteDatabase database, Context context) {
        dB = database;
        ctx = context;
    }

    public static String getCreateTable(Context context) {
        return context.getString(R.string.create_table_votes);
    }

    public static String getDropTable(Context context) {
        return context.getString(R.string.drop_table_votes);
    }

    public void insert(Vote vote) {
        Object[] args = {
                vote.getCardVotingId(),
                vote.getEmail(),
                vote.getAnswer()
        };
        dB.execSQL(ctx.getString(R.string.insert_vote), args);
    }

    public void insert(List<Vote> voteList) {

        for (Vote vote : voteList) {
            Object[] args = {
                    vote.getCardVotingId(),
                    vote.getEmail(),
                    vote.getAnswer()
            };
            dB.execSQL(ctx.getString(R.string.insert_vote), args);
        }
    }

    public List<Vote> selectAll() {
        Cursor cursor = dB.rawQuery(ctx.getString(R.string.select_all_votes), null);

        List<Vote> dataList = manageCursor(cursor);

        closeCursor(cursor);

        return dataList;
    }

    public List<Vote> selectAllByIdCardVoting(int idCardVoting) {
        String[] args = {
                String.valueOf(idCardVoting)
        };
        Cursor cursor = dB.rawQuery(ctx.getString(R.string.select_votes_by_card_voting_id), args);

        List<Vote> dataList = manageCursor(cursor);

        closeCursor(cursor);

        return dataList;
    }

    public List<Vote> manageCursor(Cursor cursor) {
        List<Vote> dataList = new ArrayList<Vote>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Vote vote = cursorToData(cursor);
                dataList.add(vote);
                cursor.moveToNext();
            }
        }
        return dataList;
    }

    protected Vote cursorToData(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Table.COLUMN_ID);
        int idCardVotingIndex = cursor.getColumnIndex(Table.COLUMN_ID_CARD_VOTING);
        int emailIndex = cursor.getColumnIndex(Table.COLUMN_EMAIL);
        int answerIndex = cursor.getColumnIndex(Table.COLUMN_ANSWER);

        return new Vote(cursor.getInt(idIndex), cursor.getInt(idCardVotingIndex),
                cursor.getString(emailIndex), cursor.getInt(answerIndex));
    }

    public static void populateData(SQLiteDatabase db, Context context) {
        List<Vote> votesCollection = new ArrayList<Vote>();
        Random rand = new Random();

        // random 0 to 1
        for (int j = 1; j < 9; j++) {
            for (int i = 0; i < 500; i++) {
                int randomNum = rand.nextInt((1 - 0) + 1) + 0;

                votesCollection.add(new Vote(j, "gylopl@wp.pl", randomNum));
            }
        }
        for (Vote vote : votesCollection) {
            Object[] args = {
                    vote.getCardVotingId(),
                    vote.getEmail(),
                    vote.getAnswer()
            };
            db.execSQL(context.getString(R.string.insert_vote), args);
        }
        Log.d("SqLiteDataBase", "Wpisz dane do bazy...");
    }
}
