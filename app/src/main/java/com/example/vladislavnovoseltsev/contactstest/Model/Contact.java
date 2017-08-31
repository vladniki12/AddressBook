package com.example.vladislavnovoseltsev.contactstest.Model;

/**
 * Created by vladislavnovoseltsev on 31.08.2017.
 */

public class Contact {
    String name;
    String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName(){
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }
}

