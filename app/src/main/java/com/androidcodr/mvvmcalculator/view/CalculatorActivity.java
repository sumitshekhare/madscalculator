package com.androidcodr.mvvmcalculator.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.androidcodr.mvvmcalculator.R;
import com.androidcodr.mvvmcalculator.databinding.ActivityCalculatorBinding;

import com.androidcodr.mvvmcalculator.model.HistoryModel;
import com.androidcodr.mvvmcalculator.viewmodel.CalculatorViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};
    // TextView used to display the output
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    // Represent that current state is in error or not
    private boolean stateError;
    // If true, do not allow to add another DOT
    private boolean lastDot;

    public static String userloggedin;


    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel updates the Model
        // after observing changes in the View
        // model will also update the view
        // via the ViewModel
        ActivityCalculatorBinding activityMainBindings = DataBindingUtil.setContentView(this, R.layout.activity_calculator);
        activityMainBindings.setViewModel(new CalculatorViewModel());
        activityMainBindings.executePendingBindings();


        this.txtScreen = (TextView) findViewById(R.id.txtInput);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener();
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener();
        context = this;

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            userloggedin = extra.getString("user");
        }
    }

    // any change in toastMessage attribute
    // defined on the Button with bind prefix
    // invokes this method
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent i = new Intent(this, HistoryActivity.class);
            i.putExtra("user", userloggedin);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just append/set the text of clicked button
                Button button = (Button) v;
                if (stateError) {
                    // If current state is Error, replace the error message
                    txtScreen.setText(button.getText());
                    stateError = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    txtScreen.append(button.getText());
                }
                // Set the flag
                lastNumeric = true;
            }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.
     */
    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        // Decimal point
        findViewById(R.id.btnDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });


        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText("");  // Clear the screen
                // Reset all the states and flags
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });


        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.

        String docalculation = txtScreen.getText().toString();

        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = txtScreen.getText().toString();
            // Create an Expression (A class from exp4j library)

            try {
                // Calculate the result and display
                txt = getanswer(txt);
                double result = Double.parseDouble(txt);
                txtScreen.setText(Double.toString(result));
                lastDot = true; // Result contains a dot
            } catch (ArithmeticException ex) {
                // Display an error message
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
        LoginMainActivity.mdb.addtransaction(new HistoryModel(userloggedin, docalculation));
    }


    private String getanswer(String requested) {
        String input = requested;

        String output = "0";

        String[] substrings = input.split("(?<=[-+*/])|(?=[-+*/])");

        List<String> substringsnew = new ArrayList<String>();
        for (int i = 0; i < substrings.length; i++) {
            substringsnew.add(substrings[i]);
        }


        for (int i = 0; i < substringsnew.size(); i++) {
            String ch = substringsnew.get(i);
            int pos =substringsnew.indexOf("*");
            if (pos > -1) {
                Double FNo = Double.parseDouble(substringsnew.get(pos - 1));
                Double LNo = Double.parseDouble(substringsnew.get(pos + 1));
                output = (FNo * LNo) + "";

                substringsnew.remove(String.format("%.0f", FNo));
                substringsnew.remove(String.format("%.0f", LNo));
                substringsnew.remove("*");
                substringsnew.add(pos - 1, output);
            }

            pos = substringsnew.indexOf("+");
            if (pos > -1) {
                Double FNo = Double.parseDouble(substringsnew.get(pos - 1));
                Double LNo = Double.parseDouble(substringsnew.get(pos + 1));
                output = (FNo + LNo) + "";

                substringsnew.remove(String.format("%.0f", FNo));
                substringsnew.remove(String.format("%.0f", LNo));
                substringsnew.remove("+");
                substringsnew.add(pos - 1, output);
            }

            pos = substringsnew.indexOf("/");
            if (pos > -1) {
                Double FNo = Double.parseDouble(substringsnew.get(pos - 1));
                Double LNo = Double.parseDouble(substringsnew.get(pos + 1));
                output = (FNo / LNo) + "";
                substringsnew.remove(String.format("%.0f", FNo));
                substringsnew.remove(String.format("%.0f", LNo));
                substringsnew.remove("/");
                substringsnew.add(pos - 1, output);
            }

            pos = substringsnew.indexOf("-");
            if (pos > -1) {
                Double FNo = Double.parseDouble(substringsnew.get(pos - 1));
                Double LNo = Double.parseDouble(substringsnew.get(pos + 1));
                output = (FNo - LNo) + "";
                substringsnew.remove(String.format("%.0f", FNo));
                substringsnew.remove(String.format("%.0f", LNo));
                substringsnew.remove("-");
                substringsnew.add(pos - 1, output);
            }
        }

        return output;
    }


}
