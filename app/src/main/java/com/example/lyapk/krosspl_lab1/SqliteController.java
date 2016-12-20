package com.example.lyapk.krosspl_lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 26.07.2016.
 */

public class SqliteController extends SQLiteOpenHelper {

    private static SqliteController sInstance;

    public static synchronized SqliteController getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new SqliteController(context.getApplicationContext());
        }
        return sInstance;
    }


    private SqliteController(Context applicationcontext) {
        super(applicationcontext, "sqlitedb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE data ( _id INTEGER PRIMARY KEY AUTOINCREMENT, sometext TEXT)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS data";
        database.execSQL(query);
        onCreate(database);
    }

    public void insertSometext(String sometext) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sometext", sometext);
        database.insert("data", null, values);
    }

    public Cursor getFilteredData(String filter){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT id AS _id, * FROM data WHERE sometext LIKE '%"+filter+"%' ORDER BY _id DESC;";
        Cursor crs = database.rawQuery(query, null);
        return crs;
    }

    public Cursor getData(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT id AS _id, * FROM data ORDER BY _id DESC;";
        Cursor crs = database.rawQuery(query, null);
//
//        ArrayList<HashMap> messages = new ArrayList<HashMap>();
//        while(crs.moveToNext()){
//            HashMap hashMap = new HashMap();
//            hashMap.put("sometext", crs.getString(crs.getColumnIndex("sometext")));
//            hashMap.put("id", crs.getString(crs.getColumnIndex("id")));
//            messages.add(hashMap);
//        }
//        String[] messagesString = new String[messages.size()];
//        String[] idsString = new String[messages.size()];
//        int i = 0;
//        for(HashMap hashMap : messages){
//            idsString[i] = String.valueOf(hashMap.get("id"));
//            messagesString[i] = String.valueOf(hashMap.get("sometext"));
//            i++;
//
//        }
//        ArrayList<String[]> arrayList = new ArrayList<>();
//        arrayList.add(0, messagesString);
//        arrayList.add(1, idsString);

        return crs;
    }

    //clears database
    public void clearDatabase(String databaseName){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM "+databaseName+";";
        database.execSQL(query);
    }

    public void deleteRow(int id){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM data WHERE id="+id+";";
        database.execSQL(query);
    }

    public void deleteRow(String text){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM data WHERE sometext='"+text+"';";
        database.execSQL(query);
    }
//
//
//    public void truncateUserTable(){
//        SQLiteDatabase database = this.getWritableDatabase();
//        String query = "DROP TABLE IF EXISTS user;";
//        database.execSQL(query);
//        query = "CREATE TABLE user ( id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, secondname TEXT, email TEXT, registered INT)";
//        database.execSQL(query);
//
//    }

    //counts number of rows in database
    public int countRows(String databaseName){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "SELECT * FROM "+databaseName+";";
        Cursor crs = database.rawQuery(query, null);
        Integer count = crs.getCount();

        if(count == null) count=0;

        return count;
    }

}