package com.example.legol.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Legol on 7/26/2016.
 */
public final class FeedReaderContract{
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns  {
        public static final String TABLE_NAME = "Account";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_EMAIL = "Email";
        public static final String COLUMN_NAME_PASSWORD = "Password";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;



        public class FeedReaderDbHelper extends SQLiteOpenHelper {



            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "FeedReader.db";

            public FeedReaderDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);

                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
                // Gets the data repository in write mode
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(FeedEntry.COLUMN_NAME_NAME, "Bob Cool");
                values.put(FeedEntry.COLUMN_NAME_EMAIL, "hey@gmail.com");
                values.put(FeedEntry.COLUMN_NAME_PASSWORD, "12345");

// Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(
                        FeedEntry.TABLE_NAME,
                        FeedEntry.COLUMN_NAME_NAME,
                        values);
            }
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_ENTRIES);
            }
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }
            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
            }



        }


    }


}
