package com.example.sriramthiyagaraja.hackathon.aadharNumber;

/**
 * Created by sriram.thiyagaraja on 6/12/2017.
 */

//import com.example.sriramthiyagaraja.hackathon.aadharNumber.com.example.sriramthiyagaraja.hackathon.VerhoeffAlgorithm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AadharPresenterImpl implements the aadharPresenter interface
 */
public class AadharPresenterImpl implements IPresenter.aadharPresenter {
    private MainView mainView;
    public AadharPresenterImpl(MainActivityAadharNumber mainView)
    {
        this.mainView = mainView;
    }
// aadhar number validation function
    @Override
    public boolean aadharNumberValidator(String number) {
        String regexStr = "^[0-9]{12,12}$";
        //Pattern p = Pattern.compile(regexStr);
        //Matcher m = p.matcher(number);

        if(number.isEmpty() || !number.matches(regexStr)) {
          //  final boolean validatedAadhar = VerhoeffAlgorithm.validateVerhoeff(number);
            //if (!validatedAadhar) {
                mainView.showValidationError();
                return false;
            }
          //  return false;
        //}
        else{
            mainView.onValidationSuccess();
            return true;
        }


    }

}
