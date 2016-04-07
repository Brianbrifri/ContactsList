package edu.umsl.bekvff.contactslist;

/**
 * Created by b-kizzle on 4/5/16.
 */
public class Contact {
    private String mFirstName;
    private String mLastName;
    private String mEmailAddress;
    private String mBusinessName;

    public Contact() {

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
}
