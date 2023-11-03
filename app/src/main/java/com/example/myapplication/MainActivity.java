package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText e1, e2, e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editTextText2);
        e2 = findViewById(R.id.editTextText3);
        e3 = findViewById(R.id.editTextText4);
    }

    public void insert(View v){
        db = openOrCreateDatabase("empData", MODE_PRIVATE, null);
        if(db.isOpen()){
            String query = "create table if not exists emp(name varchar(20), email varchar(25), phone varchar(10) )";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(),"table created", Toast.LENGTH_LONG).show();
            String name = e1.getText().toString();
            String email = e2.getText().toString();
            String phone = e3.getText().toString();

            String insertqry = "insert into emp values('"+name+"','"+email+"','"+phone+"')";

            try{
                db.execSQL(insertqry);
                Toast.makeText(this, "data inserted", Toast.LENGTH_LONG).show();
            }catch(Exception e){
                Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"error creating table", Toast.LENGTH_LONG).show();
        }
    }

    public void reset(View v){
        e1.getText().clear();
        e2.getText().clear();
        e3.getText().clear();
    }

    public void view(View v){
        FrameLayout rl = findViewById(R.id.frame);
        TextView tv= new TextView(this);


        db = openOrCreateDatabase("empData", MODE_PRIVATE, null);
        try{
            String viewQuery = "SELECT * FROM emp";
            Cursor c = db.rawQuery(viewQuery,null);
            if(c.getCount()==0){
                Toast.makeText(getApplicationContext(), "No record",Toast.LENGTH_LONG).show();
            }
            else{
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("Empname:"+c.getString(0)+"\n");
                    buffer.append("Email:"+c.getString(1)+"\n");
                    buffer.append("Phone"+c.getString(2)+"\n\n");
                }
                tv.setText(buffer.toString());
                rl.addView(tv);
            }

        }catch(Exception e){
            Toast.makeText(this,String.valueOf(e),Toast.LENGTH_LONG).show();
        }


    }


}