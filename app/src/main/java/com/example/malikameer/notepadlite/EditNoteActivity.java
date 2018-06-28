package com.example.malikameer.notepadlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent intent=getIntent();
        final String mTitle=intent.getStringExtra("Title");
        int id=intent.getIntExtra("Id",1);
        Toolbar toolbar=findViewById(R.id.toolbarEditNote);
        final EditText editText=findViewById(R.id.editTextEditNote);
        if(id==2)
            editText.setText(intent.getStringExtra("Description"));
        toolbar.setTitle(mTitle);
        Button btnSave=new Button(this);
        btnSave.setBackgroundColor(Color.GREEN);
        btnSave.setText("Save");
        CheckBox checkBox=new CheckBox(this);
        checkBox.setText("Set Reminder");
        Toolbar.LayoutParams params=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity=Gravity.END;
        toolbar.addView(btnSave,params);
        toolbar.addView(checkBox,params);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date= new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM.dd 'at' HH:mm:ss z");
                simpleDateFormat.format(date);
                SQLDatabase sqLiteDatabase=new SQLDatabase(EditNoteActivity.this);
                sqLiteDatabase.saveData(mTitle,editText.getText().toString());
                Toast.makeText(EditNoteActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
