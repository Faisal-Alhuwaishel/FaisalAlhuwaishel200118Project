package com.example.faisalalhuwaishel200118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class FirebaseEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_edit);
        ImageView img = (ImageView) findViewById(R.id.imgWeatherFireEdit);
        TextView temp = (TextView) findViewById(R.id.txtTempFireEdit);
        TextView humid = (TextView) findViewById(R.id.txtHumidFireEdit);
        TextView city = (TextView) findViewById(R.id.txtCityFireEdit);
        Weather.weather(img,temp,humid,city,FirebaseEdit.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        EditText ID = (EditText) findViewById(R.id.idInput);
        Spinner key = (Spinner) findViewById(R.id.spinnerKey);
        EditText newValue = (EditText) findViewById(R.id.valueInput);
        Button update = (Button) findViewById(R.id.btnUpdate);
        Button find = (Button) findViewById(R.id.btnFind);
        Button delete = (Button) findViewById(R.id.btnDelete);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !newValue.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ') &&
                        !(newValue.getText().toString().charAt(0) == ' ')) {
                Query query = myRef.orderByKey().equalTo(ID.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                        }
                        else {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                snapshot.getRef().child(key.getSelectedItem().toString()).setValue(newValue.getText().toString());
                                Toasty.success(getBaseContext(), "Student "+ID.getText().toString()+" Updated", Toast.LENGTH_SHORT,true).show();
                                DatabaseHelper myDB = new DatabaseHelper(FirebaseEdit.this);
                                if(myDB.getSpecificResult(ID.getText().toString()).getCount() == 1){
                                    myDB.updateSpecificStudent(ID.getText().toString(),key.getSelectedItem().toString(),newValue.getText().toString());
                                    Toasty.success(getBaseContext(), "Student "+ID.getText().toString()+" Updated in mySQL", Toast.LENGTH_SHORT,true).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Faisal ", "onCancelled", databaseError.toException());
                    }
                });
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
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(ID.getText().toString()).getValue() == null){
                            Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                        }
                        else {
                        Student student = new Student(String.valueOf(snapshot.child(ID.getText().toString()).child("ID").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("First_Name").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("Father_Name").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("Surname").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("NationalID").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("Date").getValue()),
                                String.valueOf(snapshot.child(ID.getText().toString()).child("Gender").getValue()));
                        String result = "ID: "+student.ID+"\n"+
                                "First Name: "+student.First_Name+"\n"+
                                "Father Name: "+student.Father_Name+"\n"+
                                "Surname: "+student.Surname+"\n"+
                                "National ID: "+student.NationalID+"\n"+
                                "Date: "+student.Date+"\n"+
                                "Gender: "+student.Gender+"\n";
                            Toasty.info(getBaseContext(), result, Toast.LENGTH_SHORT,true).show();
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ID.getText().toString().isEmpty() &&
                        !(ID.getText().toString().charAt(0) == ' ')) {
                Query query = myRef.orderByKey().equalTo(ID.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            Toasty.error(getBaseContext(), "Student does not exist", Toast.LENGTH_SHORT,true).show();
                        }
                        else {
                            DataSnapshot snapshot = dataSnapshot.child(ID.getText().toString());
                                Student student = new Student(String.valueOf(snapshot.child("ID").getValue()),
                                        String.valueOf(snapshot.child("First_Name").getValue()),
                                        String.valueOf(snapshot.child("Father_Name").getValue()),
                                        String.valueOf(snapshot.child("Surname").getValue()),
                                        String.valueOf(snapshot.child("NationalID").getValue()),
                                        String.valueOf(snapshot.child("Date").getValue()),
                                        String.valueOf(snapshot.child("Gender").getValue()));
                                String result = "ID: "+student.ID+"\n"+
                                        "First Name: "+student.First_Name+"\n"+
                                        "Father Name: "+student.Father_Name+"\n"+
                                        "Surname: "+student.Surname+"\n"+
                                        "National ID: "+student.NationalID+"\n"+
                                        "Date: "+student.Date+"\n"+
                                        "Gender: "+student.Gender+"\n";
                            Toasty.success(getBaseContext(), "Student deleted: \n"+result, Toast.LENGTH_SHORT,true).show();
                            DatabaseHelper myDB = new DatabaseHelper(FirebaseEdit.this);
                            if(myDB.getSpecificResult(ID.getText().toString()).getCount() == 1){
                                myDB.deleteSpecificProduct(ID.getText().toString());
                                Toasty.success(getBaseContext(), "Student "+ID.getText().toString()+" deleted in mySQL", Toast.LENGTH_SHORT,true).show();
                            }
                                snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Faisal ", "onCancelled", databaseError.toException());
                    }
                });
                }
                else {
                    Toasty.error(getBaseContext(), "Please Insert All values correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
    }
}