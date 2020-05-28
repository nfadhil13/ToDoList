package com.fdev.todoapps;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import com.fdev.todoapps.adapter.RecyclerViewToDoAdapter;
import com.fdev.todoapps.data.DatabaseHandler;
import com.fdev.todoapps.model.DateViewModel;
import com.fdev.todoapps.model.ToDo;
import com.fdev.todoapps.util.DateFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    private Button mSaveBtn;


    private EditText mTitleEditText;
    private EditText mDescEditText;

    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mHeaderDateTextView;
    private TextView mHeaderCurrentMonthYearTextView;
    private TextView mWarnTextView;


    private RelativeLayout mDateLayoutBtn;
    private RelativeLayout mTimeLayoutBtn;
    private ImageButton mNextWeekBtn;
    private ImageButton mPrefWeekBtn;



    private RadioGroup mUrgentRadioGroup;

    private RecyclerView mToDoRecyclerView;



    private RecyclerViewToDoAdapter mRecyclerViewToDoAdapter;


    private DatePickerDialog datePickerDialog;
    private DateFormatter mDateFormatter;
    private Calendar mChoosenSaveDate;
    private int mChoosenHour;
    private int mChoosenMin;
    private Calendar mCurrentWeek;

    private DatabaseHandler db;

    private List<ToDo> toDosList;

    private List<Long> dateList;

    private long mChoosenShowDate;


    DateViewModel[] mDateViewModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);



        mHeaderCurrentMonthYearTextView = findViewById(R.id.tv_current_month_year);





        mChoosenHour=-1;
        mChoosenMin=-1;

        mHeaderDateTextView = findViewById(R.id.tv_choosenday_title_header);
        mWarnTextView = findViewById(R.id.tv_nothingtodo);


        mNextWeekBtn = findViewById(R.id.header_next_btn);
        mNextWeekBtn.setOnClickListener(this);

        mPrefWeekBtn = findViewById(R.id.header_prev_btn);
        mPrefWeekBtn.setOnClickListener(this);


        mDateFormatter = new DateFormatter();

        db = new DatabaseHandler(this);

        toDosList = new ArrayList<>();


        mToDoRecyclerView = findViewById(R.id.todo_show_recylerview);
        mToDoRecyclerView.setHasFixedSize(true);

        mToDoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewToDoAdapter = new RecyclerViewToDoAdapter(this,toDosList);
        mToDoRecyclerView.setAdapter(mRecyclerViewToDoAdapter);

        mCurrentWeek = Calendar.getInstance();
        mCurrentWeek.set(Calendar.HOUR_OF_DAY, 0);
        mCurrentWeek.set(Calendar.MINUTE, 0);
        mCurrentWeek.set(Calendar.SECOND, 0);
        mCurrentWeek.set(Calendar.MILLISECOND,0);


        mChoosenShowDate = mCurrentWeek.getTimeInMillis();

        int currentDayOfWeek =mCurrentWeek.getFirstDayOfWeek();
        mCurrentWeek.add(Calendar.DATE , -currentDayOfWeek);

        dateList = new ArrayList<>();
        intializeDateView();
        setWeeks(0);
        onDateChange(mChoosenShowDate);

//        List<ToDo> tempDeletingToDo = db.getAllToDos();
//        for(ToDo todo : tempDeletingToDo){
//            db.deleteToDo(todo.getId());
//        }



        }

    private void setWeeks(int add) {
        dateList.clear();

        mCurrentWeek.add(Calendar.DATE , add);


        dateList.add(mCurrentWeek.getTimeInMillis());
        fillDateView(mCurrentWeek.getTime(),0);
        for(int i=0;i<6;i++){
            mCurrentWeek.add(Calendar.DATE,1);
            dateList.add(mCurrentWeek.getTimeInMillis());
            fillDateView(mCurrentWeek.getTime(),i+1);
        }
        mCurrentWeek.add(Calendar.DATE , -6);

        String monthName = new SimpleDateFormat("MMM YYYY").format(mCurrentWeek.getTime());
        mHeaderCurrentMonthYearTextView.setText(monthName);






    }

    private void intializeDateView(){
        mDateViewModels = new DateViewModel[7];

        TextView tempDateTextView;
        TextView tempDayTextView;
        CardView tempCardView;


        tempDateTextView = findViewById(R.id.tv_date_number_header_sunday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_sunday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_sunday);
        mDateViewModels[0] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[0].getDate().setOnClickListener(this);
        mDateViewModels[0].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_monday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_monday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_monday);
        mDateViewModels[1] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[1].getDate().setOnClickListener(this);
        mDateViewModels[1].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_tuesday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_tuesday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_tuesday);
        mDateViewModels[2] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[2].getDate().setOnClickListener(this);
        mDateViewModels[2].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_wednesday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_wednesday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_wednesday);
        mDateViewModels[3] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[3].getDate().setOnClickListener(this);
        mDateViewModels[3].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_thursday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_thursday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_thursday);
        mDateViewModels[4] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[4].getDate().setOnClickListener(this);
        mDateViewModels[4].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_friday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_friday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_friday);
        mDateViewModels[5] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[5].getDate().setOnClickListener(this);
        mDateViewModels[5].getCardView().setOnClickListener(this);

        tempDateTextView = findViewById(R.id.tv_date_number_header_saturday);
        tempDayTextView = findViewById(R.id.tv_day_intial_header_saturday);
        tempCardView = findViewById(R.id.header_date_cardView_bg_saturday);
        mDateViewModels[6] = new DateViewModel(tempDateTextView,tempDayTextView,tempCardView);
        mDateViewModels[6].getDate().setOnClickListener(this);
        mDateViewModels[6].getCardView().setOnClickListener(this);




    }

    private void fillDateView(Date date , int position){

        String tempDayIntial = mDateFormatter.getDayName(date).substring(0,1);
        String tempDate = mDateFormatter.getOnlyDate(date);

        mDateViewModels[position].getDate().setText(tempDate);
        mDateViewModels[position].getDayInitial().setText(tempDayIntial);

        if(date.getTime() == mChoosenShowDate){
            mDateViewModels[position].getCardView().setAlpha(1);
            mDateViewModels[position].getDate().setTextColor(Color.parseColor("#425195"));
        }else{
            mDateViewModels[position].getDate().setTextColor(Color.WHITE);
            mDateViewModels[position].getCardView().setAlpha(0.5f);
        }
    }





    @Override
    public void onClick(View v) {
        int clickedID = v.getId();

        switch (clickedID){
            case R.id.relativelayout_date_input_edit:
                showDateDialog();
                break;

            case R.id.fab:
                creatPopupDialog();
                break;

            case R.id.btn_save:
                saveToDo(v);
                break;

            case R.id.header_next_btn:
                setWeeks(7);
                break;

            case R.id.header_prev_btn:
                setWeeks(-7);
                break;

            case R.id.relativelayout_time_input_edit:
                showTimeDialog(v);
                break;

        }

        if(v instanceof CardView || v instanceof TextView){
            int clickedPosition = getPositionDateView(clickedID);
            Log.d("ClickedPosition" , "Yang di clikc: " + clickedPosition);
            if(clickedPosition!= -1){
                mDateViewModels[clickedPosition].getCardView().setAlpha(1);
                mDateViewModels[clickedPosition].getDate().setTextColor(Color.parseColor("#425195"));

                int lastPosition = dateList.indexOf(mChoosenShowDate);
                if(lastPosition != -1){
                    mDateViewModels[lastPosition].getDate().setTextColor(Color.WHITE);
                    mDateViewModels[lastPosition].getCardView().setAlpha(0.5f);
                }

                onDateChange(dateList.get(clickedPosition));
            }
        }
    }

    private int getPositionDateView(int id){
        if(id == R.id.header_date_cardView_bg_sunday || id== R.id.tv_date_number_header_sunday ){
            return 0;
        }
        if(id == R.id.header_date_cardView_bg_monday || id== R.id.tv_date_number_header_monday){
            return 1;
        }
        if(id == R.id.header_date_cardView_bg_tuesday || id== R.id.tv_date_number_header_tuesday){
            return 2;
        }
        if(id == R.id.header_date_cardView_bg_wednesday || id== R.id.tv_date_number_header_wednesday){
            return 3;
        }
        if(id == R.id.header_date_cardView_bg_thursday || id== R.id.tv_date_number_header_thursday){
            return 4;
        }
        if(id == R.id.header_date_cardView_bg_friday || id== R.id.tv_date_number_header_friday){
            return 5;
        }
        if(id == R.id.header_date_cardView_bg_saturday|| id== R.id.tv_date_number_header_saturday){
            return 6;
        }
        return -1;
    }





    private void creatPopupDialog() {


        mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_input_edit,null);

        mTitleEditText = view.findViewById(R.id.et_title_input_edit);



        mDateTextView = view.findViewById(R.id.tv_date_input_edit_result);
        mDateLayoutBtn = view.findViewById(R.id.relativelayout_date_input_edit);
        mDateLayoutBtn.setOnClickListener(this);

        mTimeTextView = view.findViewById(R.id.tv_time_input_edit_result);
        mTimeLayoutBtn = view.findViewById(R.id.relativelayout_time_input_edit);
        mTimeLayoutBtn.setOnClickListener(this);

        mUrgentRadioGroup = view.findViewById(R.id.radio_group_urgent);




        mSaveBtn = view.findViewById(R.id.btn_save);
        mSaveBtn.setOnClickListener(this);

        mBuilder.setView(view);

        mDialog = mBuilder.create();
        mDialog.show();

    }

    private void saveToDo(View parentView){
        if(!mTitleEditText.getText().toString().isEmpty()){
            if(mChoosenSaveDate != null){
                if(mChoosenHour!=-1 && mChoosenMin!=-1){
                    View view = getLayoutInflater().inflate(R.layout.pop_input_edit,null);
                    if(saveToDotoDB(view)){

                        Toast.makeText(this,"Succes Add To Do",Toast.LENGTH_LONG).show();
                        mChoosenSaveDate = null;
                        mChoosenMin = -1;
                        mChoosenHour=-1;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },500);

                    }else{
                        Toast.makeText(this,"Fail Add To Do",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Snackbar.make(parentView,"Choose Valid Time" , Snackbar.LENGTH_LONG).show();
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



        long toDoDeadlineDate = mChoosenSaveDate.getTimeInMillis();

        //setChoosenRadioButton();
        int choosenRadioButton = mUrgentRadioGroup.getCheckedRadioButtonId();
        String toDoUrgent  = ((RadioButton)view.findViewById(choosenRadioButton)).getText().toString();





        ToDo newToDo = new ToDo();
        newToDo.setTitle(toDoTitle);
        newToDo.setDeadlineDate(toDoDeadlineDate);
        newToDo.setHour(mChoosenHour);
        newToDo.setMinute(mChoosenMin);
        newToDo.setUrgentLevel(toDoUrgent);
        if(newToDo.getDeadlineDate()==mChoosenShowDate){
            toDosList.add(newToDo);
            mRecyclerViewToDoAdapter.notifyItemInserted(toDosList.size()-1);
            mWarnTextView.setVisibility(View.INVISIBLE);
        }
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


                if(!newDate.before(newCalendar)){
                    /**
                     * Update TextView with choosen date
                     */
                    newDate.set(Calendar.HOUR_OF_DAY, 0);
                    newDate.set(Calendar.MINUTE, 0);
                    newDate.set(Calendar.SECOND, 0);
                    newDate.set(Calendar.MILLISECOND,0);
                    mDateTextView.setTextColor(Color.BLACK);
                    String day = mDateFormatter.getDayName(newDate.getTime());
                    String date = mDateFormatter.getOnlyDate(newDate.getTime());
                    String month = mDateFormatter.getMonth(newDate.getTime());
                    mDateTextView.setText(day + " " + date + "," + month);
                    mChoosenSaveDate = newDate;
                }else{
                    mDateTextView.setText("Deadline has to be at leats same as current date");
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

    private void showTimeDialog(View v){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                tempCalendar.set(Calendar.MINUTE,selectedMinute);
                mTimeTextView.setText(mDateFormatter.getHourMinute(tempCalendar.getTime()));
                mChoosenHour = selectedHour;
                mChoosenMin = selectedMinute;
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }



    public void onDateChange(long clickeDate) {
            mChoosenShowDate = clickeDate;
            Date tempDate = new Date(mChoosenShowDate);
        String monthName = mDateFormatter.getMonthYear(tempDate);
        String dayName = mDateFormatter.getDayName(tempDate);
        String dateNumber = mDateFormatter.getOnlyDate(tempDate);

            String fullDate = dayName + " " + dateNumber + ","+monthName;

            mHeaderDateTextView.setText(fullDate);

            Log.d("To Do Before"  , "  " + toDosList.size());
            toDosList.clear();
            toDosList.addAll(db.getToDosByDate(mChoosenShowDate));
            Log.d("To Do After"  , "  " + toDosList.size());
            mRecyclerViewToDoAdapter.notifyDataSetChanged();
            if(toDosList.isEmpty()){
                mWarnTextView.setVisibility(View.VISIBLE);
            }else{
                mWarnTextView.setVisibility(View.INVISIBLE);
            }


    }
}
