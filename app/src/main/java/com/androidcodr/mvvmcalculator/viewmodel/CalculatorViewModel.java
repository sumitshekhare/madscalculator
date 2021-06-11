package com.androidcodr.mvvmcalculator.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.androidcodr.mvvmcalculator.BR;
import com.androidcodr.mvvmcalculator.model.CalculatorModel;


public class CalculatorViewModel extends BaseObservable {

    // creating object of Model class
    private CalculatorModel model;

    // string variables for
    // toast messages
    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";

    @Bindable
    // string variable for
    // toast message
    private String toastMessage = null;

    // getter and setter methods
    // for toast message
    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    // getter and setter methods
    // for email varibale
    @Bindable
    public String getNumberString() {
        return model.getNumberString();
    }

    public void setNumberString(String numberstring) {
        model.setNumberString(numberstring);
        notifyPropertyChanged(BR.userEmail);
    }

    // getter and setter methods
    // for password variable
    @Bindable
    public String getAnswerString() {
        return model.getAnswerString();
    }

    public void setAnswerString(String answerstring) {
        model.setAnswerString(answerstring);
        notifyPropertyChanged(BR.userPassword);
    }

    // constructor of ViewModel class
    public CalculatorViewModel() {
        // instantiating object of
        // model class
        model = new CalculatorModel("", "");
    }

    // actions to be performed
    // when user clicks
    // the LOGIN button
    public void onButtonClicked() {
        if (isValid()) {
            setToastMessage(successMessage);
        } else {
            setToastMessage(errorMessage);
        }
    }

    // method to keep a check
    // that variable fields must
    // not be kept empty by user
    public boolean isValid() {
        return !TextUtils.isEmpty(getNumberString()) && Patterns.EMAIL_ADDRESS.matcher(getAnswerString()).matches()
                && getAnswerString().length() > 5;
    }
}
