package com.example.sriramthiyagaraja.hackathon.aadharAmount;

/**
 * Created by sriram.thiyagaraja on 6/12/2017.
 */

/**
 *@IPresenterAadharAmount has amount validator function to validate the amount entered by the user
 */

public interface IPresenterAadharAmount {
//    This method validates the amount
    public boolean aadharAmountValidator(String amount);
}
