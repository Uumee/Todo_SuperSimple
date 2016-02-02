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

public class MainActivity extends AppCompatActivity {


    EditText nameEdit = null;
    Button createButton = null;
    LinearLayout itemLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText)this.findViewById(R.id.nameEdit);
        createButton = (Button)this.findViewById(R.id.createButton);
        itemLayout = (LinearLayout)this.findViewById(R.id.itemLayout);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_insert();
                createItem();
            }
        });



        createItem();

    }


    public void db_insert(){
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("name",nameEdit.getText().toString());
        value.put("finish",0);
        db.insert("todoTable",null,value);
        db.close();
    }

    public void createItem(){
        String name = "";
        int finish = 0;
        int id = 0;

        itemLayout.removeAllViews();

        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();

        Cursor c = db.rawQuery("select * from todoTable", null);
        while(c.moveToNext()){
            TextView tv = new TextView(this);
            tv.setTextSize(30);
            id = c.getInt(0);
            name = c.getString(1);
            finish = c.getInt(2);
            tv.setText(id + " : " + name + " : " + ((finish==0)?"未完了":"完了"));

            final int finalId = id;
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
