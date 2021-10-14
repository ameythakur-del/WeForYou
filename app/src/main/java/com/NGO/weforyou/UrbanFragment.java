package com.NGO.weforyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.ChildEventListener;
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

public class UrbanFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchView;
    DatabaseReference reference,chipref;
    public List<urbanmodel> urbanmodels;
    ChipGroup chipGroup;
    String spec,city;
    Timer timer;
    private Handler mHandler;
    Spinner staticSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_urban, container, false);

        mHandler = new Handler();
        searchView = root.findViewById(R.id.search_bar);

        recyclerView = root.findViewById(R.id.view);
        reference = FirebaseDatabase.getInstance().getReference().child("Form");
        chipref=FirebaseDatabase.getInstance().getReference().child("Chips");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);

        chipGroup = root.findViewById(R.id.chipGroup);

        ProgressBar progressBar = root.findViewById(R.id.progress);

        staticSpinner =root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.taluka,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        city=staticSpinner.getSelectedItem().toString();

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
                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(getActivity(), urbanmodels);

                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(urbanRecyclerAdapter);
                urbanRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chipref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String newchip=snapshot.getValue().toString();
                Chip chip = new Chip(getActivity());
                chip.setText(newchip);
                chip.setChipBackgroundColorResource(R.color.chipback);
                chip.setCheckable(true);
                chip.setChipStrokeColorResource(R.color.stroke);
                chip.setChipStrokeWidth(2.5F);
                chip.setTextColor(getResources().getColor(R.color.black));
                chipGroup.addView(chip);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(getActivity(), urbanmodels);

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
                        //if (spec.equals("Electrician") || spec.equals("Barber") || spec.equals("Painter") || spec.equals("Mechanic") || spec.equals("Home Cleaning") || spec.equals("Pest Control") || spec.equals("Massage")) {
                        if (!spec.equals(null)){
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
                                    urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(getActivity(), urbanmodels);
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
                urbanRecyclerAdapter urbanRecyclerAdapter = new urbanRecyclerAdapter(getActivity(), urbanmodels);

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
                                (getActivity()).runOnUiThread(new Runnable() {
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
        return root;
    }

}