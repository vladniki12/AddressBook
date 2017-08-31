package com.example.vladislavnovoseltsev.contactstest;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vladislavnovoseltsev.contactstest.Logic.Adapter.AddressBookAdapter;
import com.example.vladislavnovoseltsev.contactstest.Logic.Controllers.AddressBookController;
import com.example.vladislavnovoseltsev.contactstest.Logic.IAddresBook;
import com.example.vladislavnovoseltsev.contactstest.Model.Contact;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            final ListView addressBookListView = (ListView) findViewById(R.id.list_view_address_book);

            AddressBookController.getInstance(this, new IAddresBook() {
                @Override
                public void Action(Object object) {
                    AddressBookAdapter addressBookAdapter = new AddressBookAdapter(MainActivity.this,
                            (ArrayList<Contact>) object,
                            new IAddresBook() {
                                @Override
                                public void Action(Object object) {
                                    call(((Contact)object).getPhone());
                                }
                            }
                    );
                    addressBookListView.setAdapter(addressBookAdapter);
                    addressBookListView.setOnItemClickListener(addressBookAdapter.onItemClickListener);
                }




            });

        }catch (Throwable ex){
            ex.printStackTrace();
        }

    }

    void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        try {
            callIntent.setPackage("com.android.phone");
            startActivity(callIntent);
        } catch(Exception e) {
            callIntent.setPackage("com.android.server.telecom");
            startActivity(callIntent);
        }
    }
}
