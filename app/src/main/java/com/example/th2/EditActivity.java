package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.th2.databinding.ActivityEditBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    List<String> stts = new ArrayList<String>(){{
        add("Chưa thực hiện");
        add("Đang thực hiện");
        add("Đã hoàn thành");
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Database db = new Database(this);
        Task task = (Task) getIntent().getSerializableExtra("task");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_sp, stts);
        binding.spStatus.setAdapter(adapter);

        binding.edtName.setText(task.getName());
        binding.edtContent.setText(task.getContent());
        binding.edtDate.setText(task.getDate());

        int positionSpinner = getIndex(task.getStatus());
        binding.spStatus.setSelection(positionSpinner, true);
        if(task.getColab() != 0){
            binding.isColab.setChecked(true);
        }
        else{
            binding.isColab.setChecked(false);
        }


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

        binding.btnUpdate.setOnClickListener(view -> {
            String name = binding.edtName.getText().toString().trim();
            String content = binding.edtContent.getText().toString().trim();
            String status = binding.spStatus.getSelectedItem().toString();
            String date = binding.edtDate.getText().toString().trim();
            int isColab = 0;
            if(binding.isColab.isChecked()){
                isColab = 1;
            }
            db.updateTask(new Task(task.getId(), name, content, date, status, isColab));
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
            finish();
        });

        binding.btnRemove.setOnClickListener(view -> {
            db.deleteTask(task.getId());
            finish();
        });
        binding.btnBack.setOnClickListener(view -> finish());
    }

    private int getIndex(String status) {
        for (String s : stts) {
            if (s.equals(status)) return stts.indexOf(status);
        }
        return 0;
    }
}