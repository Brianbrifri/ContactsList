package edu.umsl.bekvff.contactslist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

/**
 * Created by Brian Koehler on 4/6/2016.
 */

//Contact fragment, has text change listeners in the edit text fields to update the contact info
    //clicking on the photo icon launches the camera to take a picture and replace the default contact
    //picture. Clicking on the message icon shows a list of apps available to send an email to the email
    //address of the contact you are in. The database gets updated when this fragment is paused, ie put into
    //background by pressing the back button
public class ContactFragment extends Fragment{

    private static final String ARG_CONTACT_ID = "contact_id";
    private static final int REQUEST_CAMERA = 1;

    private Contact mContact;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailAddressField;
    private EditText mBusinessNameField;
    private ImageView mContactImage;
    private ImageView mSendMessageImage;
    private File mPhotoFile;

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
        mPhotoFile = ContactModel.get(getActivity()).getPhotoFile(mContact);

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
        mSendMessageImage = (ImageView) view.findViewById(R.id.send_message_button);


        mFirstNameField.setText(mContact.getFirstName());
        mLastNameField.setText(mContact.getLastName());
        mEmailAddressField.setText(mContact.getEmailAddress());
        mBusinessNameField.setText(mContact.getBusinessName());

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

        PackageManager packageManager = getActivity().getPackageManager();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mContactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_CAMERA);
            }
        });

        mSendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder("mailto:").append(mContact.getEmailAddress());
                String mailId = builder.toString();
                Uri uri = Uri.parse(mailId);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        updatePhotoView();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        ContactModel.get(getActivity()).updateContact(mContact);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CAMERA) {
            updatePhotoView();
        }

    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mContactImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.default_pic, null));
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mContactImage.setImageBitmap(bitmap);
        }
    }
}
