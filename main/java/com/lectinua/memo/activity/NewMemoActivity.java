package com.lectinua.memo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.lectinua.memo.R;
import com.lectinua.memo.database.DatabaseManager;
import com.lectinua.memo.databinding.ActivityNewMemoBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewMemoActivity extends AppCompatActivity {

    private ActivityNewMemoBinding binding;
    private Animation show, hide;
    private InputMethodManager imm;
    private DatabaseManager databaseManager;

    private String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(NewMemoActivity.this, R.layout.activity_new_memo);
        binding.setActivity(this);

        databaseManager = new DatabaseManager(getApplicationContext(),
                "MY_MEMO.db", null, 1);

        show = AnimationUtils.loadAnimation(this, R.anim.show);
        hide = AnimationUtils.loadAnimation(this, R.anim.hide);

        TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
        SimpleDateFormat date = new SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH시 m분", Locale.getDefault());
        date.setTimeZone(zone);

        this.date = date.format(new Date());
        binding.tvDate.setText(this.date);
        this.time = time.format(new Date());
        binding.tvTime.setText(this.time);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void setDateDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NewMemoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        binding.tvDate.setText(i + "년 " + (i1 + 1) + "월 " + i2 + "일");
                    }
                }, 2020, (2-1), 9);

        datePickerDialog.show();
    }

    private boolean window_show;
    public void back(View view){
        back();
    }
    private void back(){
        imm.hideSoftInputFromWindow(binding.editContent.getWindowToken(), 0);
        binding.editContent.requestFocus();

        if(!window_show){
            window_show = true;
            binding.btnYes.setEnabled(true);
            binding.btnNo.setEnabled(true);
            binding.windowSureExit.startAnimation(show);
            binding.windowSureExit.setVisibility(View.VISIBLE);
        }
    }

    public void yes(View view){
        if(window_show){
            finish();
        }
    }
    public void no(View view){
        if(window_show){
            window_show = false;
            binding.btnYes.setEnabled(false);
            binding.btnNo.setEnabled(false);
            binding.windowSureExit.startAnimation(hide);
            binding.windowSureExit.setVisibility(View.GONE);
        }
    }

    public void saveMemo(View view) {
        imm.hideSoftInputFromWindow(binding.editContent.getWindowToken(), 0);
        binding.editContent.requestFocus();

        databaseManager.insert(binding.editTitle.getText().toString(),
                binding.editContent.getText().toString(),
                time, date, "");

        finish();
    }
}
