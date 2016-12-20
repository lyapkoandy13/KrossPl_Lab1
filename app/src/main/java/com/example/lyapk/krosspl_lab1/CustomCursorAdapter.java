package com.example.lyapk.krosspl_lab1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by lyapkoandy13 on 19.12.16.
 */

public class CustomCursorAdapter extends CursorAdapter{

    // Default constructor
    public CustomCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvPriority = (TextView) view.findViewById(R.id.listTextView);
        // Extract properties from cursor
        String sometext = cursor.getString(cursor.getColumnIndexOrThrow("sometext"));
        tvPriority.setTag(cursor.getString(cursor.getColumnIndexOrThrow("_id")));

        // Populate fields with extracted properties
        tvPriority.setText(sometext);
    }
}
