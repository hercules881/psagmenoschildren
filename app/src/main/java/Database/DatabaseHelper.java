package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by h.lionakis on 13/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "psagmenos";

    // Contacts table name
    public static final String TABLE_QUESTIONS = "QUESTIONS";
    public static final String TABLE_ANSWERS = "ANSWERS";

    // Contacts Table Columns names
    public static final String KEY_ID_QUESTION  = "questionid";
    public static final String KEY_QUESTION  = "question";
    public static final String KEY_CATEGORY = "category";

    public static final String KEY_ID_ANSWER = "answerid";
    public static final String KEY_QUESTION_ID = "idquestion";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_VALID_ANSWER = "validanswer";
    // private static final String KEY_IMAGEPATH = "image_path";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_ID_QUESTION  + " INTEGER PRIMARY KEY,"+ KEY_QUESTION + " TEXT,"
                + KEY_CATEGORY +" TEXT" + ")";
        String CREATE_ANSWERS_TABLE = "CREATE TABLE " + TABLE_ANSWERS + "("
                + KEY_ID_ANSWER  + " INTEGER PRIMARY KEY,"+ KEY_QUESTION_ID + " INTEGER,"
                + KEY_ANSWER +" TEXT," + KEY_VALID_ANSWER +" INTEGER" + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_ANSWERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public  void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_QUESTION, question.getQuestionId());
        values.put(KEY_QUESTION, question.getText()); // Contact Name
        values.put(KEY_CATEGORY, question.getCategory()); // Contact Phone
        // values.put(KEY_IMAGEPATH,user.getImageUrl());
        // Inserting Row
        db.insert(TABLE_QUESTIONS, null, values);
        db.close(); // Closing database connection
    }

    public  void addAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_ANSWER, answer.getAnswerId());
        values.put(KEY_ID_QUESTION, answer.getQuestionId()); // Contact Name
        values.put(KEY_ANSWER, answer.getText()); // Contact Phone
        values.put(KEY_VALID_ANSWER, answer.getIsValidAnswer()); // Contact Phone
        // values.put(KEY_IMAGEPATH,user.getImageUrl());
        // Inserting Row
        db.insert(TABLE_ANSWERS, null, values);
        db.close(); // Closing database connection
    }



    // Getting single contact



}
