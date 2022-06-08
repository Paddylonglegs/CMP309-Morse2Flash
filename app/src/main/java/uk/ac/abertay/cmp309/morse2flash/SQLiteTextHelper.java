package uk.ac.abertay.cmp309.morse2flash;

//CLASS TAKEN FROM LAB 6 WITH MINOR MODIFICATIONS//
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteTextHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Morse2FlashDB";
    private static final String TABLE_NAME = "MorseMessages";
    private static final String[] COLUMN_NAMES = {"ID", "Text"};

    private static SQLiteTextHelper instance = null;

    // Build a table creation query string
    private String createCreateString(String[] colNames){
        String s = "CREATE TABLE " + TABLE_NAME + " (";
        for (int i = 0; i < colNames.length; i++) {
            s += colNames[i] + " TEXT";
            if(i < colNames.length - 1){
                s+= ", ";
            } else {
                s+= ");";
            }
        }
        return s;
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private SQLiteTextHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    public static SQLiteTextHelper getInstance(Context context){
        if(instance == null){
            instance = new SQLiteTextHelper(context);
        }
        return instance;
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCreateString(COLUMN_NAMES));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    // Saves a single text string to SQLite database, overwriting existing one in row 0
    public void saveText(String text){
        // Check the number of rows
        SQLiteDatabase db = this.getReadableDatabase();
        // Get all table rows
        Cursor result = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);
        int count = 0;
        if(result != null) {
            // See how many entries there are
            count = result.getCount();
            db.close();
            db = getWritableDatabase();
            ContentValues row = new ContentValues();
            // Prepare a row for saving, use 0 as the ID
            row.put(COLUMN_NAMES[0], 0);
            row.put(COLUMN_NAMES[1], text); //store Morse message in database
            // if no rows, INSERT one
            if(count == 0){
                 db.insert(TABLE_NAME, null, row);
            } else { // else update existing one
                db.update(TABLE_NAME, row, "ID = '0'", null);
            }
            db.close();
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    public String loadText(){
        // check the number of rows
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);
        int count = 0;

        // if there is text to load, return it, otherwise return error message
        if(result.getCount() > 0){
            result.moveToPosition(0);
            return result.getString(1);
        } else {
            return "NO TEXT STORED IN DB!";
        }
    }
} //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.
