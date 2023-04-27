package com.example.th2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.th2.databinding.FragmentTaskBinding;

import java.util.List;

public class TaskFragment extends Fragment implements TaskAdapter.OnClickListener {
    private FragmentTaskBinding binding;
    private TaskAdapter adapter = new TaskAdapter(this);
    Database db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        db = new Database(requireContext());

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.reloadData(db.getAll());
        binding.rcv.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(Task task, View view) {
        Intent intent = new Intent(requireContext(), EditActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reloadData(db.getAll());
    }
}