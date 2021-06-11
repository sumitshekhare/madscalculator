package com.androidcodr.mvvmcalculator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.androidcodr.mvvmcalculator.R;
import com.androidcodr.mvvmcalculator.adapter.RecyclerViewAdapter;
import com.androidcodr.mvvmcalculator.model.HistoryModel;
import com.androidcodr.mvvmcalculator.viewmodel.HistoryViewModel;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity  implements LifecycleOwner {

    HistoryActivity context;
    HistoryViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    private String userloggedin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context = this;
        recyclerView = findViewById(R.id.rv_main);
        viewModel = ViewModelProviders.of(context).get(HistoryViewModel.class);
        viewModel.getUserMutableLiveData().observe(context, userListUpdateObserver);

        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            userloggedin=extra.getString("user");
        }

    }

    Observer<ArrayList<HistoryModel>> userListUpdateObserver = new Observer<ArrayList<HistoryModel>>() {
        @Override
        public void onChanged(ArrayList<HistoryModel> userArrayList) {
            recyclerViewAdapter = new RecyclerViewAdapter(context,userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };




}