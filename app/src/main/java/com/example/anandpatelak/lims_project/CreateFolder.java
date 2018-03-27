package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateFolder extends AppCompatActivity {

    DatabaseReference databaseFolders;
    EditText folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);
        folderName = (EditText) findViewById(R.id.etFolderName);

        databaseFolders = FirebaseDatabase.getInstance().getReference("folders");

    }
    public void createFolder(View view){
    String fName = folderName.getText().toString().trim();
    String id = databaseFolders.push().getKey();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();
    String subjectName = "COMP-303";
    Folder folder = new Folder(id,fName,email,subjectName);

    databaseFolders.child(id).setValue(folder);
        Toast.makeText(this, "Folder Created",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),InstructorActivity.class));
    }
}
