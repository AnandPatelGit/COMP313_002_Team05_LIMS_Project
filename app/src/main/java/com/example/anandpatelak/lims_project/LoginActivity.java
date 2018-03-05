package com.example.anandpatelak.lims_project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    RadioButton radioBtnAdmin, radioBtnStudent, radioBtnInstructor;
    EditText inputUsername,inputPassword;
    Button btnLogin,btnRegister;
    DatabaseManager dbManager;
    private static String TABLE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //EditTexts Register.
        inputUsername = (EditText)findViewById(R.id.editTextLoginUsername);
        inputPassword = (EditText)findViewById(R.id.editTextLoginPassword);

        //RadioButtons Register,
        radioBtnAdmin = (RadioButton)findViewById(R.id.radioButtonAdmin);
        radioBtnStudent = (RadioButton)findViewById(R.id.radioButtonStudent);
        radioBtnInstructor = (RadioButton)findViewById(R.id.radioButtonInstructor);

        //Buttons Register
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnRegister = (Button)findViewById(R.id.buttonLoginRegister);

        //Database Initialise;
        dbManager = new DatabaseManager(getApplicationContext());

        /* Insert values
        ContentValues contentValues = new ContentValues();
        contentValues.put("id","300935345");
        contentValues.put("firstName","Anand");
        contentValues.put("lastName","Patel");
        contentValues.put("emailId","patelanand9594@gmail.com");
        contentValues.put("password","anand");
        contentValues.put("noOfFiles",0);
        contentValues.put("noOfCourses",6);
        try{
            dbManager.addRow(contentValues,"tbl_student");
            Log.d("Values Entered",null);
            //stockManager.addRow(contentValues1);
            //stockManager.addRow(contentValues2);
        }catch (Exception e){
            Toast.makeText(LoginActivity.this,
                    e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",e.getMessage());
        }*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent
                Intent intent = null;
                if(radioBtnStudent.isChecked())
                {
                    TABLE_NAME = "tbl_student";
                    if(dbManager.checkUser(TABLE_NAME,inputUsername.getText().toString(),inputPassword.getText().toString())) {
                        intent = new Intent(getApplicationContext(), StudentActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Check your Credentials.\n Try Register if you dont have account",Toast.LENGTH_LONG).show();
                }
                else if(radioBtnStudent.isChecked())
                {
                    TABLE_NAME = "tbl_instructor";
                    if(dbManager.checkUser(TABLE_NAME,inputUsername.getText().toString(),inputPassword.getText().toString())) {
                        intent = new Intent(getApplicationContext(), InstructorActivity.class);
                        startActivity(intent);

                    }
                    else
                        Toast.makeText(getApplicationContext(),"Check your Credentials \n Try Register if you dont have account",Toast.LENGTH_LONG).show();

                }
                else if(radioBtnAdmin.isChecked())
                {
                    TABLE_NAME = "tbl_admin";
                    if(dbManager.checkUser(TABLE_NAME,inputUsername.getText().toString(),inputPassword.getText().toString())) {
                        intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Check your Credentials \n Try Register if you dont have account",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Check your Credentials \n Try Register if you dont have account",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}