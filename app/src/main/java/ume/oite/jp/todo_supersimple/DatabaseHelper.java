package ume.oite.jp.todo_supersimple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ume on 16/2/2.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sample.db";
    private static final String TABLE = "todoTable";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE =
            "create table " + TABLE + "("
                    +"id integer primary key autoincrement,"
                    +"name text not null,"
                    +"finish int );";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}