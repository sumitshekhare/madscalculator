package com.androidcodr.mvvmcalculator.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.androidcodr.mvvmcalculator.R;
import com.androidcodr.mvvmcalculator.databinding.ActivityMainBinding;
import com.androidcodr.mvvmcalculator.db.SQLiteDBhelper;
import com.androidcodr.mvvmcalculator.viewmodel.LoginAppViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoginMainActivity extends AppCompatActivity {
    public static Context mContext;
    private static final String DATABASE_NAME = "calculationmaster.db";
    public static SQLiteDBhelper mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // ViewModel updates the Model
        // after observing changes in the View

        // model will also update the view
        // via the ViewModel

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(new LoginAppViewModel());
        activityMainBinding.executePendingBindings();
        checkDB();
        mdb = new SQLiteDBhelper(mContext);
    }

    // any change in toastMessage attribute
    // defined on the Button with bind prefix
    // invokes this method
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null) {
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    public void CopyDB(String paramString) throws IOException {
        FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
        byte[] arrayOfByte = new byte[1024];
        InputStream localInputStream = getAssets().open(DATABASE_NAME);
        while (true) {
            int i = localInputStream.read(arrayOfByte);
            if (i <= 0) {
                localInputStream.close();
                localFileOutputStream.flush();
                localFileOutputStream.close();
                return;
            }
            localFileOutputStream.write(arrayOfByte, 0, i);
            localFileOutputStream.flush();
        }
    }


    public void checkDB() {
        try {
            String str1 = getPackageName();
            String str2 = "/data/data/" + str1 + "/databases";
            String str3 = "/data/data/" + str1 + "/databases/" + DATABASE_NAME;
            File localFile1 = new File(str2);
            File localFile2 = new File(str3);
            if (!localFile1.exists()) {
                localFile1.mkdirs();
                localFile1.createNewFile();
            }
            if (!localFile2.exists()) {
                CopyDB(str3);
            }
        } catch (FileNotFoundException localFileNotFoundException) {
            while (true) {
                localFileNotFoundException.printStackTrace();
            }
        } catch (IOException localIOException) {
            while (true) {
                localIOException.printStackTrace();
            }
        }
    }


}
