package com.example.anandpatelak.lims_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private final static String TABLE_NAME = "";
    private static final String tableCreatorString =
            "CREATE TABLE "+ TABLE_NAME + " (stockSymbol TEXT PRIMARY KEY, companyName TEXT, stockQuote INTEGER);";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
