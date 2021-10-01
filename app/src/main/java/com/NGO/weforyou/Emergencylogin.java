package com.NGO.weforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Emergencylogin extends AppCompatActivity {
    Button sendotp;
    EditText name,phone;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String phoneNumber,fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencylogin);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        sendotp=findViewById(R.id.sendotp);
        progressDialog = new ProgressDialog(this);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phone.getText().toString().trim();
                fullname = name.getText().toString();

                if (TextUtils.isEmpty(fullname)) {
                    progressDialog.dismiss();
                    Toast.makeText(Emergencylogin.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    progressDialog.dismiss();
                    Toast.makeText(Emergencylogin.this, "Please enter the mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNumber.length() < 10){
                    progressDialog.dismiss();
                    Toast.makeText(Emergencylogin.this, "Please enter the valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNumber.length() > 10){
                    progressDialog.dismiss();
                    Toast.makeText(Emergencylogin.this, "Please enter the mobile number without country code", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    Intent intent = new Intent(Emergencylogin.this, OTPActivity.class);
                    intent.putExtra("phone", phoneNumber);
                    intent.putExtra("name", fullname);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}