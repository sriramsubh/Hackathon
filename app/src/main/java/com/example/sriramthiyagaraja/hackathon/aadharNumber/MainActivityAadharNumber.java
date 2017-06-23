package com.example.sriramthiyagaraja.hackathon.aadharNumber;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sriramthiyagaraja.hackathon.AuthSuccessActivity;
import com.example.sriramthiyagaraja.hackathon.FingerPrintManager.FingerprintHandler;
import com.example.sriramthiyagaraja.hackathon.R;
import com.example.sriramthiyagaraja.hackathon.aadharAmount.AadharEnterAmountView;
import com.example.sriramthiyagaraja.hackathon.aadharAmount.PresenterAadharAmountImplementation;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


/**
 * @MainActivityAadharNumber has the UI for the user to enter the number
 */
public class MainActivityAadharNumber extends AppCompatActivity implements MainView, Button.OnClickListener, AadharEnterAmountView {
    //copied variables
    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;

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
        // fingerprint validation function to validate the fingerprint
        fingerprintValidation();

    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }




    /**
     * This function performs the validation check with the presenter when the submit button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {

      if(validateAmountAndAadhar() && fingerprintValidation())
        startActivity(new Intent(MainActivityAadharNumber.this, AuthSuccessActivity.class));
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
    public boolean validateAmountAndAadhar()
    {
        EditText aadharNumber, aadharAmount;
        TextInputLayout aadharAmountTextInputLayout, aadharNumberTextInputLayout;
        //ImageView initialFingerPrintImage;


        aadharNumber = (EditText)  findViewById(R.id.aadharnumberAnimatededitText);
        aadharAmount = (EditText) findViewById(R.id.aadharAmountAnimatededitText);
        aadharNumberTextInputLayout = (TextInputLayout) findViewById(R.id.aadhar_number_TextView_id);
        aadharNumberTextInputLayout.setHint("aadhar number");
        aadharAmountTextInputLayout = (TextInputLayout) findViewById(R.id.aadhar_amount_TextView_id);
        aadharAmountTextInputLayout.setHint("amount");
        // initialFingerPrintImage = (ImageView) findViewById(R.id.img_finger_print);

        boolean aadharValidatedNumber = aadharPresenter.aadharNumberValidator(aadharNumber.getText().toString());
        boolean aadharAmountValidator = aadharPresenterImpl.aadharAmountValidator(aadharAmount.getText().toString());
        if (aadharValidatedNumber && aadharAmountValidator ) {
            aadharAmountTextInputLayout.setError(null);
            aadharNumberTextInputLayout.setError(null);

        return true;

        }
        // initialFingerPrintImage.setBackgroundResource(R.drawable.finger_print_success);
        //  Intent intent = new Intent(getApplicationContext(), ActivityFingerPrint.class);
        //startActivity(intent);
        else {
            // multiple IF-else block to catch the possible error outcomes

            if (!aadharAmountValidator && aadharValidatedNumber ) {
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
            // initialFingerPrintImage.setBackgroundResource(R.drawable.finger_print_failure);
            // Toast.makeText(this,"cannot start the activity",Toast.LENGTH_SHORT).show();
            return false;
        }


    }
    public boolean fingerprintValidation()
    {

            //begining of the code copu
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            TextView textView;

            textView = (TextView) findViewById(R.id.textView);


            // Check whether the device has a Fingerprint sensor.
            if (!fingerprintManager.isHardwareDetected()) {
                /**
                 * An error message will be displayed if the device does not contain the fingerprint hardware.
                 * However if you plan to implement a default authentication method,
                 * you can redirect the user to a default authentication activity from here.
                 * Example:
                 * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                 * startActivity(intent);
                 */
                textView.setText("Your Device does not have a Fingerprint Sensor");
            } else {
                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    textView.setText("Fingerprint authentication permission not enabled");
                } else {
                    // Check whether at least one fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        textView.setText("Register at least one fingerprint in Settings");
                    } else {
                        // Checks whether lock screen security is enabled or not
                        if (!keyguardManager.isKeyguardSecure()) {
                            textView.setText("Lock screen security not enabled in Settings");
                        } else {
                            generateKey();


                            if (cipherInit()) {
                                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                FingerprintHandler helper = new FingerprintHandler(this);
                                helper.startAuth(fingerprintManager, cryptoObject);
                                return true;
                            }
                        }
                    }
                }

            }
        return false;
    }

}
