package com.example.anandpatelak.lims_project; /**
 * Created by anandpatelak on 2018-02-25.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anandpatelak on 2018-01-11.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    //database name and version
    private static final String DATABASE_NAME = "LIMSDB";
    private static final int DATABASE_VERSION = 1;
    // table name and table creator string (SQL statement to create the table)
    // should be set from within main activity
    private static String tableNames[] = {"tbl_student","tbl_instructor","tbl_course","tbl_admin"};
    private static String tableCreatorStrings[] = {

            //Student table
            "CREATE TABLE tbl_student (id TEXT PRIMARY KEY" +
                    ", firstName TEXT" +
                    ", lastName TEXT ," +
                    " emailId TEXT," +
                    " password TEXT," +
                    " noOfFiles INTEGER," +
                    " noOfCourses INTEGER);",

            //Instructor table
            "CREATE TABLE tbl_instructor (id TEXT PRIMARY KEY" +
                    ", firstName TEXT" +
                    ", lastName TEXT ," +
                    " emailId TEXT," +
                    " password TEXT," +
                    " noOfCourses INTEGER);," ,

            //Admin Table
            "CREATE TABLE tbl_admin (id TEXT PRIMARY KEY" +
                    ", firstName TEXT" +
                    ", lastName TEXT ," +
                    " emailId TEXT," +
                    " password TEXT);",

            //Course Table
            "CREATE TABLE tbl_course (courseId TEXT PRIMARY KEY" +
                    ",courseName TEXT ," +
                    " emailId TEXT," +
                    " instructor TEXT);"
    };
    //
    // no-argument constructor
    public DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    // Called when the database is created for the first time.
    // This is where the creation of tables and the initial population
    // of the tables should happen.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table
        for(int i= 0  ;i<tableCreatorStrings.length;i++)
        db.execSQL(tableCreatorStrings[i]);
    }
    //
    // Called when the database needs to be upgraded.
    // The implementation should use this method to drop tables,
    // add tables, or do anything else it needs to upgrade
    // to the new schema version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table if existed
        for(int i= 0  ;i<tableNames.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tableNames[i]);
        //recreate the table
        onCreate(db);
    }
    //
    // initialize table names and CREATE TABLE statement
    // called by activity to create a table in the database
    // The following arguments should be passed:
    // tableName - a String variable which holds the table name
    // tableCreatorString - a String variable which holds the CREATE Table statement
    //
    public void dbInitialize(String tableName, String tableCreatorString)
    {
        for(int i= 0  ;i<tableCreatorStrings.length;i++) {
            this.tableNames[i] = tableNames[i];
            this.tableCreatorStrings[i] = tableCreatorStrings[i];
        }
    }

    //
    // CRUD Operations
    //
    //This method is called by the activity to add a row in the table
    // The following arguments should be passed:
    // values - a ContentValues object that holds row values
    /*public boolean addRow  (ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert the row
        long nr= db.insert(tableNames[i], null, values);
        db.close(); //close database connection
        return nr> -1;
    }*/
    // This method returns a stock object which holds the table row with the given id
    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field

    //Three different users
    //User: Student
    public Student getStudentById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + tableNames[0] + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Student student = new Student(); //create a new Student object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of stock object
            student.setID(cursor.getString(0));
            student.setFirstName(cursor.getString(1));
            student.setLastName(cursor.getString(2));
            student.setPassword(cursor.getString(3));
            student.setEmailID(cursor.getString(4));
            student.setInstructor(cursor.getString(5));
            cursor.close();

        } else {
            student = null;
        }
        db.close();
        return student;

    }

    //User: Instructor
    public Instructor getInstructorById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + tableNames[1] + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Instructor instructor = new Instructor(); //create a new Student object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of stock object
            instructor.setID(cursor.getString(0));
            instructor.setFirstName(cursor.getString(1));
            instructor.setLastName(cursor.getString(2));
            instructor.setPassword(cursor.getString(3));
            instructor.setEmailID(cursor.getString(4));
            instructor.setDepartment(cursor.getString(5));
            cursor.close();

        } else {
            instructor = null;
        }
        db.close();
        return instructor;

    }

    //User: Admin
    public Admin getAdminById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + tableNames[2] + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Admin admin = new Admin(); //create a new Student object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of stock object
            admin.setID(cursor.getString(0));
            admin.setFirstName(cursor.getString(1));
            admin.setLastName(cursor.getString(2));
            admin.setPassword(cursor.getString(3));
            admin.setEmailID(cursor.getString(4));

            cursor.close();

        } else {
            admin = null;
        }
        db.close();
        return admin;

    }

    public Course getCourseById(Object id, String fieldName) throws Exception{
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + tableNames[3] + " where "+ fieldName + "='"+String.valueOf(id)+"'", null );
        Course course = new Course(); //create a new Student object
        if (cursor.moveToFirst()) { //if row exists
            cursor.moveToFirst(); //move to first row
            //initialize the instance variables of stock object
            course.setCourseId(cursor.getString(0));
            course.setCourseName(cursor.getString(1));
            course.setCourseInstructor(cursor.getString(2));
            cursor.close();

        } else {
            course = null;
        }
        db.close();
        return course;

    }
    //
    // The following argument should be passed:
    // id - an Object which holds the primary key value
    // fieldName - the  name of the primary key field
    // values - a ContentValues object that holds row values
    //
    /*public boolean editRow (Object id, String fieldName, ContentValues values) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        //
        int nr = db.update(tableNames[i], values, fieldName + " = ? ", new String[]{String.valueOf(id)});
        return nr > 0;
    }*/

} //end of ScheduleManager

