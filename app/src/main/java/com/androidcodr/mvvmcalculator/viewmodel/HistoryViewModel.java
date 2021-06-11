package com.androidcodr.mvvmcalculator.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidcodr.mvvmcalculator.model.HistoryModel;
import com.androidcodr.mvvmcalculator.view.CalculatorActivity;
import com.androidcodr.mvvmcalculator.view.LoginMainActivity;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel {

    MutableLiveData<ArrayList<HistoryModel>> userLiveData;
    ArrayList<HistoryModel> userArrayList;

    public HistoryViewModel() {
        userLiveData = new MutableLiveData<>();
        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<HistoryModel>> getUserMutableLiveData() {
        return userLiveData;
    }


    public void init() {
        populateList();
        userLiveData.setValue(userArrayList);
    }

    public void populateList() {

       /* HistoryModel user = new HistoryModel("Darknight","10+2");

        userArrayList = new ArrayList<>();
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);
        userArrayList.add(user);*/

        userArrayList = LoginMainActivity.mdb.getAllDateTransData(CalculatorActivity.userloggedin);
    }
}