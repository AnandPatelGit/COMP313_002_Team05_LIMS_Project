package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DownloadFile extends AppCompatActivity {

    TextView tvFileName;
    String fileName,folderName;
    Button btnDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        tvFileName = (TextView) findViewById(R.id.textViewFileName);
        btnDownload = (Button) findViewById(R.id.buttonDownload);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("selected-file");
            String h =(String) b.get("selected-folder");
            tvFileName.setText(j);
            fileName = j;
            folderName = h;

        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });
    }

    private void downloadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String fileLocation = "Folders/"+folderName+"/"+fileName;
        Log.i("FileURL",fileLocation);
        StorageReference pathReference = storageRef.child(fileLocation);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Log.e("Tuts+", "uri: " + uri.toString());
            }
        });
    }
}
