package com.NGO.weforyou;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class panicinfo extends AppCompatActivity {
    TextInputEditText name, phoneNumber, gphoneNumber;
    DatabaseReference users,panicmsg;
    FirebaseUser mUser;
    String sphone,sname,messsage,sgphoneNumber;
    double latitude,longitude;
    Button button,savedeatils;
    FusedLocationProviderClient mFusedLocationClient;
    String localty,street,Taluka,link;
    Spinner staticSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panicinfo);
        button = findViewById(R.id.button);
        savedeatils=findViewById(R.id.savedetails);
        users = FirebaseDatabase.getInstance().getReference("Panic mode Users");
        panicmsg=FirebaseDatabase.getInstance().getReference("Panic Messages Sent");
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        gphoneNumber = findViewById(R.id.gphoneNum);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        sphone = mUser.getPhoneNumber();


        ActivityCompat.requestPermissions(panicinfo.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);


        staticSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.taluka,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);



        users.child(sphone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sname = snapshot.child("Name").getValue().toString();
                    name.setText(sname);
                    phoneNumber.setText(sphone);
                    if (snapshot.child("GuardianPhone").exists() &&snapshot.child("Taluka").exists() ){
                        button.setVisibility(View.VISIBLE);
                        savedeatils.setVisibility(View.GONE);
                        gphoneNumber.setText(snapshot.child("GuardianPhone").getValue().toString());
                        String tal=snapshot.child("Taluka").getValue().toString();
                        int spinnerpos=staticAdapter.getPosition(tal);
                        staticSpinner.setSelection(spinnerpos);
                    }
                    else {
                        savedeatils.setVisibility(View.VISIBLE);
                        button.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sgphoneNumber=gphoneNumber.getText().toString();
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (TextUtils.isEmpty(sname)) {
                    name.setError( "Name is required!");
                    Toast.makeText(panicinfo.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sgphoneNumber)) {
                    Toast.makeText(panicinfo.this, "Please enter Guardian's mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sgphoneNumber.length() < 10){
                    Toast.makeText(panicinfo.this, "Please enter valid Guardian's mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sgphoneNumber.length() > 10){
                    Toast.makeText(panicinfo.this, "Please enter Guardian's number without country code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (staticSpinner.getSelectedItem().toString().equals("Select")){
                    Toast.makeText(panicinfo.this, "Please select Taluka", Toast.LENGTH_SHORT).show();
                }
                if (ActivityCompat.checkSelfPermission(panicinfo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(panicinfo.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                }
                if (ActivityCompat.checkSelfPermission(panicinfo.this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(panicinfo.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                    ActivityCompat.requestPermissions(panicinfo.this, new String[] { Manifest.permission.SEND_SMS}, 44);
                }
                else {
                    statusCheck();
                    getlocation();
                    AlertDialog.Builder ab = new AlertDialog.Builder(panicinfo.this);
                    ab.setMessage("By Clicking yes,your information will be sent to police").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }

                //FirebaseAuth.getInstance().signOut();
                //Intent intent = new Intent(panicinfo.this, Options.class);
                //startActivity(intent);
            }
        });
        savedeatils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sgphoneNumber=gphoneNumber.getText().toString();
                if (TextUtils.isEmpty(sname)) {
                    name.setError( "Name is required!");
                    Toast.makeText(panicinfo.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sgphoneNumber)) {
                    Toast.makeText(panicinfo.this, "Please enter Guardian's mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sgphoneNumber.length() < 10){
                    Toast.makeText(panicinfo.this, "Please enter valid Guardian's mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sgphoneNumber.length() > 10){
                    Toast.makeText(panicinfo.this, "Please enter Guardian's number without country code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (staticSpinner.getSelectedItem().toString().equals("Select")){
                    Toast.makeText(panicinfo.this, "Please select Taluka", Toast.LENGTH_SHORT).show();
                }
                else {
                    Taluka= staticSpinner.getSelectedItem().toString();
                    HashMap hashMap=new HashMap();
                    hashMap.put("Taluka",Taluka);
                    hashMap.put("GuardianPhone",gphoneNumber.getText().toString());
                    users.child(sphone).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            Toast.makeText(panicinfo.this, "Details Saved", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //find();

        if (ActivityCompat.checkSelfPermission(panicinfo.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(panicinfo.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        statusCheck();


    }

    private void find() {
        if (ActivityCompat.checkSelfPermission(panicinfo.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //getlocation();
        } else {
            ActivityCompat.requestPermissions(panicinfo.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if (location!= null){
                    try {
                        Geocoder geocoder=new Geocoder(panicinfo.this, Locale.getDefault());
                        List<Address> addresses=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        street=addresses.get(0).getSubLocality();
                        localty=addresses.get(0).getLocality();
                    } catch (IOException e) {
                        Log.d(e.getMessage(), "Location");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
        else {
            getlocation();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Toast.makeText(panicinfo.this, "Please turn on Location", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Do your Yes progress
                    link="https://maps.google.com/?q="+latitude+","+longitude;
                    Taluka= staticSpinner.getSelectedItem().toString();
                    messsage="Name:"+sname +"\n"+
                            "PhoneNo:"+sphone+"\n"+
                            "ContactNo:"+sgphoneNumber+"\n"+
                            link+"\n"+"Area:"+street+","+localty+"\n"+Taluka;

                    try {
                        final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage("8600608273",null,messsage
                                ,null,null);
                        Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
                        HashMap hashMap=new HashMap();
                        hashMap.put("message",messsage);
                        panicmsg.child(sphone+","+currentDateTimeString).updateChildren(hashMap);

                    }catch (Exception e)
                    {
                        Log.d(e.getMessage(), "SMS error ");
                        Toast.makeText(getApplicationContext(),"Message not sent",Toast.LENGTH_LONG).show();
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Do your No progress
                    break;
            }
        }
    };

}