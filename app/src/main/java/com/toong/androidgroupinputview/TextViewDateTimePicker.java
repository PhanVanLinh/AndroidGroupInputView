package com.toong.androidgroupinputview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TextViewDateTimePicker implements View.OnClickListener {
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private TextView mView;
    private Context mContext;
    private long mMinDate;
    private long mMaxDate;

    public TextViewDateTimePicker(Context context, TextView view) {
        this(context, view, 0, 0);
    }

    public TextViewDateTimePicker(Context context, TextView view, long minDate, long maxDate) {
        mView = view;
        mView.setOnClickListener(this);
        mView.setFocusable(false);

        mContext = context;
        mMinDate = minDate;
        mMaxDate = maxDate;
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        mDatePickerDialog =
                new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month,
                            final int dayOfMonth) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mTimePickerDialog = new TimePickerDialog(mContext,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                    int minute) {
                                                updateUI(year, month, dayOfMonth, hourOfDay,
                                                        minute);
                                            }
                                        }, hour, minute, false);
                                mTimePickerDialog.show();
                            }
                        }, 100);
                    }
                }, year, month, day);

        if (mMinDate != 0) {
            mDatePickerDialog.getDatePicker().setMinDate(mMinDate);
        }
        if (mMaxDate != 0) {
            mDatePickerDialog.getDatePicker().setMaxDate(mMaxDate);
        }
        mDatePickerDialog.show();
    }

    private void updateUI(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Date date = calendar.getTime();

        SimpleDateFormat formatter =
                new SimpleDateFormat(AppUtils.DateTime.DATE_TIME_SERVER_PATTERN);
        mView.setText(formatter.format(date));
    }

    public DatePickerDialog getDatePickerDialog() {
        return mDatePickerDialog;
    }

    public void setMinDate(long minDate) {
        mMinDate = minDate;
    }

    public void setMaxDate(long maxDate) {
        mMaxDate = maxDate;
    }
}