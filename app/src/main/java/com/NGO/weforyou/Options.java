package com.NGO.weforyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Options extends AppCompatActivity {
    CardView weserve,hospital,emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        hospital=findViewById(R.id.hospital);
        weserve=findViewById(R.id.weServe);
        emergency=findViewById(R.id.emergency);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent=new Intent(Options.this,panicinfo.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(Options.this,Emergencylogin.class);
                    startActivity(intent);
                }

            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Options.this,Main2Activity.class);
                startActivity(intent);
            }
        });
        weserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Options.this,urban.class);
                startActivity(intent);
            }
        });
    }
}