package com.example.sriramthiyagaraja.hackathon.FingerPrintManager;

/**
 * Created by sriram.thiyagaraja on 6/22/2017.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sriramthiyagaraja.hackathon.R;


/**
 * Created by whit3hawks on 11/16/16.
 */
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;
    public static boolean fingerprintAuth = false;


    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
      //  Log.d("TAG",""+result.)
        this.update("Fingerprint Authentication succeeded.", true);

 fingerprintAuth = true;

    }


    public void update(String e, Boolean success){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.textView);
        ImageView imageView =(ImageView)((Activity)context).findViewById(R.id.img_finger_print);
        textView.setText(e);

        if(success){
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            imageView.setBackgroundResource(R.drawable.finger_print_success);
        }
        else{
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            imageView.setBackgroundResource(R.drawable.finger_print_failure);
        }
    }



}
