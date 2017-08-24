package com.toong.androidgroupinputview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.text.ParseException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private GroupInputView ivDate;
    private GroupInputView ivDateTime;

    private Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = (Button) findViewById(R.id.button_test);
        ivDate = (GroupInputView) findViewById(R.id.input_view_date);
        ivDateTime = (GroupInputView) findViewById(R.id.input_view_date_time);

        ivDate.setMaxDate(Calendar.getInstance().getTimeInMillis());

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar calendar = AppUtils.DateTime.dateToCalendar(ivDate.getText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Calendar calendar = AppUtils.DateTime.dateToCalendar(ivDateTime.getText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
