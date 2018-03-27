package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    ImageView img;
        EditText displayNameET;
        Button submitBtn;

        Uri uriProfileImage;
        ProgressBar progressBar;
        FirebaseAuth mAuth;
        String profileImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        //get views
        displayNameET = (EditText) findViewById(R.id.etContactNumber);
        submitBtn = (Button) findViewById(R.id.submitButton);
        img = (ImageView) findViewById(R.id.imageView1);

        progressBar = findViewById(R.id.progressbar);

        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showImagePicker();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

    }

    private void saveUserInfo() {
        String userDisplayName = displayNameET.getText().toString().trim();
        if(userDisplayName.isEmpty()){
            displayNameET.setError("Contact Number Required");
            displayNameET.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && profileImageUrl != null)
        {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userDisplayName)
                    .setPhotoUri(Uri.parse(profileImageUrl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){

            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                img.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImage = FirebaseStorage.getInstance().getReference("profilePics/" + System.currentTimeMillis() + ".jpg");
        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileImage.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void showImagePicker(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
    private void loginAct()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
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
