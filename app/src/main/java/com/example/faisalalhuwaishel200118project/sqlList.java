package com.example.faisalalhuwaishel200118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class sqlList extends ListActivity {
    List<Student> students = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sql_list);
        List<String> list = new LinkedList<>();
        DatabaseHelper myDB = new DatabaseHelper(this);
        Cursor data = myDB.getListContents();
        data.moveToFirst();
        students.clear();
        for (int i=0;i<data.getCount();i++) {
            students.add(new Student(data.getString(0),data.getString(1),data.getString(2)
                    ,data.getString(3),data.getString(4),data.getString(5),data.getString(6)));
            /*String result =
                    students.get(i).ID+" - "+students.get(i).First_Name+" "+students.get(i).Father_Name+" "+ students.get(i).Surname;*/
            String result = "ID: "+students.get(i).ID+"\n"+
                    "First Name: "+students.get(i).First_Name+"\n"+
                    "Father Name: "+students.get(i).Father_Name+"\n"+
                    "Surname: "+students.get(i).Surname+"\n"+
                    "National ID: "+students.get(i).NationalID+"\n"+
                    "Date: "+students.get(i).Date+"\n"+
                    "Gender: "+students.get(i).Gender+"\n";
            list.add(result);
            data.moveToNext();
        }
        setListAdapter(new ArrayAdapter<String>(sqlList.this,R.layout.activity_firebase_list,R.id.list,list));

    }
    protected void onListItemClick(ListView l, View v, int i, long id) {
        String result = students.get(i).First_Name+" "+students.get(i).Surname;
        Toasty.info(getBaseContext(), result, Toast.LENGTH_SHORT,true).show();
    }

}