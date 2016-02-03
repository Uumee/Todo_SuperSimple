package ume.oite.jp.todo_supersimple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelperクラス
 * ここは、SQLiteOpenHelperを継承したヘルパークラス
 * データベースの作成や、それに関連したバージョン管理をする場所。
 * バージョン管理とは、アプリの更新時、データベースの主にテーブルに新たなカラムを作成したい時などに用いるよ。
 * たぶん、テーブルを削除（Drop）してもっかい作るような処理が想定されてる（onCreate）。
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