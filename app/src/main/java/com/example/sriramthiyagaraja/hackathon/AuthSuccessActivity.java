package com.example.sriramthiyagaraja.hackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AuthSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_success);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"you cannot back!!",Toast.LENGTH_SHORT).show();
            }
}
