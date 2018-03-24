package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructorActivity extends AppCompatActivity {

    Button btnAddFolder, btnDisplayFolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        btnAddFolder = (Button) findViewById(R.id.addFolderButton);

    }
    public void naviagateToNewFolderActivity(View view){
        Intent intent = new Intent(this, CreateFolder.class);
        startActivity(intent);
    }
}
