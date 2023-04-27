package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.th2.databinding.ActivityAddBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Database db = new Database(this);
        String[] stts = {"Chưa thực hiện", "Đang thực hiện", "Đã hoàn thành"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sp, stts);
        binding.spStatus.setAdapter(adapter);

        Calendar newCalendar = Calendar.getInstance();
        binding.edtDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                binding.edtDate.setText(sdf.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        binding.btnAdd.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString().trim();
            String content = binding.edtContent.getText().toString().trim();
            String status = binding.spStatus.getSelectedItem().toString();
            String date = binding.edtDate.getText().toString().trim();
            int isColab = 0;
            if(binding.isColab.isChecked()){
                isColab = 1;
            }
            Task task = new Task(name, content, date, status, isColab);
            db.insertTask(task);
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

        binding.btnCancel.setOnClickListener(view -> finish());
    }
}