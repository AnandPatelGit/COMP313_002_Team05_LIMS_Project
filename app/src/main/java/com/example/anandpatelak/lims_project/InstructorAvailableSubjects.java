package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InstructorAvailableSubjects extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView folderList;
    TextView headerTextViewInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_available_subjects);

        folderList = (ListView) findViewById(R.id.listVIewSubjects);

        headerTextViewInstructor = (TextView)findViewById(R.id.textView16) ;


        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("subjects");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        headerTextViewInstructor.setText("Available Course's for "+email);

        databaseReference.orderByChild("subjectProfessor").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("subjectName").getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(InstructorAvailableSubjects.this, android.R.layout.simple_list_item_1,arrayList);
                folderList.setAdapter(arrayAdapter);
                folderList.setOnItemClickListener(new InstructorAvailableSubjects.FileListClickHandler());
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
    public class FileListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            //TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = ((TextView)view).getText().toString();
            // MyClass item = (MyClass) adapter.getItemAtPosition(position);
            Intent intent = new Intent(InstructorAvailableSubjects.this, InstructorActivity.class);
            intent.putExtra("selected-subject", text);
            startActivity(intent);

        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.slider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent (this, LoginActivity.class));
        }
        return true;
    }
}
