package com.josergdev.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AgendaHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACT = "contact";
    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_NAME = "name";
    private static final String KEY_CONTACT_PHONE = "phone";

    private static AgendaHelper agendaHelperInstance;

    public static synchronized AgendaHelper getInstance(Context context) {
        if (agendaHelperInstance == null) {
            agendaHelperInstance = new AgendaHelper(context.getApplicationContext());
        }
        return agendaHelperInstance;
    }

    public AgendaHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACT +
                "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + // Define a primary key
                KEY_CONTACT_NAME + " TEXT," +
                KEY_CONTACT_PHONE + " TEXT" +
                ")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
            onCreate(db);
        }
    }

    //MARK: - CRUD Methods

    public void addContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_CONTACT_NAME, contact.name);
            values.put(KEY_CONTACT_PHONE, contact.phone);

            db.insertOrThrow(TABLE_CONTACT, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.err.println("Error while trying to add contact to database");
            System.err.println(e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        String CONTACT_SELECT_QUERY =  String.format("SELECT * FROM %s",  TABLE_CONTACT);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONTACT_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.id = cursor.getInt(cursor.getColumnIndex(KEY_CONTACT_ID));
                    contact.name = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME));
                    contact.phone = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_PHONE));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error while trying to get contacts from database");
            System.err.println(e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return contacts;
    }

    public void deleteAllContacts() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_CONTACT, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.err.println("Error while trying to delete all contacts from database");
            System.err.println(e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<Contact> findContactsWithPattern(String pattern) {
        List<Contact> contacts = new ArrayList<>();

        String whereClause = KEY_CONTACT_NAME +" like ?";
        String[] whereArgs = {pattern + "%"};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACT, null,  whereClause, whereArgs, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.id = cursor.getInt(cursor.getColumnIndex(KEY_CONTACT_ID));
                    contact.name = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_NAME));
                    contact.phone = cursor.getString(cursor.getColumnIndex(KEY_CONTACT_PHONE));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error while trying to search contacts with specified pattern from database");
            System.err.println(e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return contacts;
    }

}
