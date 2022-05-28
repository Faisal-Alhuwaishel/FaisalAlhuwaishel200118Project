package com.example.faisalalhuwaishel200118project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class FirebaseInsert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_insert);
        ImageView img = (ImageView) findViewById(R.id.imgWeatherFireInsert);
        TextView temp = (TextView) findViewById(R.id.txtTempFireInsert);
        TextView humid = (TextView) findViewById(R.id.txtHumidFireInsert);
        TextView city = (TextView) findViewById(R.id.txtCityFireInsert);
        Weather.weather(img,temp,humid,city,FirebaseInsert.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students");
        EditText studentName = (EditText) findViewById(R.id.idInput);
        EditText studentFather = (EditText) findViewById(R.id.fatherName);
        EditText studentSurname = (EditText) findViewById(R.id.valueInput);
        EditText studentNID = (EditText) findViewById(R.id.nID);
        //EditText studentDate = (EditText) findViewById(R.id.dateInput);
        ImageButton date = (ImageButton) findViewById(R.id.btnDate);
        TextView studentDate = (TextView) findViewById(R.id.txtDate);
        EditText studentGender = (EditText) findViewById(R.id.genderInput);
        Button insert = (Button) findViewById(R.id.btnInsert);
        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance();
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                c.set(Calendar.YEAR, i);
                c.set(Calendar.MONTH, i1);
                c.set(Calendar.DAY_OF_MONTH, i2);
                studentDate.setText(dateFormat.format(c.getTime()));
            }
        } ;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FirebaseInsert.this,d,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!studentName.getText().toString().isEmpty() &&
                        !studentFather.getText().toString().isEmpty() &&
                        !studentSurname.getText().toString().isEmpty() &&
                        !studentNID.getText().toString().isEmpty() &&
                        !studentDate.getText().toString().isEmpty() &&
                        !studentGender.getText().toString().isEmpty() &&
                        !(studentName.getText().toString().charAt(0) == ' ') &&
                        !(studentFather.getText().toString().charAt(0) == ' ') &&
                        !(studentSurname.getText().toString().charAt(0) == ' ') &&
                        !(studentNID.getText().toString().charAt(0) == ' ') &&
                        !(studentDate.getText().toString().charAt(0) == ' ') &&
                        !(studentGender.getText().toString().charAt(0) == ' '))
                {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int studentId = -1;
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                               studentId = Integer.parseInt(String.valueOf(dataSnapshot.child("ID").getValue()));
                            }
                            studentId++;
                            Student student = new Student(
                                    String.valueOf(studentId),studentName.getText().toString(),
                                    studentFather.getText().toString(), studentSurname.getText().toString(),
                                    studentNID.getText().toString(), studentDate.getText().toString(),
                                    studentGender.getText().toString());
                            myRef.child(String.valueOf(studentId)).setValue(student)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toasty.success(getBaseContext(), "Student has been inserted into database", Toast.LENGTH_SHORT,true).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toasty.error(getBaseContext(), "Error: "+e, Toast.LENGTH_SHORT,true).show();
                                        }
                                    });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toasty.error(getBaseContext(), "Error: "+error.toString(), Toast.LENGTH_SHORT,true).show();
                        }
                    });
                } else {
                    Toasty.error(getBaseContext(), "Please Insert All values correctly", Toast.LENGTH_SHORT,true).show();
                }
            }
        });
    }
}