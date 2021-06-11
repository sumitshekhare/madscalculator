package com.androidcodr.mvvmcalculator.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.androidcodr.mvvmcalculator.BR;
import com.androidcodr.mvvmcalculator.model.LoginModel;
import com.androidcodr.mvvmcalculator.view.CalculatorActivity;
import com.androidcodr.mvvmcalculator.view.LoginMainActivity;


public class LoginAppViewModel extends BaseObservable {

    // creating object of Model class
    private LoginModel model;

    // string variables for
    // toast messages
    private String successMessage = "Login successful";
    private String errorMessage = "User or Password is not valid";

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
    public String getUserEmail() {
        return model.getEmail();
    }

    public void setUserEmail(String email) {
        model.setEmail(email);
        notifyPropertyChanged(BR.userEmail);
    }

    // getter and setter methods
    // for password variable
    @Bindable
    public String getUserPassword() {
        return model.getPassword();
    }

    public void setUserPassword(String password) {
        model.setPassword(password);
        notifyPropertyChanged(BR.userPassword);
    }

    // constructor of ViewModel class
    public LoginAppViewModel() {
        // instantiating object of
        // model class
        model = new LoginModel("", "");
    }

    // actions to be performed
    // when user clicks
    // the LOGIN button
    public void onButtonClicked() {
        if (isValid()) {
            setToastMessage(successMessage);
            Intent i = new Intent(LoginMainActivity.mContext, CalculatorActivity.class);
            i.putExtra("user", getUserEmail());
            LoginMainActivity.mContext.startActivity(i);
            ((Activity) LoginMainActivity.mContext).finish();
        } else {
            setToastMessage(errorMessage);
        }
    }

    // method to keep a check
    // that variable fields must
    // not be kept empty by user
    public boolean isValid() {
        return LoginMainActivity.mdb.isAccExists(getUserEmail().trim(), getUserPassword().trim());
    }

}
