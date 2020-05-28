package com.fdev.todoapps.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fdev.todoapps.R;
import com.fdev.todoapps.data.DatabaseHandler;
import com.fdev.todoapps.model.ToDo;
import com.fdev.todoapps.util.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerViewToDoAdapter extends RecyclerView.Adapter<RecyclerViewToDoAdapter.ViewHolder> {

    private Context mContext;
    private List<ToDo> mToDoList;
    private DateFormatter dateFormatter;


    public RecyclerViewToDoAdapter(Context context , List<ToDo> mToDoList){
        this.mToDoList = mToDoList;
        this.mContext = context;
        dateFormatter = new DateFormatter();
    }

    @NonNull
    @Override
    public RecyclerViewToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewToDoAdapter.ViewHolder holder, int position) {
        ToDo todo = mToDoList.get(position);

        holder.mTitleTextView.setText(todo.getTitle());
       // holder.mDescTextView.setText(todo.getDescription());

       // holder.mUrgentTextView.setText(todo.getUrgentLevel());
        //holder.mRelativeLayoutBg.setBackgroundColor(mContext.getColor(getColorByUrgent(todo.getUrgentLevel())));
//        Date addeDate = new Date();
//        addeDate.setTime(todo.getAddedDate());
        //holder.mAddedDateTextView.setText("" );

        Date deadlineDate = new Date();
        deadlineDate.setTime(todo.getDeadlineDate());

        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(Calendar.HOUR_OF_DAY,todo.getHour());
        tempCalendar.set(Calendar.MINUTE,todo.getMinute());

        String deadline = dateFormatter.getMonth(deadlineDate) + " "
                                +dateFormatter.getOnlyDate(deadlineDate) +", "
                                +dateFormatter.getHourMinute(tempCalendar.getTime());
        holder.mDeadlineDateTextView.setText(deadline);
        String urgentLevel = todo.getUrgentLevel();

        if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_very_urgent))){
            holder.mImageView.setImageResource(R.drawable.very_urgent);
        }else if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_urgent))){
            holder.mImageView.setImageResource(R.drawable.urgent);
        }else if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_so_so))){
            holder.mImageView.setImageResource(R.drawable.so_so);
        }else{
            holder.mImageView.setImageResource(R.drawable.not_important);
        }

        holder.mUrgentTextView.setText(todo.getUrgentLevel());



    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitleTextView;
//        public TextView mDescTextView;
//        public TextView mAddedDateTextView;
        public TextView mDeadlineDateTextView;
        public TextView mUrgentTextView;
        public ImageView mImageView;
        public ImageButton mDoneBtn;
        public ImageButton mEditBtn;


        // TO change the background when te level of urgent change
 //       public RelativeLayout mRelativeLayoutBg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_title_show);
//            mDescTextView = itemView.findViewById(R.id.tv_desc_show);
//            mAddedDateTextView = itemView.findViewById(R.id.tv_added_date_show);
            mDeadlineDateTextView = itemView.findViewById(R.id.tv_deadline_date_show);
            mUrgentTextView = itemView.findViewById(R.id.tv_urgent_level_Show);
 //           mRelativeLayoutBg = itemView.findViewById(R.id.relativeLayout_cardview_todo);
            mImageView = itemView.findViewById(R.id.imgView_show_image);
            mEditBtn = itemView.findViewById(R.id.btn_edit);
            mDoneBtn = itemView.findViewById(R.id.btn_mark_as_done);
            mEditBtn.setOnClickListener(this);
            mDoneBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position;
            if(v.getId()==R.id.btn_edit){
                Toast.makeText(mContext,"Edit",Toast.LENGTH_LONG).show();

            }else if(v.getId() == R.id.btn_mark_as_done){
                position = getAdapterPosition();
                ToDo tempTodo = mToDoList.get(position);
                deleteItem(tempTodo.getId());
            }
        }

        private void deleteItem(int id){
            DatabaseHandler db = new DatabaseHandler(mContext);
            db.deleteToDo(id);
            mToDoList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());

        }


        private void creatPopupDialog() {


//            mBuilder = new AlertDialog.Builder(mContext);
//            View view = mContext.inflate(R.layout.pop_input_edit,null);
//
//            mTitleEditText = view.findViewById(R.id.et_title_input_edit);
//
//
//
//            mDateTextView = view.findViewById(R.id.tv_date_input_edit_result);
//            mDateLayoutBtn = view.findViewById(R.id.relativelayout_date_input_edit);
//            mDateLayoutBtn.setOnClickListener(this);
//
//            mTimeTextView = view.findViewById(R.id.tv_time_input_edit_result);
//            mTimeLayoutBtn = view.findViewById(R.id.relativelayout_time_input_edit);
//            mTimeLayoutBtn.setOnClickListener(this);
//
//            mUrgentRadioGroup = view.findViewById(R.id.radio_group_urgent);
//
//
//
//
//            mSaveBtn = view.findViewById(R.id.btn_save);
//            mSaveBtn.setOnClickListener(this);
//
//            mBuilder.setView(view);
//
//            mDialog = mBuilder.create();
//            mDialog.show();

        }
    }


}