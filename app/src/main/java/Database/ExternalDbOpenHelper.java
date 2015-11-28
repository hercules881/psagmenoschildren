package Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mixalis.psagmenos.MainActivity;
import com.example.mixalis.psagmenos.RythmiseisActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Misc.ChangeLanguageListener;

/**
 * Created by h.lionakis on 14/11/2015.
 */
public class ExternalDbOpenHelper extends DatabaseHelper implements ChangeLanguageListener {

    //Path to the device folder with databases
    public static String DB_PATH;

    //Database file name
    public static String DB_NAME = "psagmenossschildrendb.db";
    public SQLiteDatabase database;
    public final Context context;
    private static ExternalDbOpenHelper _instance;

    public SQLiteDatabase getDb() {
        return database;
    }

    public ExternalDbOpenHelper(Context context) {
        super(context);
        this.context = context;
        _instance = this;
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        String currentLanguage = preferences.getString(MainActivity.LANGUAGE_KEY,"el");
        ExternalDbOpenHelper.DB_NAME =  currentLanguage.equals("el")?"psagmenossschildrendb.db":"psagmenossschildrenenglishdb.db";

        //Write a full path to the databases of your application
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RythmiseisActivity.setOnChangeLanguageListener(this);
    }

    //This piece of code will create a database if it’s not yet created
    public void createDataBase() {
        File dbFile = context.getDatabasePath(DB_NAME);
        boolean dbExist = dbFile.exists();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    //Performing a database existence check
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        String path = DB_PATH + DB_NAME;
        checkDb = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READONLY);
        //Android doesn’t like resource leaks, everything should
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

        //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

        //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    public Question getQuestion(int questionId) {
        SQLiteDatabase db = database;

        Cursor cursor = db.query(TABLE_QUESTIONS, new String[]{KEY_ID_QUESTION,
                        KEY_QUESTION, KEY_CATEGORY}, KEY_ID_QUESTION + "=?",
                new String[]{(String.valueOf(questionId))}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Question question = new Question((cursor.getInt(0)),cursor.getString(1), (cursor.getString(2)));
        // return contact
        return question;
    }
    public Answer getAnswer(int answerId) {
        SQLiteDatabase db = database;

        Cursor cursor = db.query(TABLE_ANSWERS, new String[]{KEY_ID_ANSWER,
                        KEY_QUESTION_ID, KEY_ANSWER, KEY_VALID_ANSWER}, KEY_ID_ANSWER + "=?",
                new String[]{(String.valueOf(answerId))}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Answer answer = new Answer((cursor.getInt(0)),cursor.getInt(1), (cursor.getString(2)),cursor.getInt(3));
        // return contact

        return answer;
    }

    // Getting All Contacts
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = database;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestionId(cursor.getInt(0));
                question.setText(cursor.getString(1));
                question.setCategory((cursor.getString(2)));
                // Adding contact to list
                questions.add(question);
            } while (cursor.moveToNext());
        }

        // return contact list
        return questions;
    }

    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<Answer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWERS;

        SQLiteDatabase db = database;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setAnswerId(cursor.getInt(0));
                answer.setQuestionId(cursor.getInt(1));
                answer.setText((cursor.getString(2)));
                answer.setIsValidAnswer(cursor.getInt(3));
                // Adding contact to list
                answers.add(answer);
            } while (cursor.moveToNext());
        }
        // return contact list
        return answers;
    }
    // Updating single contact
    public List<Answer> getPossibleAnswersForQuestion(Question question) {
        List<Answer> answers = new ArrayList<Answer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWERS + " WHERE " + KEY_QUESTION_ID + " = "+ String.valueOf(question.getQuestionId());

        SQLiteDatabase db = database;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setAnswerId(cursor.getInt(0));
                answer.setQuestionId(cursor.getInt(1));
                answer.setText((cursor.getString(2)));
                answer.setIsValidAnswer(cursor.getInt(3));
                // Adding contact to list
                answers.add(answer);
            } while (cursor.moveToNext());
        }


        // return contact list
        return answers;
    }

    public List<Question> getQuestionForCategory(String category) {
        List<Question> questions = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS + " WHERE " + KEY_CATEGORY + " = "+"'"+category+"'";

        SQLiteDatabase db = database;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestionId(cursor.getInt(0));
                question.setText(cursor.getString(1));
                question.setCategory((cursor.getString(2)));
                // Adding contact to list
                questions.add(question);
            } while (cursor.moveToNext());
        }


        // return contact list
        return questions;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    //auti edw kaleitai otan allaxtei i glwssa stis rithmiseis kai fortwnei tin analogi vasi
    @Override
    public void onLanguageChange(boolean isGreekLanguage) {
       if( isGreekLanguage)
        DB_NAME = "psagmenossschildrendb.db";
        else
           DB_NAME = "psagmenossschildrenenglishdb.db";
    }


    public static ExternalDbOpenHelper sharedInstance()
    {


        return _instance;

    }
}
