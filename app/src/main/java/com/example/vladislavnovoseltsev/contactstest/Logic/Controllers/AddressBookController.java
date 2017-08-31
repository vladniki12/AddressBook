package com.example.vladislavnovoseltsev.contactstest.Logic.Controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.example.vladislavnovoseltsev.contactstest.Logic.Adapter.AddressBookAdapter;
import com.example.vladislavnovoseltsev.contactstest.Logic.IAddresBook;
import com.example.vladislavnovoseltsev.contactstest.Model.Contact;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by vladislavnovoseltsev on 31.08.2017.
 */

public class AddressBookController {

    static final String CONTACT_ID = ContactsContract.Contacts._ID;
    static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;


    static AddressBookController addressBookController;

    IAddresBook iAddresBook;
    Context ctx;

    AddressBookController(Context ctx, IAddresBook iAddresBook) {
        this.iAddresBook = iAddresBook;
        this.ctx = ctx;
        new AsyncReadContacts().execute();
    }


    public static AddressBookController getInstance(Context ctx, IAddresBook iAddresBook) {
        if(addressBookController == null) {
            addressBookController = new AddressBookController(ctx,iAddresBook);
        }
        return addressBookController;
    }


    class AsyncReadContacts extends AsyncTask<Void,Void,ArrayList<Contact>> {



        public AsyncReadContacts() {

        }
        @Override
        protected ArrayList<Contact> doInBackground(Void... voids) {
            TreeMap<Integer, Contact> addressBookTreeMap = new TreeMap<>();

            ContentResolver cr = ctx.getContentResolver();

            Cursor pCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{PHONE_NUMBER, PHONE_CONTACT_ID,DISPLAY_NAME},
                    null,
                    null,
                    null
            );
            while (pCur.moveToNext()) {
                Integer contactId = pCur.getInt(pCur.getColumnIndex(PHONE_CONTACT_ID));
                String phone = pCur.getString(pCur.getColumnIndex(PHONE_NUMBER));
                String name = pCur.getString(pCur.getColumnIndex(DISPLAY_NAME));
                Contact contact = new Contact( name , phone );

                addressBookTreeMap.put(contactId,contact);


            }

            ArrayList<Contact> arrayList = new ArrayList<>(addressBookTreeMap.values());

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            super.onPostExecute(contacts);
            iAddresBook.Action(contacts);

        }
    }


}
