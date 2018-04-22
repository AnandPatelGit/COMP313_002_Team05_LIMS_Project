package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorFeedbackActivity extends AppCompatActivity {

    TextView fileName, studentName, studentEmail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Spinner spin;
    DatabaseReference databaseFeedback;
    EditText score, comments;
    String fileRef,fileId;
    Button btnSubmit;
    String[] grade = { "A+", "A", "B+", "B", "C+","C", "D+", "D", "E", "F"  };      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_feedback);
        fileName = (TextView) findViewById(R.id.tvFileName);
        studentEmail = (TextView) findViewById(R.id.tvStudentEmail);
        score = (EditText) findViewById(R.id.editText);
        comments = (EditText) findViewById(R.id.editText2);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        btnSubmit = (Button) findViewById(R.id.btnFeedback);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeedback();
            }
        });

       spin = (Spinner) findViewById(R.id.spinnerGrades);


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,grade);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        if(b!=null)
        {
            String j =(String) b.get("fileName");
            String h =(String) b.get("folderName");
            fileName.setText(h+"/"+j);
            fileRef ="Folders/"+h+"/"+j;

        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("files");


        databaseReference.orderByChild("fileReference").equalTo(fileRef).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String se = dataSnapshot.child("email").getValue(String.class);
                studentEmail.setText(se);
                fileId = dataSnapshot.child("email").getValue(String.class);

                //arrayList.add(value);
                //arrayAdapter = new ArrayAdapter<String>(InstructorAvailableSubjects.this, android.R.layout.simple_list_item_1,arrayList);
                //folderList.setAdapter(arrayAdapter);
                //folderList.setOnItemClickListener(new InstructorAvailableSubjects.FileListClickHandler());
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
    public void addFeedback(){
        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback");

        String id = databaseFeedback.push().getKey();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String sEmail = studentEmail.getText().toString().trim();
        String grade = spin.getSelectedItem().toString().trim();
        String strScore = score.getText().toString().trim();
        String strComment = comments.getText().toString();
        String strFileId = fileId;
        String strFileRef = fileRef;
        Feedback feedback = new Feedback(id, email,sEmail,grade,strScore,strComment,strFileId,strFileRef);

        if(strScore.isEmpty())
        {
            score.setError("Please give a score");
            score.findFocus();
        }
        else if(strComment.isEmpty()){
            comments.setError("Please give a comment");
            comments.findFocus();
        }
        else{
            databaseFeedback.child(id).setValue(feedback);
            Toast.makeText(this, "Feedback submitted",Toast.LENGTH_LONG).show();
        }

    }

}
