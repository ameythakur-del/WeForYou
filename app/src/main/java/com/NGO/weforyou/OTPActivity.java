package com.NGO.weforyou;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.OtpView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    OtpView otp;
    Button login;
    TextView change, show, capture, resend;
    ProgressBar progressBar;
    String phoneNumber,name,phoneNum;
    FirebaseAuth mAuth;
    TextView timer;
    CountDownTimer countDownTimer;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        capture = (TextView) findViewById(R.id.capture);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        show = (TextView) findViewById(R.id.mobile_show);
        mAuth = FirebaseAuth.getInstance();
        otp = (OtpView) findViewById(R.id.otp_view);
        login = (Button) findViewById(R.id.login);
        phoneNumber = this.getIntent().getExtras().getString("phone");
        phoneNum="+91"+phoneNumber;
        name = this.getIntent().getExtras().getString("name");
        timer = (TextView) findViewById(R.id.timer);
        change = (TextView) findViewById(R.id.changeNumber);
        resend = (TextView) findViewById(R.id.resend);
        show.setText("+91-" + phoneNumber);

        users= FirebaseDatabase.getInstance().getReference("Panic mode Users");
        countDownTimer = new CountDownTimer(59000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                capture.setVisibility(View.INVISIBLE);
                resend.setVisibility(View.VISIBLE);
            }
        };
        sendOTP();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OTPActivity.this,Emergencylogin.class);
                startActivity(intent);
                finish();
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });
    }

    private void sendOTP() {
        countDownTimer.start();
        resend.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        capture.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(OTPActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential);
            otp.setText(credential.getSmsCode().toString());

            signInWithPhoneAuthCredential(credential);
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);
            progressBar.setVisibility(View.INVISIBLE);
            capture.setVisibility(View.INVISIBLE);
            resend.setVisibility(View.VISIBLE);
            countDownTimer.cancel();
            timer.setVisibility(View.INVISIBLE);
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(OTPActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Toast.makeText(OTPActivity.this, "Limit for the same mobile number is exceeded. Please try after some time", Toast.LENGTH_LONG).show();
            }

            // Show a message and update the UI
            // ...
        }
        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countDownTimer.cancel();
                    timer.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    String code = otp.getText().toString();
                    if(!TextUtils.isEmpty(code)){
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        signInWithPhoneAuthCredential(credential);
                    }
                }
            });
            // Save verification ID and resending token so we can use them later
            // ...
        }
    };
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        countDownTimer.cancel();
                        timer.setVisibility(View.INVISIBLE);
                        capture.setVisibility(View.INVISIBLE);
                        resend.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            HashMap hashMap=new HashMap();
                            hashMap.put("Name",name);
                            hashMap.put("phoneNumber",phoneNum);
                            users.child(phoneNum).updateChildren(hashMap);
                            Intent intent=new Intent(OTPActivity.this,panicinfo.class);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OTPActivity.this, "Verification failed", Toast.LENGTH_LONG).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}