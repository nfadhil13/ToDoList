package com.fdev.todoapps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import com.fdev.todoapps.adapter.RecycleViewAdapter;
import com.fdev.todoapps.data.DatabaseHandler;
import com.fdev.todoapps.model.ToDo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    private Button mSaveBtn;


    private EditText mTitleEditText;
    private EditText mDescEditText;

    private TextView mDateTextView;

    private ImageButton mDateBtn;

    private RadioGroup mUrgentRadioGroup;
    private RadioButton mUrgentRadioButton;
    private RadioButton mVeryUrgentRadioButton;
    private RadioButton mSoSoRadioButton;
    private RadioButton mNotImportantRadioButton;

    private RecyclerView mRecycleView;

    private RecycleViewAdapter mRecyclerViewAdapter;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar mChoosenDate;

    private DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mRecycleView = findViewById(R.id.todo_recyleview);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        db = new DatabaseHandler(this);

        mRecyclerViewAdapter = new RecycleViewAdapter(this,db.getAllToDos());

        mRecycleView.setAdapter(mRecyclerViewAdapter);

        }



    private void refreshList(){
        mRecyclerViewAdapter = new RecycleViewAdapter(this,db.getAllToDos());

        mRecycleView.setAdapter(mRecyclerViewAdapter);

    }



    @Override
    public void onClick(View v) {
        int clickedID = v.getId();

        switch (clickedID){
            case R.id.btn_input_edit_date:
                showDateDialog();
                break;

            case R.id.fab:
                creatPopupDialog();
                break;

            case R.id.btn_save:
                saveToDo(v);
                break;


        }
    }



    private void creatPopupDialog() {


        mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_input_edit,null);

        mTitleEditText = view.findViewById(R.id.et_title_input_edit);



        mDateTextView = view.findViewById(R.id.tv_date_input_edit_result);
        mDateBtn = view.findViewById(R.id.btn_input_edit_date);
        mDateBtn.setOnClickListener(this);


        mUrgentRadioGroup = view.findViewById(R.id.radio_group_urgent);


        mDescEditText =view.findViewById(R.id.et_description_input_edit);


        mSaveBtn = view.findViewById(R.id.btn_save);
        mSaveBtn.setOnClickListener(this);

        mBuilder.setView(view);

        mDialog = mBuilder.create();
        mDialog.show();

    }

    private void saveToDo(View parentView){
        if(!mTitleEditText.getText().toString().isEmpty()){
            if(mChoosenDate != null){
                View view = getLayoutInflater().inflate(R.layout.pop_input_edit,null);
                if(saveToDotoDB(view)){
                    Toast.makeText(this,"Succes Add To Do",Toast.LENGTH_LONG).show();
                    mChoosenDate = null;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            //refreshList();

                        }
                    },1000);

                }else{
                    Toast.makeText(this,"Fail Add To Do",Toast.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(parentView,"Choose Valid Date" , Snackbar.LENGTH_LONG).show();
            }
        }else{
            Snackbar.make(parentView,"Title not allowed to be empty" , Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean saveToDotoDB(View view){

        String toDoTitle = mTitleEditText.getText().toString();



        long toDoDeadlineDate = mChoosenDate.getTimeInMillis();

        //setChoosenRadioButton();
        int choosenRadioButton = mUrgentRadioGroup.getCheckedRadioButtonId();
        String toDoUrgent  = ((RadioButton)view.findViewById(choosenRadioButton)).getText().toString();


        String toDoDesc;
        if(mDescEditText.getText().toString().isEmpty()){
            toDoDesc= "";
        }else{
            toDoDesc = mDescEditText.getText().toString();
        }


        ToDo newToDo = new ToDo();
        newToDo.setTitle(toDoTitle);
        newToDo.setDeadlineDate(toDoDeadlineDate);
        newToDo.setUrgentLevel(toDoUrgent);
        newToDo.setDescription(toDoDesc);

        return db.addToDo(newToDo);

    }




    private void showDateDialog(){
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        final Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                newDate.set(Calendar.HOUR, 0);
                newDate.set(Calendar.MINUTE, 0);
                newDate.set(Calendar.SECOND, 0);
                newDate.set(Calendar.MILLISECOND,0);

                if(newDate.after(newCalendar)){
                    /**
                     * Update TextView with choosen date
                     */
                    mDateTextView.setTextColor(Color.BLACK);
                    mDateTextView.setText("Choosen Date : "+dateFormatter.format(newDate.getTime()));
                    mChoosenDate = newDate;
                }else{
                    mDateTextView.setText("Deadline has to be after current date");
                    mDateTextView.setTextColor(Color.RED);
                    Toast.makeText(view.getContext(),"invalid choosen date",
                            Toast.LENGTH_LONG).show();
                }


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }


}
