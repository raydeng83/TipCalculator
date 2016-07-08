package tipcalculator.murach.com.tipcalculator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class TipCalculatorActivity extends Activity implements TextView.OnEditorActionListener, View.OnClickListener{

    private EditText billAmountEditText;
    private TextView tipTextView;
    private TextView totalTextView;
    private TextView tipPercentTextView;
    private String billAmountString="";
    private String tipAmountString="";
    private Button percentUpButton;
    private Button percentDownButton;

    private int tipPercent = 15;

    private SharedPreferences savedValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        billAmountEditText = (EditText) findViewById(R.id.billAmountEditText);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalAmount);
        tipPercentTextView = (TextView) findViewById(R.id.percentNumber);


        billAmountEditText.setOnEditorActionListener(this);

        percentDownButton = (Button) findViewById(R.id.percentDownButton);
        percentUpButton = (Button) findViewById(R.id.percentUpButton);

        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

//    @Override
//    public void onPause() {
//        Editor editor = savedValues.edit();
//        editor.putString("billAmountString", billAmountString);
//        editor.putInt("tipPercent", tipPercent);
//        editor.commit();
//
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        billAmountString = savedValues.getString("billAmountString", "");
//        tipPercent = savedValues.getInt("tipPercent", tipPercent);
//    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {


        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

            calculateAndDisplay();

        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.percentDownButton: tipPercent = tipPercent - 1; calculateAndDisplay(); break;
            case R.id.percentUpButton: tipPercent = tipPercent + 1; calculateAndDisplay(); break;
        }

    }

    public void calculateAndDisplay() {
        billAmountString = billAmountEditText.getText().toString();
        String percentNumber = tipPercent+"%";

        float billAmount;

        if(billAmountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAmountString);
        }

        float tipAmount = billAmount * tipPercent / 100;
        float totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        tipPercentTextView.setText(percentNumber);
        totalTextView.setText(currency.format(totalAmount));
    }
}
