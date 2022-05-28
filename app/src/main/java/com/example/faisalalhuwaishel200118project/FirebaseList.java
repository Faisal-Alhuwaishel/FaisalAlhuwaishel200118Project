package com.example.faisalalhuwaishel200118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class FirebaseList extends ListActivity {
    List<Student> students = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_firebase_list);
        List<String> list = new LinkedList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                students.clear();
                list.clear();
                int i=0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    students.add( new Student(String.valueOf(dataSnapshot.child("ID").getValue()),
                            String.valueOf(dataSnapshot.child("First_Name").getValue()),
                            String.valueOf(dataSnapshot.child("Father_Name").getValue()),
                            String.valueOf(dataSnapshot.child("Surname").getValue()),
                            String.valueOf(dataSnapshot.child("NationalID").getValue()),
                            String.valueOf(dataSnapshot.child("Date").getValue()),
                            String.valueOf(dataSnapshot.child("Gender").getValue())));
                    String result =
                            students.get(i).ID+" - "+students.get(i).First_Name+" "+students.get(i).Father_Name+" "+ students.get(i).Surname;
                    list.add(result);
                    i++;
                }
                setListAdapter(new ArrayAdapter<String>(FirebaseList.this,R.layout.activity_firebase_list,R.id.list,list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void onListItemClick(ListView l, View v, int i, long id) {
        StringBuffer buffer = new StringBuffer();
            buffer.append("ID: "+students.get(i).ID+"\n"+
                    "First Name: "+students.get(i).First_Name+"\n"+
                    "Father Name: "+students.get(i).Father_Name+"\n"+
                    "Surname: "+students.get(i).Surname+"\n"+
                    "National ID: "+students.get(i).NationalID+"\n"+
                    "Date: "+students.get(i).Date+"\n"+
                    "Gender: "+students.get(i).Gender+"\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(FirebaseList.this);
            builder.setTitle(students.get(i).First_Name+" "+students.get(i).Surname);
            builder.setMessage(buffer.toString());
            builder.show();

    }

}