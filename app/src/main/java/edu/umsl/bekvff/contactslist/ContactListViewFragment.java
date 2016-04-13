package edu.umsl.bekvff.contactslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by b-kizzle on 4/5/16.
 */
public class ContactListViewFragment extends Fragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mContactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_recycler_view, container, false);
        mContactRecyclerView = (RecyclerView) view.findViewById(R.id.contact_listing_recycler_view);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        updateUI();
        Log.d("TAG", "On create called for list fragment");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ContactModel contactModel = ContactModel.get(getActivity());
        List<Contact> contacts = contactModel.getContacts();

        if(mContactAdapter == null) {
            mContactAdapter = new ContactAdapter(contacts);
            mContactRecyclerView.setAdapter(mContactAdapter);
        }
        else {
            mContactAdapter.setContacts(contacts);
            mContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:
                Contact contact = new Contact();
                ContactModel.get(getActivity()).addContact(contact);
                Intent intent = ContactPagerActivity.newIntent(getActivity(), contact.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mFirstNameTextView;
        private TextView mLastNameTextView;
        private TextView mBusinessNameTextView;
        private ImageView mContactImageView;
        private Contact mContact;

        public ContactHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mFirstNameTextView = (TextView) itemView.findViewById(R.id.list_item_first_name_view);
            mLastNameTextView = (TextView) itemView.findViewById(R.id.list_item_last_name_view);
            mContactImageView = (ImageView) itemView.findViewById(R.id.list_item_contact_image_view);
        }

        public void bindContact(Contact contact) {
            mContact = contact;
            mFirstNameTextView.setText(mContact.getFirstName());
            mLastNameTextView.setText(mContact.getLastName());
            mContactImageView.setImageResource(mContact.getImageUrl());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ContactPagerActivity.newIntent(getActivity(), mContact.getId());
            startActivity(intent);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
        private List<Contact> mContacts;

        public ContactAdapter(List<Contact> contacts) {
            mContacts = contacts;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_layout, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bindContact(contact);
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        public void setContacts(List<Contact> contacts) {
            mContacts = contacts;
        }
    }
}
