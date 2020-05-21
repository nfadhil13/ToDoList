package com.fdev.todoapps;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;
    private Button mSaveButton;
    private EditText mTitleEditText;
    private TextView mDateTextView;
    private TextView mUrgentTextView;
    private EditText mDescEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creatPopupDialog();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void creatPopupDialog() {


        mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Context context = this;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbalar_menu,menu);
        return true;
    }
}
