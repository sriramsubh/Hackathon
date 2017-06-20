package com.example.sriramthiyagaraja.hackathon.aadharNumber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sriramthiyagaraja.hackathon.R;
import com.example.sriramthiyagaraja.hackathon.aadharAmount.AadharEnterAmountView;
import com.example.sriramthiyagaraja.hackathon.aadharAmount.PresenterAadharAmountImplementation;


/**
 * @MainActivityAadharNumber has the UI for the user to enter the number
 */
public class MainActivityAadharNumber extends AppCompatActivity implements MainView, Button.OnClickListener, AadharEnterAmountView {
    private AadharPresenterImpl aadharPresenter;
    private PresenterAadharAmountImplementation aadharPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_input);
        aadharPresenter = new AadharPresenterImpl(this);
        aadharPresenterImpl = new PresenterAadharAmountImplementation(this);
        Button button = (Button) findViewById(R.id.button_next);

        button.setOnClickListener(this);
    }

    /**
     * This function performs the validation check with the presenter when the submit button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

        EditText aadharNumber, aadharAmount;
        TextInputLayout aadharAmountTextInputLayout, aadharNumberTextInputLayout;
        ImageView initialFingerPrintImage;


        aadharNumber = (EditText) findViewById(R.id.aadharnumberAnimatededitText);
        aadharAmount = (EditText) findViewById(R.id.aadharAmountAnimatededitText);
        aadharNumberTextInputLayout = (TextInputLayout) findViewById(R.id.aadhar_number_TextView_id);
        aadharNumberTextInputLayout.setHint("aadhar number");
        aadharAmountTextInputLayout = (TextInputLayout) findViewById(R.id.aadhar_amount_TextView_id);
        aadharAmountTextInputLayout.setHint("amount");
        initialFingerPrintImage = (ImageView) findViewById(R.id.img_finger_print);

        boolean aadharValidatedNumber = aadharPresenter.aadharNumberValidator(aadharNumber.getText().toString());
        boolean aadharAmountValidator = aadharPresenterImpl.aadharAmountValidator(aadharAmount.getText().toString());
        if (aadharValidatedNumber && aadharAmountValidator) {
            aadharAmountTextInputLayout.setError(null);
            aadharNumberTextInputLayout.setError(null);
            initialFingerPrintImage.setBackgroundResource(R.drawable.finger_print_success);
            //  Intent intent = new Intent(getApplicationContext(), ActivityFingerPrint.class);
            //startActivity(intent);
        } else {
            // multiple IF-else block to catch the possible error outcomes

            if (!aadharAmountValidator && aadharValidatedNumber) {
                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                aadharAmount.startAnimation(shake);
                aadharNumberTextInputLayout.setError(null);
                aadharAmountTextInputLayout.setError("enter the correct amount");
            } else {
                if (aadharAmountValidator && !aadharValidatedNumber) {
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    aadharNumber.startAnimation(shake);
                    aadharAmountTextInputLayout.setError(null);
                    aadharNumberTextInputLayout.setError("enter correct Aadhar");
                } else {
                    if (!aadharAmountValidator && !aadharValidatedNumber) {
                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                        aadharNumber.startAnimation(shake);
                        aadharAmount.startAnimation(shake);
                        aadharAmountTextInputLayout.setError("enter correct amount");
                        aadharNumberTextInputLayout.setError("enter correct aadhar");
                    }
                }
            }
            initialFingerPrintImage.setBackgroundResource(R.drawable.finger_print_failure);
            // Toast.makeText(this,"cannot start the activity",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void showValidationError() {
        //show validationError message
        Toast.makeText(this, "check for the addhar number correctness", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationSuccess() {
        Toast.makeText(this, "Aadhar validated successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCorrectAmount() {
        Toast.makeText(this, "correct amount entered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inValidAmount() {
        Toast.makeText(this, "check the amount entered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "back press not allowed", Toast.LENGTH_SHORT).show();
    }
}
