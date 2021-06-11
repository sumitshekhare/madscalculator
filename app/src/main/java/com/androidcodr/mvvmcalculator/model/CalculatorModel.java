package com.androidcodr.mvvmcalculator.model;

import androidx.annotation.Nullable;

public class CalculatorModel {

    @Nullable
    String numberstring, answerstring;

    // constructor to initialize
    // the variables
    public CalculatorModel(String numberstring, String answerstring) {
        this.numberstring = numberstring;
        this.answerstring = answerstring;
    }

    // getter and setter methods
    // for email variable
    @Nullable
    public String getNumberString() {
        return numberstring;
    }

    public void setNumberString(@Nullable String numberstring) {
        this.numberstring = numberstring;
    }

    // getter and setter methods
    // for password variable
    @Nullable
    public String getAnswerString() {
        return answerstring;
    }

    public void setAnswerString(@Nullable String answerstring) {
        this.answerstring = answerstring;
    }

}
