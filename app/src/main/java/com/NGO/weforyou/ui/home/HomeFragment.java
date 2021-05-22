package com.NGO.weforyou.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.NGO.weforyou.Hospital;
import com.NGO.weforyou.HospitalRecyclerAdapter;
import com.NGO.weforyou.R;
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

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private EditText searchView;
    DatabaseReference reference;
    public List<Hospital> hospitals;
    ChipGroup chipGroup;
    String tal;
    Timer timer;
    private Handler mHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mHandler = new Handler();
        searchView = root.findViewById(R.id.search_bar);

        recyclerView = root.findViewById(R.id.view);
        reference = FirebaseDatabase.getInstance().getReference().child("Hospitals");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);

        chipGroup = root.findViewById(R.id.chipGroup);

        ProgressBar progressBar = root.findViewById(R.id.progress);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals = new ArrayList<Hospital>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    {
                        Hospital hospital = dataSnapshot1.getValue(Hospital.class);
                        hospitals.add(hospital);
                    }
                }
                HospitalRecyclerAdapter hospitalRecyclerAdapter = new HospitalRecyclerAdapter(getActivity(), hospitals);

                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(hospitalRecyclerAdapter);
                hospitalRecyclerAdapter.notifyDataSetChanged();
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
                    tal = chip.getText().toString();

                    if(tal.equals("All")){
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                hospitals = new ArrayList<Hospital>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Hospital hospital = dataSnapshot1.getValue(Hospital.class);
                                        hospitals.add(hospital);
                                    }
                                }
                                HospitalRecyclerAdapter hospitalRecyclerAdapter = new HospitalRecyclerAdapter(getActivity(), hospitals);

                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(hospitalRecyclerAdapter);
                                hospitalRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    else {
                        Query query;
                        if (tal.equals("Devgad") || tal.equals("Kankavli") || tal.equals("Malvan") || tal.equals("Vengurla") || tal.equals("Sawantwadi") || tal.equals("Vaibhavwadi") || tal.equals("Dodamarg") || tal.equals("Kudal")) {
                            query = reference.orderByChild("Taluka").equalTo(tal);
                        } else {
                            query = reference.orderByChild("center").equalTo(tal);
                        }
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                hospitals = new ArrayList<Hospital>();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    {
                                        Hospital hospital = dataSnapshot1.getValue(Hospital.class);
                                        hospitals.add(hospital);
                                    }
                                }
                                HospitalRecyclerAdapter hospitalRecyclerAdapter = new HospitalRecyclerAdapter(getActivity(), hospitals);
                                recyclerView.setAdapter(hospitalRecyclerAdapter);
                                hospitalRecyclerAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitals = new ArrayList<Hospital>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    {
                        Hospital hospital = dataSnapshot1.getValue(Hospital.class);
                        hospitals.add(hospital);
                    }
                }
                HospitalRecyclerAdapter hospitalRecyclerAdapter = new HospitalRecyclerAdapter(getActivity(), hospitals);

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
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hospitalRecyclerAdapter.getFilter().filter(s);
                                        recyclerView.setAdapter(hospitalRecyclerAdapter);
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