package com.fdev.todoapps.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.fdev.todoapps.R;
import com.fdev.todoapps.model.ToDo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<ToDo> mToDoList;
    private SimpleDateFormat dateFormatter;


    public RecycleViewAdapter(Context context , List<ToDo> mToDoList){
        this.mToDoList = mToDoList;
        this.mContext = context;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        ToDo todo = mToDoList.get(position);

        holder.mTitleTextView.setText(todo.getTitle());
        holder.mDescTextView.setText(todo.getDescription());

        holder.mUrgentTextView.setText(todo.getUrgentLevel());
        holder.mRelativeLayoutBg.setBackgroundColor(mContext.getColor(getColorByUrgent(todo.getUrgentLevel())));
        Date addeDate = new Date();
        addeDate.setTime(todo.getAddedDate());
        holder.mAddedDateTextView.setText(dateFormatter.format(addeDate));

        Date deadlineDate = new Date();
        deadlineDate.setTime(todo.getDeadlineDate());
        holder.mDeadlineDateTextView.setText(dateFormatter.format(deadlineDate));



    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitleTextView;
        public TextView mDescTextView;
        public TextView mAddedDateTextView;
        public TextView mDeadlineDateTextView;
        public TextView mUrgentTextView;
        public ImageView mImageView;


        // TO change the background when te level of urgent change
        public RelativeLayout mRelativeLayoutBg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_title_show);
            mDescTextView = itemView.findViewById(R.id.tv_desc_show);
            mAddedDateTextView = itemView.findViewById(R.id.tv_added_date_show);
            mDeadlineDateTextView = itemView.findViewById(R.id.tv_deadline_date_show);
            mUrgentTextView = itemView.findViewById(R.id.tv_urgent_level_Show);
            mRelativeLayoutBg = itemView.findViewById(R.id.relativeLayout_cardview_todo);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public int getColorByUrgent(String urgentLevel){

        if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_very_urgent))){
            return R.color.veryUrgent;
        }else if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_urgent))){
            return R.color.urgent;
        }else if(urgentLevel.equals(mContext.getString(R.string.input_edit_level_so_so))){
            return R.color.so_so;
        }else{
            return R.color.not_important;
        }
    }
}
