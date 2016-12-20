package com.example.lyapk.krosspl_lab1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

public class DBActivity extends AppCompatActivity {

    private Cursor crs;
    private CustomCursorAdapter listAdapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        init();
    }

    SqliteController sqliteController;

    private void init() {
       sqliteController = SqliteController.getInstance(getApplicationContext());

        final ListView listView = (ListView) findViewById(R.id.listview);

//        ArrayList data = (ArrayList) sqliteController.getData();
//        String[] messagesString = (String[]) data.get(0);
//        final String[] idsString = (String[]) data.get(1);
//        final ArrayList dbData = new ArrayList<String>(Arrays.asList(messagesString));

//        final ArrayAdapter listAdapt = new ArrayAdapter(this, R.layout.list_item, dbData);

        crs = sqliteController.getData();

        listAdapt = new CustomCursorAdapter(this, crs, 0);


        listView.setAdapter(listAdapt);
        listView.setTextFilterEnabled(true);
        listAdapt.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("TAG",String.valueOf(view.findViewById(R.id.listTextView).getTag()));
                sqliteController.deleteRow(Integer.valueOf(String.valueOf(view.findViewById(R.id.listTextView).getTag())));
                crs = sqliteController.getData();
                listAdapt.changeCursor(crs);
                listAdapt.notifyDataSetChanged();
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.searchViewDB);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText !=null && newText != "") {
                    crs = sqliteController.getFilteredData(newText);
                    Log.e("FILTERTEXT", String.valueOf(crs.getCount()));
                    listAdapt.changeCursor(crs);
                    listAdapt.notifyDataSetChanged();
                } else {
                    crs = sqliteController.getData();
                    Log.e("NEWTEXT", crs.toString());
                    listAdapt.changeCursor(crs);
                    listAdapt.notifyDataSetChanged();
                }

                return false;
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainActivity();
            }
        });

    }

    private void toMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        crs = sqliteController.getData();
        listAdapt.changeCursor(crs);
        listAdapt.notifyDataSetChanged();

    }
}
