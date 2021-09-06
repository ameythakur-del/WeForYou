package com.NGO.weforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class urban extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchView;
    DatabaseReference reference;
    public List<urbanmodel> urbanmodels;
    ChipGroup chipGroup;
    String spec;
    Timer timer;
    private Handler mHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urban);

        mHandler = new Handler();
        searchView = findViewById(R.id.search_bar);

        recyclerView = findViewById(R.id.view);
        reference = FirebaseDatabase.getInstance().getReference().child("Form");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);

        chipGroup = findViewById(R.id.chipGroup);

        ProgressBar progressBar = findViewById(R.id.progress);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urbanmodels = new ArrayList<urbanmodel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    {
                        urbanmodel urbanmodel = dataSnapshot1.getValue(urbanmodel.class);
                        urbanmodels.add(urbanmodel);
                    }
                }
                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(urban.this, urbanmodels);

                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(urbanRecyclerAdapter);
                urbanRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);

                if(chip != null){
                    spec = chip.getText().toString();

                    if(spec.equals("All")){
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                urbanmodels = new ArrayList<urbanmodel>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        urbanmodel urbanmodel = dataSnapshot1.getValue(urbanmodel.class);
                                        urbanmodels.add(urbanmodel);
                                    }
                                }
                                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(urban.this, urbanmodels);

                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(urbanRecyclerAdapter);
                                urbanRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    else {
                        Query query;
                        if (spec.equals("Electrician") || spec.equals("Barber") || spec.equals("Painter") || spec.equals("Mechanic") || spec.equals("Home Cleaning") || spec.equals("Pest Control") || spec.equals("Massage")) {
                            query = reference.orderByChild("Specialization").equalTo(spec);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    urbanmodels = new ArrayList<urbanmodel>();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        {
                                            urbanmodel urbanmodel = dataSnapshot1.getValue(urbanmodel.class);
                                            urbanmodels.add(urbanmodel);
                                        }
                                    }
                                    urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(urban.this, urbanmodels);
                                    recyclerView.setAdapter(urbanRecyclerAdapter);
                                    urbanRecyclerAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }
                }
            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urbanmodels = new ArrayList<urbanmodel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    {
                        urbanmodel urbanmodel = dataSnapshot1.getValue(urbanmodel.class);
                        urbanmodels.add(urbanmodel);
                    }
                }
                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(urban.this, urbanmodels);

                searchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }



                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                (urban.this).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        urbanRecyclerAdapter.getFilter().filter(s);
                                        recyclerView.setAdapter(urbanRecyclerAdapter);
                                    }
                                });
                            }
                        }, 1000);
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}