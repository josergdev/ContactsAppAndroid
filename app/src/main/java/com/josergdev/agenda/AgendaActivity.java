package com.josergdev.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    AgendaHelper agendaHelper;

    ArrayAdapter<Contact> adapter;
    List<Contact> contactList;

    EditText searchText;
    Button searchButton;
    Button addButton;
    ListView contactListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agendaHelper = AgendaHelper.getInstance(this);

        searchText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.button);
        addButton = findViewById(R.id.button3);
        contactListView = findViewById(R.id.listView);

        searchText.setHint(R.string.searchPlaceholder);
        searchButton.setText(R.string.searchButton);
        addButton.setText(R.string.addButton);

        /*
        agendaHelper.addContact(new Contact("Jose", "649787322"));
        agendaHelper.addContact(new Contact("Jose María", "676888888"));
        agendaHelper.addContact(new Contact("Joaquín", "654123456"));
        */

        updateContactListView();
    }

    public void updateContactListView(String pattern) {
        contactList = agendaHelper.findContactsWithPattern(pattern);

        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contactList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(contactList.get(position).name);
                text2.setText(contactList.get(position).phone);
                return view;
            }
        };

        contactListView.setAdapter(adapter);
    }

    public void updateContactListView() {
        updateContactListView("");
    }

    public void addButtonOnClick(View view) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

    public void searchButtonOnClick(View view) {
        updateContactListView(searchText.getText().toString());
        // agendaHelper.deleteAllContacts();
    }
}
