package edu.umsl.bekvff.contactslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.umsl.bekvff.contactslist.database.ContactBaseHelper;
import edu.umsl.bekvff.contactslist.database.ContactCursorWrapper;
import edu.umsl.bekvff.contactslist.database.ContactDbSchema.ContactTable;

/**
 * Created by Brian Koehler on 4/6/2016.
 */
public class ContactModel {
    private static ContactModel sContactModel;
    private List<Contact> mContacts;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ContactModel get(Context context) {
        if(sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    private ContactModel(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ContactBaseHelper(mContext).getWritableDatabase();
    }

    private static ContentValues getContentValues(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.UUID, contact.getId().toString());
        values.put(ContactTable.Cols.FIRST, contact.getFirstName());
        values.put(ContactTable.Cols.LAST, contact.getLastName());
        values.put(ContactTable.Cols.BUSINESS, contact.getBusinessName());
        values.put(ContactTable.Cols.EMAIL, contact.getEmailAddress());
        return values;
    }

    public void updateContact(Contact contact) {
        String uuidString = contact.getId().toString();
        ContentValues values = getContentValues(contact);

        mDatabase.update(ContactTable.NAME, values, ContactTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ContactTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ContactCursorWrapper(cursor);
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        ContactCursorWrapper cursor = queryContacts(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contacts.add(cursor.getContact());
            cursor.moveToNext();
        }
        cursor.close();


        return contacts;
    }

    public void addContact(Contact contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactTable.NAME, null, values);
    }


    public Contact getContact(UUID contactId) {
        ContactCursorWrapper cursor = queryContacts(
                ContactTable.Cols.UUID + " = ?",
                new String[] {contactId.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getContact();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Contact contact) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, contact.getPhotoFilename());
    }
}
