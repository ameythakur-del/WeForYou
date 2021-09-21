package com.NGO.weforyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {
    CardView weserve,hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        hospital=findViewById(R.id.hospital);
        weserve=findViewById(R.id.weServe);
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