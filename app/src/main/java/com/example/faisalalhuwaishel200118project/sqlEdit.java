package com.example.faisalalhuwaishel200118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class sqlEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_edit);
        ImageView img = (ImageView) findViewById(R.id.imgWeatherSQLEdit);
        TextView temp = (TextView) findViewById(R.id.txtTempSQLEdit);
        TextView humid = (TextView) findViewById(R.id.txtHumidSQLEdit);
        TextView city = (TextView) findViewById(R.id.txtCitySQLEdit);
        Weather.weather(img,temp,humid,city,sqlEdit.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        DatabaseHelper myDB = new DatabaseHelper(this);
        EditText ID = (EditText) findViewById(R.id.idInputSQL);
        Spinner key = (Spinner) findViewById(R.id.spinnerKeySQL);
        EditText newValue = (EditText) findViewById(R.id.valueInputSQL);
        Button insert = (Button) findViewById(R.id.btnInsertSQL);
        Button update = (Button) findViewById(R.id.btnUpdateSQL);
        Button find = (Button) findViewById(R.id.btnFindSQL);
        Button delete = (Button) findViewById(R.id.btnDeleteSQL);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ')) {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(ID.getText().toString()).getValue() == null){
                                Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                            }
                            else if(myDB.getSpecificResult(ID.getText().toString()).getCount() == 1){
                                Student student = new Student(String.valueOf(snapshot.child(ID.getText().toString()).child("ID").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("First_Name").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Father_Name").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Surname").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("NationalID").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Date").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Gender").getValue()));
                                boolean state = myDB.insertWithUpdate(student.ID,student.First_Name,student.Father_Name,student.Surname,
                                        student.NationalID,student.Date,student.Gender);
                                if(state) {
                                    Toasty.success(getBaseContext(), "Student "+student.ID+" values updated in SQLite Database", Toast.LENGTH_SHORT,true).show();
                                }
                                else {
                                    Toasty.error(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT,true).show();
                                }
                            }
                            else {
                                Student student = new Student(String.valueOf(snapshot.child(ID.getText().toString()).child("ID").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("First_Name").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Father_Name").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Surname").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("NationalID").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Date").getValue()),
                                        String.valueOf(snapshot.child(ID.getText().toString()).child("Gender").getValue()));
                                boolean state = myDB.addData(student.ID,student.First_Name,student.Father_Name,student.Surname,
                                        student.NationalID,student.Date,student.Gender);
                                if(state) {
                                    /*String result = "ID: " + student.ID + "\n" +
                                            "First Name: " + student.First_Name + "\n" +
                                            "Father Name: " + student.Father_Name + "\n" +
                                            "Surname: " + student.Surname + "\n" +
                                            "National ID: " + student.NationalID + "\n" +
                                            "Date: " + student.Date + "\n" +
                                            "Gender: " + student.Gender + "\n";*/
                                    Toasty.success(getBaseContext(), "Student "+student.ID+" inserted into SQLite Database", Toast.LENGTH_SHORT,true).show();
                                }
                                else {
                                    Toasty.error(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT,true).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toasty.error(getBaseContext(), "Please Insert All values correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !newValue.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ') &&
                        !(newValue.getText().toString().charAt(0) == ' ')) {
                    Cursor data = myDB.getSpecificResult(ID.getText().toString());
                    if(data.getCount() == 1) {
                        if (myDB.updateSpecificStudent(ID.getText().toString(),key.getSelectedItem().toString(),newValue.getText().toString())){
                            Toasty.success(getBaseContext(), "Student "+ID.getText().toString()+" Updated in SQLite Database", Toast.LENGTH_SHORT,true).show();

                        }
                        else {
                            Toasty.error(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT,true).show();
                        }
                    }
                    else {
                        Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                    }
                }
                else {
                    Toasty.error(getBaseContext(), "Please Insert All values correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ')) {
                        Cursor data = myDB.getSpecificResult(ID.getText().toString());
                        data.moveToFirst();
                        if(data.getCount() == 1) {
                            String result = "ID: " + data.getString(0) + "\n" +
                                    "First Name: " + data.getString(1) + "\n" +
                                    "Father Name: " + data.getString(2) + "\n" +
                                    "Surname: " + data.getString(3) + "\n" +
                                    "National ID: " + data.getString(4) + "\n" +
                                    "Date: " + data.getString(5) + "\n" +
                                    "Gender: " + data.getString(6) + "\n";
                            Toasty.info(getBaseContext(), result, Toast.LENGTH_SHORT,true).show();
                        }
                        else {
                            Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                        }
                }
                else {
                    Toasty.error(getBaseContext(), "Please Insert All Values Correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ')) {
                    if(myDB.deleteSpecificProduct(ID.getText().toString()) == 1){
                        Toasty.success(getBaseContext(), "Student "+ID.getText().toString()+" Deleted From SQLite Database", Toast.LENGTH_SHORT,true).show();
                    }
                    else {
                        Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                    }
                }
                else {
                    Toasty.error(getBaseContext(), "Please Insert All values correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
    }
}