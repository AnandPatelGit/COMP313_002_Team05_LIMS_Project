package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.R.layout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button btnAddFolder, btnDisplayFolders;
    ListView folderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        btnAddFolder = (Button) findViewById(R.id.addFolderButton);
        folderList = (ListView) findViewById(R.id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("folders");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("folderName").getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(InstructorActivity.this, android.R.layout.simple_list_item_1,arrayList);
                folderList.setAdapter(arrayAdapter);
                folderList.setOnItemClickListener(new FileListClickHandler());
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
    public void naviagateToNewFolderActivity(View view){
        Intent intent = new Intent(this, CreateFolder.class);
        startActivity(intent);
    }
    public class FileListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            //TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = ((TextView)view).getText().toString();
            // MyClass item = (MyClass) adapter.getItemAtPosition(position);
            Intent intent = new Intent(InstructorActivity.this, InstructorFolderItems.class);
            intent.putExtra("selected-folder", text);
            startActivity(intent);

        }

    }
}
