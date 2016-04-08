package edu.umsl.bekvff.contactslist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Brian Koehler on 4/6/2016.
 */
public class ContactModel {
    public static ContactModel sContactModel;
    private List<Contact> mContacts;

    public static ContactModel get(Context context) {
        if(sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    private ContactModel(Context context) {
        mContacts = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return mContacts;
    }

    public void setContacts(List<Contact> contacts) {
        mContacts = contacts;
    }

    public Contact getContact(UUID contactId) {
        for(Contact contact: mContacts) {
            if(contactId == contact.getId()) {
                return contact;
            }
        }
        return null;
    }
}
