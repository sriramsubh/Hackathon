package com.example.sriramthiyagaraja.hackathon.aadharNumber;

/**
 * Created by sriram.thiyagaraja on 6/12/2017.
 */

public interface PresenterAadharNumber {
    public interface aadharPresenter{
        //this interface calls the aadharNumberValidator function to validate the aadhaNumber
        public boolean aadharNumberValidator(String number);

    }
    public void aadharAmountValidator(Long amount);

}
