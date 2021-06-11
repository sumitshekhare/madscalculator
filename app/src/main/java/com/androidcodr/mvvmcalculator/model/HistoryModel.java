package com.androidcodr.mvvmcalculator.model;

import androidx.annotation.Nullable;

public class HistoryModel {

    @Nullable
    String user, numberstring;

    // constructor to initialize
    // the variables
    public HistoryModel(String user, String numberstring) {
        this.user = user;
        this.numberstring = numberstring;
    }

    // getter and setter methods
    // for email variable
    @Nullable
    public String getUser() {
        return user;
    }

    public void setUser(@Nullable String user) {
        this.user = user;
    }

    // getter and setter methods
    // for password variable
    @Nullable
    public String getCalculation() {
        return numberstring;
    }

    public void setCalculation(@Nullable String numberstring) {
        this.numberstring = numberstring;
    }

}
