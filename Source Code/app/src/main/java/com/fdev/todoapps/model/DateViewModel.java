package com.fdev.todoapps.model;

import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class DateViewModel {

    TextView date;
    TextView dayInitial;
    CardView cardView;

    public DateViewModel() {
    }

    public DateViewModel(TextView date, TextView dayInitial, CardView cardView) {

        this.date = date;
        this.dayInitial = dayInitial;
        this.cardView = cardView;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getDayInitial() {
        return dayInitial;
    }

    public void setDayInitial(TextView dayInitial) {
        this.dayInitial = dayInitial;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
