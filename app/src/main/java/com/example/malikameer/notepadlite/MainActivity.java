package com.example.malikameer.notepadlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener ,View.OnClickListener,Adapter.OnRecyclerViewItemClick {
List<Model> mList=new ArrayList<>();
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(MainActivity.this,EditNoteActivity.class);
        Toolbar toolbar=findViewById(R.id.toolbarMainActivity);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionButton);
        readData();
        Adapter adapter=new Adapter(mList,this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.setOnItemClickListner(this);
        floatingActionButton.setOnClickListener(this);
    }
    public void readData(){
        Model data=new Model();
        SQLDatabase database=new SQLDatabase(this);
        Cursor cursor=database.retrieveData();
        while (cursor.moveToNext()){
            data.setTitle(cursor.getString(1));
            data.setDescription(cursor.getString(2));
            mList.add(data);
        }
        cursor.close();
    }
    public void openNote(int position,String title,String description){
        intent.putExtra("Title",title);
        intent.putExtra("Description",description);
        startActivity(intent);
    }
    public void addNewNote(){
        showTitleDialog();
    }
    public void showTitleDialog(){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Enter title");
        final EditText editText=new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        ));
        alertDialog.setView(editText);
        showKeyboard(editText);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mTitle=editText.getText().toString();
                if(!mTitle.equals("")) {
                    intent.putExtra("Title", mTitle);
                    intent.putExtra("ID",1);
                    intent.putExtra("Id",2);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Unable to save empty note", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    public void showKeyboard(EditText editText){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "Opening Setting", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.floatingActionButton:
                addNewNote();
                break;
        }
    }

    @Override
    public void OnClick(int position, TextView txtTitle, TextView txtDescription) {
        openNote(position,txtTitle.getText().toString(),txtDescription.getText().toString());
    }
}
