package com.example.anandpatelak.lims_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class UploadFile extends AppCompatActivity {
    private static final int CHOOSE_FILE = 102;
    TextView tvFolderName,tvFileName;
    ImageView imageView;
    Button btnUpload;
    private Context mContext;
    ProgressBar progressBar;
    private Uri filePath;
    DatabaseReference databaseFiles;
    String fileRef,fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        databaseFiles = FirebaseDatabase.getInstance().getReference("files");
        mContext = this;
        tvFolderName =(TextView) findViewById(R.id.fileUploadName);
        tvFileName = (TextView) findViewById(R.id.userFileName);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("selected-item");
            tvFolderName.setText(j);
        }

        imageView =(ImageView) findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFileToUpload();
            }
        });
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        btnUpload.setEnabled(false);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        StorageReference storageRef = storage.getReference("Folders/"+tvFolderName.getText().toString().trim()+"/"+System.currentTimeMillis()+email+".zip");
        if (filePath != null) {

            progressBar.setVisibility(View.VISIBLE);
            fileRef = "Folders/"+tvFolderName.getText().toString().trim()+"/"+System.currentTimeMillis();
            fileName = System.currentTimeMillis()+".zip";
            storageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    //profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                    Toast.makeText(UploadFile.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                    storeFileData();
                    navigateToMainActivity();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadFile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void selectFileToUpload() {
        Intent intent = new Intent();
        intent.setType("zip/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), CHOOSE_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){

            filePath = data.getData();
            tvFileName.setText("File Selected");
            btnUpload.setEnabled(true);

        }
    }
    public void navigateToMainActivity(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
    protected void storeFileData(){
        String fileID = databaseFiles.push().getKey();
        String folderName = tvFolderName.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String ref = fileRef;
        String nameOfFile = fileName;
        StudentFile file = new StudentFile(fileID,folderName,email,ref, nameOfFile);

        databaseFiles.child(fileID).setValue(file);
    }

}
