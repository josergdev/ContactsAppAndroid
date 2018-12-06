package com.josergdev.agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    AgendaHelper agendaHelper;

    ArrayList<Contact> contactList;

    EditText searchText;
    ListView contactListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agendaHelper = AgendaHelper.getInstance(this);

        searchText = findViewById(R.id.editText);
        contactListView = findViewById(R.id.listView);
        searchText.setHint(R.string.searchPlaceholder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts(searchText.getText().toString());
    }

    public void loadContacts(String pattern) {
        if (pattern == null) {
            contactList = agendaHelper.getAllContacts();
        } else {
            contactList = agendaHelper.findContactsWithPattern(pattern);
        }
        ContactsAdapter adapter = new ContactsAdapter(this, contactList);
        contactListView.setAdapter(adapter);
    }

    public void loadContacts() {
        loadContacts( null );
    }

    public void addButtonOnClick(View view) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

    public void searchButtonOnClick(View view) {
        loadContacts(searchText.getText().toString());
    }
}
