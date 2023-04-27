package com.example.th2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.example.th2.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener,TaskAdapter.OnClickListener{
    private FragmentSearchBinding binding;
    Database db;
    TaskAdapter adapter;
    List<String> stts = new ArrayList<String>(){{
        add("All");
        add("Chưa thực hiện");
        add("Đang thực hiện");
        add("Đã hoàn thành");
    }};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        db = new Database(requireContext());
        adapter = new TaskAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.search.setOnQueryTextListener(this);
        binding.search.setOnCloseListener(this);

        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(requireContext(), R.layout.item_sp, stts);
        binding.spStt.setAdapter(spAdapter);
        binding.spStt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = stts.get(i);
                if(s.equals(stts.get(0))){
                    List<Task> t = db.getAll();
                    binding.tvSl.setText(String.valueOf(t.size()));
                    adapter.reloadData(t);
                }
                else{
                    List<Task> t = db.searchByStatus(s);
                    binding.tvSl.setText(String.valueOf(t.size()));
                    adapter.reloadData(t);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void OnItemClick(Task task, View view) {
        Intent intent = new Intent(requireContext(), EditActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        adapter.reloadData(db.searchItemBykey(s));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.reloadData(db.searchItemBykey(s));
        return false;
    }

    @Override
    public boolean onClose() {
        adapter.reloadData(new ArrayList<>());
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reloadData(db.getAll());
    }
}