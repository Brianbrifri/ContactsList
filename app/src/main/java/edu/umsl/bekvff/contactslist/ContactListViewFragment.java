package edu.umsl.bekvff.contactslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by b-kizzle on 4/5/16.
 */
public class ContactListViewFragment extends Fragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_recycler_view, container, false);
        mContactRecyclerView = (RecyclerView) view.findViewById(R.id.contact_listing_recycler_view);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        ContactModel contactModel = ContactModel.get(getActivity());
        List<Contact> contacts = contactModel.getContacts();
        mContactAdapter = new ContactAdapter(contacts);
        mContactRecyclerView.setAdapter(mContactAdapter);
    }


    private class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mFirstNameTextView;
        private TextView mLastNameTextView;
        private TextView mBusinessNameTextView;
        private ImageView mContactImageView;

        public ContactHolder(View itemView) {
            super(itemView);
            mFirstNameTextView = (TextView) itemView.findViewById(R.id.list_item_first_name_view);
            mLastNameTextView = (TextView) itemView.findViewById(R.id.list_item_last_name_view);
            mContactImageView = (ImageView) itemView.findViewById(R.id.list_item_contact_image_view);
        }

        public void bindContact(Contact contact) {
            mFirstNameTextView.setText(contact.getFirstName());
            mLastNameTextView.setText(contact.getLastName());
            mContactImageView.setImageResource(contact.getImageUrl());
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
    }
}
