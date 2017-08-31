package com.example.vladislavnovoseltsev.contactstest.Logic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vladislavnovoseltsev.contactstest.Logic.IAddresBook;
import com.example.vladislavnovoseltsev.contactstest.Model.Contact;
import com.example.vladislavnovoseltsev.contactstest.R;

import java.util.ArrayList;

/**
 * Created by vladislavnovoseltsev on 31.08.2017.
 */

public class AddressBookAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Contact> contacts;
    IAddresBook iAddresBook;

    public AddressBookAdapter(Context ctx, ArrayList<Contact> contacts, IAddresBook iAddresBook){
        this.iAddresBook = iAddresBook;
        this.contacts = contacts;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return this.contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = lInflater.inflate(R.layout.item_address_book,viewGroup, false);
        }

        Contact contact = (Contact)getItem(i);

        TextView fioPhoneTextView = (TextView)view.findViewById(R.id.text_view_fio_phone);
        fioPhoneTextView.setText(contact.getName()+ " / " + contact.getPhone());


        return view;
    }

    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         iAddresBook.Action((Contact)getItem(i));
        }
    };
}
