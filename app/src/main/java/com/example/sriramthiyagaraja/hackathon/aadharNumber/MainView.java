package com.example.sriramthiyagaraja.hackathon.aadharNumber;

/**
 * Created by sriram.thiyagaraja on 6/12/2017.
 */

/**
 * @MainView provides the view side interaction with presenter and validates the view
 */
public interface MainView {
//    interface method to show validation error
    public void showValidationError();
//    interface method to show validation success
    public void onValidationSuccess();
   // public void onValidationFailure();
}
