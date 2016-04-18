package edu.umsl.bekvff.contactslist;

import java.util.UUID;

/**
 * Created by b-kizzle on 4/5/16.
 */
//Simple contact class object
public class Contact {
    private UUID mId;
    private String mFirstName;
    private String mLastName;
    private String mEmailAddress;
    private String mBusinessName;

    public Contact() {
        this(UUID.randomUUID());
    }

    public Contact(UUID id) {
        mId = id;
    }


    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public String getBusinessName() {
        return mBusinessName;
    }

    public void setBusinessName(String businessName) {
        mBusinessName = businessName;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
