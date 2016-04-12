package edu.umsl.bekvff.contactslist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private ContactListViewFragment mContactListViewFragment;

    public static Intent newIntent(Context packageContext, UUID contactId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager manager = getSupportFragmentManager();
        mContactListViewFragment = (ContactListViewFragment) manager.findFragmentById(R.id.fragment_container);

        if (mContactListViewFragment == null) {
            mContactListViewFragment = new ContactListViewFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, mContactListViewFragment)
                    .commit();
        }


    }
}
