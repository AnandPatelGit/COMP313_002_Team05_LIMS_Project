package com.example.anandpatelak.lims_project;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "MyApp";
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private String userRole;

    private FirebaseDatabase firebaseData;
    private DatabaseReference db_ref;
    //progress dialog
    private ProgressDialog progressDialog;

    EditText inputUsername,inputPassword;
    Button btnLogin,btnRegister;

    private static String TABLE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseData = FirebaseDatabase.getInstance();
        db_ref = firebaseData.getReference("limps-centennial");
        //if the objects getcurrentuser method is not null
        //means user is already logged in
        /*if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        }*/



        //EditTexts Register.
        inputUsername = (EditText)findViewById(R.id.editTextLoginUsername);
        inputPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        //Buttons Register
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnRegister = (Button)findViewById(R.id.buttonLoginRegister);

        progressDialog = new ProgressDialog(this);

    }
    @Override
    protected void onStart(){
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    //METHODS TO REDIRECT USERS
    //1.Redirect to Register
    public void register(View view){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    //2.Redirect to Admin Page
    public void adminRedirect(){
        //finish();
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
    //2.Redirect to instructor Page
    public void instructorRedirect(){
        //finish();
        Intent intent = new Intent(this, InstructorActivity.class);
        startActivity(intent);
    }
    //3.Redirect to Student Page
    public void studentRedirect(){
        //finish();
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }


    public void userLogin(View view){
        String email = inputUsername.getText().toString().trim();
        String pass = inputPassword.getText().toString().trim();



        if(TextUtils.isEmpty(email)){
            inputUsername.setError("Email is required");
            inputUsername.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputUsername.setError("Email is not valid");
            inputUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In!! Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull

                        
                        if(task.isSuccessful()){
                            //start the profile activity
                            checkUserRole();
                            //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Please enter valid username & password",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }
    public void checkUserRole(){
        db_ref = firebaseData.getReference("users");
        db_ref.orderByChild("emailID").equalTo(inputUsername.getText().toString().trim()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Users user = dataSnapshot.getValue(Users.class);
                System.out.println(dataSnapshot.getKey() + " is " + user.getRole()
                +" Email: " +user.getEmailID()
                );
                userRole = user.getRole();
                verifyUserRole();
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

    private void verifyUserRole() {
        if(userRole.equals("Admin"))
        {
            adminRedirect();
        }
        else if(userRole.equals("Student"))
        {
            studentRedirect();
        }
        else if(userRole.equals("Instructor")){
            instructorRedirect();
        }
        else{
            Toast.makeText(this,userRole,Toast.LENGTH_LONG).show();
        }
    }
}