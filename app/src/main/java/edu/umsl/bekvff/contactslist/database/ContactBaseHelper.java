package edu.umsl.bekvff.contactslist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.umsl.bekvff.contactslist.database.ContactDbSchema.ContactTable;
/**
 * Created by Brian Koehler on 4/11/2016.
 */
public class ContactBaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "ContactBaseHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "contactBase.db";

    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
                ContactTable.Cols.UUID + ", " +
                ContactTable.Cols.FIRST + ", " +
                ContactTable.Cols.LAST + ", " +
                ContactTable.Cols.BUSINESS + ", " +
                ContactTable.Cols.EMAIL +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
