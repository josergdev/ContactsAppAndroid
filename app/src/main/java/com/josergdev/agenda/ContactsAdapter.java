package com.josergdev.agenda;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    public ContactsAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }
        TextView name = convertView.findViewById(R.id.textView3);
        final TextView phone = convertView.findViewById(R.id.textView4);

        name.setText(contact.name);
        phone.setText(contact.phone);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String phoneToCall = contact.phone;
                intent.setData(Uri.parse("tel:"+ phoneToCall));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
