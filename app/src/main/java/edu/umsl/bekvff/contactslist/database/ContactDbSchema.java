package edu.umsl.bekvff.contactslist.database;

/**
 * Created by Brian Koehler on 4/11/2016.
 */
public class ContactDbSchema {
    public static final class ContactTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FIRST = "first";
            public static final String LAST = "last";
            public static final String BUSINESS = "business";
            public static final String EMAIL = "email";

        }
    }
}
