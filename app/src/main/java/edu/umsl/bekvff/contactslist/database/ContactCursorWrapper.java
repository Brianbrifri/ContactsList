package edu.umsl.bekvff.contactslist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import edu.umsl.bekvff.contactslist.Contact;
import edu.umsl.bekvff.contactslist.database.ContactDbSchema.ContactTable;

/**
 * Created by Brian Koehler on 4/11/2016.
 */
public class ContactCursorWrapper extends CursorWrapper {

    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));
        String firstName = getString(getColumnIndex(ContactTable.Cols.FIRST));
        String lastName = getString(getColumnIndex(ContactTable.Cols.LAST));
        String business = getString(getColumnIndex(ContactTable.Cols.BUSINESS));
        String email = getString(getColumnIndex(ContactTable.Cols.EMAIL));

        Contact contact = new Contact(UUID.fromString(uuidString));
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setBusinessName(business);
        contact.setEmailAddress(email);

        return contact;
    }
}
