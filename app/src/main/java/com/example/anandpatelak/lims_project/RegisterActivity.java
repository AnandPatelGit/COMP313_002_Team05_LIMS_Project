package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText registerFirstName;
    EditText registerLastName;
    EditText registerEmail;
    EditText registerPassword;
    EditText registerPasswordConfirm;
    Button registerBtn;
    Spinner spinnerUser;

    DatabaseReference databaseUsers;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        spinnerUser = (Spinner)findViewById(R.id.spinnerUser);

        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Users, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);
        registerFirstName = (EditText) findViewById(R.id.editTextFirstName);
        registerLastName = (EditText) findViewById(R.id.editTextLastName);
        registerEmail = (EditText) findViewById(R.id.editTextEmail);
        registerPassword = (EditText) findViewById(R.id.editTextPassword);
        registerPasswordConfirm = (EditText) findViewById(R.id.editTextConfirmPassword);
        registerBtn = (Button) findViewById(R.id.buttonRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPassword();
            }
        });

    }
    private void checkPassword(){
        String password = registerPassword.getText().toString().trim();
        String passwordConf = registerPassword.getText().toString().trim();
        if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConf)){
            if(password.equals(passwordConf)){
                addUser();
            }
            else{
                Toast.makeText(this, "Password does not match",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "please enter all the required fields",Toast.LENGTH_LONG).show();
        }
    }
    private void addUser(){
        String firstName = registerFirstName.getText().toString().trim();
        String lastName = registerLastName.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String userRole = spinnerUser.getSelectedItem().toString();

        //check if all the fields are filled or not
        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email)){
            String id = databaseUsers.push().getKey();
            Users user = new Users(id,firstName,lastName,email,password,userRole);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }else{
                                //display some message here
                                Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                            }
                            //progressDialog.dismiss();
                        }
                        });
            databaseUsers.child(id).setValue(user);
            Toast.makeText(this, "User Registered",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "please enter all the required fields",Toast.LENGTH_LONG).show();
        }
    };
}
