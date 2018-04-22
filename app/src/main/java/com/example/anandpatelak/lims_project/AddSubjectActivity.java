package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReferenceSubjects;
    Button addBtn;
    EditText etName;
    Spinner spinner;
    List<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        etName = (EditText) findViewById(R.id.editTextSubjectName);
        addBtn = (Button) findViewById(R.id.buttonCreateSub);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveSubject();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReferenceSubjects = firebaseDatabase.getReference("subjects");
        spinner = (Spinner) findViewById(R.id.spinnerProfessor);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("emailID").getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(AddSubjectActivity.this, android.R.layout.simple_list_item_1,arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    protected void onSaveSubject(){
    String subjectName = etName.getText().toString().trim();
    String professorEmail = spinner.getSelectedItem().toString();
    if(TextUtils.isEmpty(subjectName)){
        etName.setError("Please Enter Name");
        etName.findFocus();
    }
    else{
        String id = databaseReferenceSubjects.push().getKey();
        Subject subject = new Subject(id,subjectName, professorEmail);
        databaseReferenceSubjects.child(id).setValue(subject);
        Toast.makeText(this, "Subject added",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),AdminActivity.class));
    }

    }
}
