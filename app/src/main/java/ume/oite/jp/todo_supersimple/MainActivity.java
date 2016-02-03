package ume.oite.jp.todo_supersimple;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * MainActivity
 * ここがすべての処理のハジマリ
 */
public class MainActivity extends AppCompatActivity {

    //レイアウトファイルにあるViewたちの変数
    EditText nameEdit = null;
    Button createButton = null;
    LinearLayout itemLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //レイアウトファイルからそれぞれ取得
        nameEdit = (EditText)this.findViewById(R.id.nameEdit);
        createButton = (Button)this.findViewById(R.id.createButton);
        itemLayout = (LinearLayout)this.findViewById(R.id.itemLayout);

        //ボタンのリスナ設定
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //データの挿入
                db_insert();
                //データの再表示
                createItem();
            }
        });

        //アイテムを作り直す
        createItem();

    }


    //データベースにデータの挿入を行う
    public void db_insert(){

        //書込み用DBとして開く
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();

        //挿入するデータの作成
        ContentValues value = new ContentValues();
        value.put("name",nameEdit.getText().toString());
        value.put("finish",0);

        //todoTableへデータの挿入
        db.insert("todoTable",null,value);

        //データベースを閉じる
        db.close();
    }

    /**
     * createItemメソッド
     * データ表示のためのアイテムの作成をし、表示をする。
     * 実行されると、一旦すべてのアイテムが消え、データベースの内容に従い
     * すべて作り直される。
     *
     * ここでのアイテムの意味は、
     * データ表示用のTextViewのこと。
     */
    public void createItem(){

        //アイテムの各パラメータ
        String name = "";
        int finish = 0;
        int id = 0;

        //アイテム表示用のLayoutの全ての子Viewの削除
        itemLayout.removeAllViews();

        //読込用データベースを開く
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();

        //データ読込用のCursorオブジェクトを作成
        //rawQueryメソッドで、取得したデータを一行ずつ参照できるようにする。
        //rawQueryメソッドの第一引数でselectを使ったSQLを実行する。
        Cursor c = db.rawQuery("select * from todoTable", null);

        //Cursorを使ったデータの一行ずつの取得のループ
        while(c.moveToNext()){

            //表示するTextViewの作成
            TextView tv = new TextView(this);
            tv.setTextSize(30);

            //表示するパラメータの設定
            id = c.getInt(0);
            name = c.getString(1);
            finish = c.getInt(2);

            //TextViewにテキストの設定
            tv.setText(id + " : " + name + " : " + ((finish==0)?"未完了":"完了"));

            //リスナ内でidを使用するためにfinalで宣言
            final int finalId = id;

            //TextViewを長くタッチした時のリスナ
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SQLiteDatabase db = new DatabaseHelper(MainActivity.this).getWritableDatabase();
                    db.delete("todoTable","id=="+ finalId,null);
                    db.close();
                    createItem();
                    return true;
                }
            });

            //TextViewを普通にタッチした時のリスナ
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = new DatabaseHelper(MainActivity.this).getWritableDatabase();
                    ContentValues value = new ContentValues();
                    value.put("finish",1);
                    db.update("todoTable", value,"id=="+finalId, null);
                    db.close();
                    createItem();
                }
            });



            itemLayout.addView(tv);
        }
        db.close();

    }






}
