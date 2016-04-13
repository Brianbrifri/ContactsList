package edu.umsl.bekvff.contactslist;

import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by Brian Koehler on 4/6/2016.
 */
public class ContactFragment extends Fragment{

    private static final String ARG_CONTACT_ID = "contact_id";

    private Contact mContact;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailAddressField;
    private EditText mBusinessNameField;
    private ImageView mContactImage;

    public static ContactFragment newInstance(UUID contactId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, contactId);

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID contactId = (UUID) getArguments().getSerializable(ARG_CONTACT_ID);
        mContact = ContactModel.get(getActivity()).getContact(contactId);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);


        mFirstNameField = (EditText) view.findViewById(R.id.contact_first_name);
        mLastNameField = (EditText) view.findViewById(R.id.contact_last_name);
        mEmailAddressField = (EditText) view.findViewById(R.id.contact_email_address);
        mBusinessNameField = (EditText) view.findViewById(R.id.contact_business_name);
        mContactImage = (ImageView) view.findViewById(R.id.contact_image);

        mFirstNameField.setText(mContact.getFirstName());
        mLastNameField.setText(mContact.getLastName());
        mEmailAddressField.setText(mContact.getEmailAddress());
        mBusinessNameField.setText(mContact.getBusinessName());
        mContactImage.setImageResource(R.drawable.default_pic);

        mFirstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEmailAddressField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setEmailAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBusinessNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContact.setBusinessName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mContactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        ContactModel.get(getActivity()).updateContact(mContact);
    }
}
