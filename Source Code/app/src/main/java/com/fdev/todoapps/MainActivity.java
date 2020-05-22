package com.fdev.todoapps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.fdev.todoapps.data.DatabaseHandler;
import com.fdev.todoapps.model.ToDo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Calendar mChoosenDate;

    private DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        db = new DatabaseHandler(this);

        List<ToDo> toDoList = db.getAllToDos();
        for(ToDo todo : toDoList){

            Date date = new Date();
            date.setTime(todo.getAddedDate());
            Date deadlineDate = new Date();
            deadlineDate.setTime(todo.getDeadlineDate());


            System.out.println("==========================");
            System.out.println("id : " + todo.getId());
            System.out.println("Title : " + todo.getTitle());
            System.out.println("Added Date : "   + " : " + dateFormatter.format(date));
            System.out.println("Deadline Date : " + dateFormatter.format(deadlineDate));
            System.out.println("Urgent Level : " + todo.getUrgentLevel());
            System.out.println("Desc : " + todo.getDescription());

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Context context = this;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbalar_menu,menu);
        return true;
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
                View view = getLayoutInflater().inflate(R.layout.pop_input_edit,null);
                if(saveToDo(view)){
                    Toast.makeText(this,"Succes Add To Do",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Fail Add To Do",Toast.LENGTH_LONG).show();
                }
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

    private boolean saveToDo(View view){

        String toDoTitle = mTitleEditText.getText().toString();

        long toDoDeadlineDate = mChoosenDate.getTimeInMillis();

        //setChoosenRadioButton();
        int choosenRadioButton = mUrgentRadioGroup.getCheckedRadioButtonId();
        String toDoUrgent  = ((RadioButton)view.findViewById(choosenRadioButton)).getText().toString();


        String toDoDesc = mDescEditText.getText().toString();

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
