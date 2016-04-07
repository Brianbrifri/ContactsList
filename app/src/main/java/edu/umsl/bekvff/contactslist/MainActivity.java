package edu.umsl.bekvff.contactslist;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ContactListViewFragment();
    }
}
