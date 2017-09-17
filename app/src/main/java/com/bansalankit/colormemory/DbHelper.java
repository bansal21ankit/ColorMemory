package com.bansalankit.colormemory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to operate on score database.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>28 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ColorMemory.db";
    private static final int DB_VERSION = 1;

    private static final String ORDER_DESC = " DESC";

    private static final String TABLE_SCORE = "Scores";
    private static final String COLUMN_ID = "_ID";
    private static final String COLUMN_PLAYER = "player";
    private static final String COLUMN_SCORE = "score";

    private static final String CREATE_SCORE = "create table " + TABLE_SCORE + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PLAYER + " text not null, " + COLUMN_SCORE + " integer" + ");";
    private static final String DROP_SCORE = "drop table if exists " + TABLE_SCORE;

    DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SCORE);
        onCreate(db);
    }

    long insetScore(String playerName, int score) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();

            ContentValues values = new ContentValues(1);
            values.put(COLUMN_PLAYER, playerName);
            values.put(COLUMN_SCORE, score);

            return database.insert(TABLE_SCORE, null, values);
        } catch (SQLiteException exception) {
            return -1;
        } finally {
            if (database != null) database.close();
        }
    }

    List<Score> getScores() {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = getReadableDatabase();
            List<Score> scores = new ArrayList<>();
            cursor = database.query(TABLE_SCORE, null, null, null, null, null, COLUMN_SCORE + ORDER_DESC);
            if (cursor != null && cursor.moveToFirst()) while (!cursor.isAfterLast()) {
                String playerName = cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER));
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                scores.add(new Score(id, playerName, score));
                cursor.moveToNext();
            }
            return scores;
        } catch (SQLiteException exception) {
            return null;
        } finally {
            if (cursor != null) cursor.close();
            if (database != null) database.close();
        }
    }
}