package com.example.faisalalhuwaishel200118project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    public static final String TABLE_NAME = "students_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "FIRST_NAME";
    public static final String COL3 = "FATHER_NAME";
    public static final String COL4 = "SURNAME";
    public static final String COL5 = "NATIONALID";
    public static final String COL6 = "DATE";
    public static final String COL7 = "GENDER";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //change columns if needed
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID TEXT PRIMARY KEY, " +
                " FIRST_NAME TEXT, FATHER_NAME TEXT," +
                " SURNAME TEXT, NATIONALID TEXT," +
                " DATE TEXT, GENDER TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String id,String firstName, String fatherName,
                           String surname, String nationalID,
                           String date, String gender
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, firstName);
        contentValues.put(COL3, fatherName);
        contentValues.put(COL4, surname);
        contentValues.put(COL5, nationalID);
        contentValues.put(COL6, date);
        contentValues.put(COL7, gender);

        long result = db.insert(TABLE_NAME, null, contentValues);
        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    /* Returns only one result */
    public Cursor structuredQuery(String ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3,COL4, COL5,COL6, COL7}, COL1 + "=?",
                new String[]{ID}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor getSpecificResult(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" where ID="+ID,null);
        return data;
    }

    // Return everything inside the dB
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    /*public Cursor getSpecificProduct(String productName){
        Log.d("MyAndroid","DB connection started");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME+" where PRODUCT_NAME=\""+productName+"\"",
                null);
        data.moveToFirst();
        if(data.getCount()!=0){
            String dataID = data.getString(0); //ID
            String dataName = data.getString( 1); // PRODUCT_NAME
            String dataQuantity = data.getString(2); // Quantity
            Log.d( "George", "dataID:"+dataID);
            Log.d( "George","dataName: "+dataName);
            Log.d("George","dataQuantity:"+dataQuantity);
            return data;
        }
        else{
            return null;
        }
    }*/
    //deletes the product with the "ID" id
    public int deleteSpecificProduct(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,COL1+" = ?",new String[]{ID});
    }

    public boolean updateSpecificStudent(String ID,String key,String newValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(key,newValue);
        long result = db.update(TABLE_NAME,contentValues,COL1 + "=?", new String[]{ID});
        if(result==-1) {return false;} else {return true;}
        /*Log.d("Faisal","UPDATE "+TABLE_NAME+" SET "+key+" = \""+newValue+"\" where ID=\""+ID+"\"");
        db.rawQuery(
                "UPDATE "+TABLE_NAME+" SET "+key+" = \""+newValue+"\" where ID=\""+ID+"\"",
                null);*/
    }

    public boolean insertWithUpdate(String id,String firstName, String fatherName,
                                    String surname, String nationalID,
                                    String date, String gender
    ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,firstName);
        contentValues.put(COL3,fatherName);
        contentValues.put(COL4,surname);
        contentValues.put(COL5,nationalID);
        contentValues.put(COL6,date);
        contentValues.put(COL7,gender);
        long result = db.update(TABLE_NAME,contentValues,COL1 + "=?", new String[]{id});
        if(result==-1) {return false;} else {return true;}
    }


}

