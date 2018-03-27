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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        mContext = this;
        tvFolderName =(TextView) findViewById(R.id.fileUploadName);
        tvFileName = (TextView) findViewById(R.id.userFileName);
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
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });
    }

    private void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("Folders/"+tvFolderName.getText().toString().trim()+"/"+System.currentTimeMillis());
        if (filePath != null) {
            //progressBar.setVisibility(View.VISIBLE);
            storageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //progressBar.setVisibility(View.GONE);
                    //profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                    Toast.makeText(UploadFile.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),StudentActivity.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   // progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadFile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void selectFileToUpload() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), CHOOSE_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null){

            filePath = data.getData();
            tvFileName.setText("File Selected");

        }
    }
}
