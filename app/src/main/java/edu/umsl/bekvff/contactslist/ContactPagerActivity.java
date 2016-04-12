package edu.umsl.bekvff.contactslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Brian Koehler on 4/11/2016.
 */
public class ContactPagerActivity extends AppCompatActivity{
    private static final String EXTRA_CRIME_ID = "edu.umsl.bekvff.contactlist.contact_id";
    private ViewPager mViewPager;
    private List<Contact> mContacts;

    public static Intent newIntent(Context packageContext, UUID contactId) {
        Intent intent = new Intent(packageContext, ContactPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, contactId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_pager);

        UUID contactId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mContacts = ContactModel.get(this).getContacts();
        FragmentManager manager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                Contact contact = mContacts.get(position);
                return ContactFragment.newInstance(contact.getId());
            }

            @Override
            public int getCount() {
                return mContacts.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Contact contact = mContacts.get(position);
                if (contact.getFirstName() != null) {
                    setTitle(contact.getFirstName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < mContacts.size(); i++) {
            if (mContacts.get(i).getId().equals(contactId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
