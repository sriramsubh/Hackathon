package com.example.sriramthiyagaraja.hackathon.aadharAmount;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.sriramthiyagaraja.hackathon.R;

/**
 * Created by sriram.thiyagaraja on 6/12/2017.
 */

/**
 * @PrsenterAadharImplementation implements the IPresenterAadharAmount
 */
public class PresenterAadharAmountImplementation implements IPresenterAadharAmount {

    private AadharEnterAmountView aadharEnterAmountView;

    public PresenterAadharAmountImplementation(AadharEnterAmountView aadharEnterAmountView) {
        this.aadharEnterAmountView = aadharEnterAmountView;
    }

    //This method is used to validate the amount entered and it takes amount as input
    @Override
    public boolean aadharAmountValidator(String amount) {

        final String PRICE_PATTERN = "((\\d{1,7})(((\\.)(\\d{1,2})){0,1}))";//eg: 1234567.06
        final String ZERO_VALIDATION = "(0)";

        if (amount.isEmpty() || (!amount.matches(PRICE_PATTERN)) || amount.matches(ZERO_VALIDATION)) {
//if the amount matches the above if statement then it is a valid one
            aadharEnterAmountView.inValidAmount();
            return false;

        } else {

            aadharEnterAmountView.onCorrectAmount();
            return true;
        }
    }


}


