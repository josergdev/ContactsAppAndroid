package com.josergdev.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddEntryActivity extends AppCompatActivity {

    AgendaHelper agendaHelper;

    EditText inputName;
    EditText inputPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        agendaHelper = AgendaHelper.getInstance(this);
        inputName = findViewById(R.id.editText2);
        inputName.setHint(R.string.addNamePlaceholder);
        inputPhone = findViewById(R.id.editText3);
        inputPhone.setHint(R.string.addPhonePlaceholder);
    }

    public void addButtonOnClick(View view) {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        Boolean nameIsValid = !name.equals("");
        Boolean phoneIsValid = !phone.equals("");

        if (!nameIsValid || !phoneIsValid) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(getResources().getString(R.string.errorMsg));
            alertDialog.setButton(alertDialog.BUTTON_NEUTRAL, "OK", (DialogInterface.OnClickListener) null);
            alertDialog.show();
        } else {
            Contact contact = agendaHelper.getContactByName(name);
            if (contact == null) {
                Toast.makeText(this, R.string.addingContact, Toast.LENGTH_SHORT).show();
                agendaHelper.addContact(new Contact(name, phone));
            } else {
                Toast.makeText(this, R.string.updatingContact, Toast.LENGTH_SHORT).show();
                contact.phone = phone;
                agendaHelper.updateContactPhone(contact);
            }
            finish();
        }
    }

    public void backButtonOnClick(View view) {
        finish();
    }

}
