package com.NGO.weforyou.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.NGO.weforyou.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Summary extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.summary, container, false);

        TextView availableBedTotal = root.findViewById(R.id.available_withouts_total);
        TextView totalBedTotal = root.findViewById(R.id.total_withouts_total);
        TextView availableOxygenTotal = root.findViewById(R.id.available_withs_total);
        TextView totalOxygenTotal = root.findViewById(R.id.total_withs_total);
        TextView availableIcuTotal = root.findViewById(R.id.available_icus_total);
        TextView totalIcuTotal = root.findViewById(R.id.total_icus_total);
        TextView availableVentilatorTotal = root.findViewById(R.id.available_ventilators_total);
        TextView totalVentilatorTotal = root.findViewById(R.id.total_ventilators_total);

        CardView cardOneTotal = root.findViewById(R.id.card1_total);
        CardView cardTwoTotal = root.findViewById(R.id.card2_total);
        CardView cardThreeTotal = root.findViewById(R.id.card3_total);
        CardView cardFourTotal = root.findViewById(R.id.card4_total);

        TextView availableBedDhc = root.findViewById(R.id.available_withouts_dhc);
        TextView totalBedDhc = root.findViewById(R.id.total_withouts_dhc);
        TextView availableOxygenDhc = root.findViewById(R.id.available_withs_dhc);
        TextView totalOxygenDhc = root.findViewById(R.id.total_withs_dhc);
        TextView availableIcuDhc = root.findViewById(R.id.available_icus_dhc);
        TextView totalIcuDhc = root.findViewById(R.id.total_icus_dhc);
        TextView availableVentilatorDhc = root.findViewById(R.id.available_ventilators_dhc);
        TextView totalVentilatorDhc = root.findViewById(R.id.total_ventilators_dhc);

        CardView cardOneDhc = root.findViewById(R.id.card1_dhc);
        CardView cardTwoDhc = root.findViewById(R.id.card2_dhc);
        CardView cardThreeDhc = root.findViewById(R.id.card3_dhc);
        CardView cardFourDhc = root.findViewById(R.id.card4_dhc);

        TextView availableBedCcc = root.findViewById(R.id.available_withouts_ccc);
        TextView totalBedCcc = root.findViewById(R.id.total_withouts_ccc);
        TextView availableOxygenCcc = root.findViewById(R.id.available_withs_ccc);
        TextView totalOxygenCcc = root.findViewById(R.id.total_withs_ccc);
        TextView availableIcuCcc = root.findViewById(R.id.available_icus_ccc);
        TextView totalIcuCcc = root.findViewById(R.id.total_icus_ccc);
        TextView availableVentilatorCcc = root.findViewById(R.id.available_ventilators_ccc);
        TextView totalVentilatorCcc = root.findViewById(R.id.total_ventilators_ccc);

        CardView cardOneCcc = root.findViewById(R.id.card1_ccc);
        CardView cardTwoCcc = root.findViewById(R.id.card2_ccc);
        CardView cardThreeCcc = root.findViewById(R.id.card3_ccc);
        CardView cardFourCcc = root.findViewById(R.id.card4_ccc);

        TextView availableBedDhcc = root.findViewById(R.id.available_withouts_dhcc);
        TextView totalBedDhcc = root.findViewById(R.id.total_withouts_dhcc);
        TextView availableOxygenDhcc = root.findViewById(R.id.available_withs_dhcc);
        TextView totalOxygenDhcc = root.findViewById(R.id.total_withs_dhcc);
        TextView availableIcuDhcc = root.findViewById(R.id.available_icus_dhcc);
        TextView totalIcuDhcc = root.findViewById(R.id.total_icus_dhcc);
        TextView availableVentilatorDhcc = root.findViewById(R.id.available_ventilators_dhcc);
        TextView totalVentilatorDhcc = root.findViewById(R.id.total_ventilators_dhcc);

        CardView cardOneDhcc = root.findViewById(R.id.card1_dhcc);
        CardView cardTwoDhcc = root.findViewById(R.id.card2_dhcc);
        CardView cardThreeDhcc = root.findViewById(R.id.card3_dhcc);
        CardView cardFourDhcc = root.findViewById(R.id.card4_dhcc);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hospitals");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalBedsTotal=0, availableBedsTotal=0, totalOxygensTotal=0, availableOxygensTotal=0, totalIcusTotal=0, availableIcusTotal=0, totalVentilatorsTotal=0, availableVentilatorsTotal=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    totalBedsTotal += Integer.parseInt(dataSnapshot.child("totalBeds").getValue().toString());
                    availableBedsTotal += Integer.parseInt(dataSnapshot.child("availableBeds").getValue().toString());
                    totalOxygensTotal += Integer.parseInt(dataSnapshot.child("totalOxygen").getValue().toString());
                    availableOxygensTotal += Integer.parseInt(dataSnapshot.child("availableOxygen").getValue().toString());
                    totalIcusTotal += Integer.parseInt(dataSnapshot.child("totalIcus").getValue().toString());
                    availableIcusTotal += Integer.parseInt(dataSnapshot.child("availableIcus").getValue().toString());
                    totalVentilatorsTotal += Integer.parseInt(dataSnapshot.child("totalVentilators").getValue().toString());
                    availableVentilatorsTotal += Integer.parseInt(dataSnapshot.child("availableVentilators").getValue().toString());
                }
                double i= ((double)availableBedsTotal/totalBedsTotal)*100;
                double j= ((double)availableOxygensTotal/totalOxygensTotal)*100;
                double k= ((double)availableIcusTotal/totalIcusTotal)*100;
                double l= ((double)availableVentilatorsTotal/totalVentilatorsTotal)*100;

                if(i <= 33 || totalBedsTotal == 0){
                    cardOneTotal.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(i <= 66){
                    cardOneTotal.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(i <= 100){
                    cardOneTotal.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(j <= 33 || totalOxygensTotal == 0){
                    cardTwoTotal.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(j <= 66){
                    cardTwoTotal.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(j <= 100){
                    cardTwoTotal.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(k <= 33 || totalIcusTotal==0){
                    cardThreeTotal.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(k <= 66){
                    cardThreeTotal.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(k <= 100){
                    cardThreeTotal.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(l <= 33 || totalVentilatorsTotal==0){
                    cardFourTotal.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(l <= 66){
                    cardFourTotal.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(l <= 100){
                    cardFourTotal.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                totalBedTotal.setText(String.valueOf(totalBedsTotal));
                availableBedTotal.setText(String.valueOf(availableBedsTotal));
                totalOxygenTotal.setText(String.valueOf(totalOxygensTotal));
                availableOxygenTotal.setText(String.valueOf(availableOxygensTotal));
                totalIcuTotal.setText(String.valueOf(totalIcusTotal));
                availableIcuTotal.setText(String.valueOf(availableIcusTotal));
                totalVentilatorTotal.setText(String.valueOf(totalVentilatorsTotal));
                availableVentilatorTotal.setText(String.valueOf(availableVentilatorsTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1 = databaseReference.orderByChild("center").equalTo("CCC");
        Query query2 = databaseReference.orderByChild("center").equalTo("DCHC");
        Query query3 = databaseReference.orderByChild("center").equalTo("DCH");

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalBedsCcc=0, availableBedsCcc=0, totalOxygensCcc=0, availableOxygensCcc=0, totalIcusCcc=0, availableIcusCcc=0, totalVentilatorsCcc=0, availableVentilatorsCcc=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    totalBedsCcc += Integer.parseInt(dataSnapshot.child("totalBeds").getValue().toString());
                    availableBedsCcc += Integer.parseInt(dataSnapshot.child("availableBeds").getValue().toString());
                    totalOxygensCcc += Integer.parseInt(dataSnapshot.child("totalOxygen").getValue().toString());
                    availableOxygensCcc += Integer.parseInt(dataSnapshot.child("availableOxygen").getValue().toString());
                    totalIcusCcc += Integer.parseInt(dataSnapshot.child("totalIcus").getValue().toString());
                    availableIcusCcc += Integer.parseInt(dataSnapshot.child("availableIcus").getValue().toString());
                    totalVentilatorsCcc += Integer.parseInt(dataSnapshot.child("totalVentilators").getValue().toString());
                    availableVentilatorsCcc += Integer.parseInt(dataSnapshot.child("availableVentilators").getValue().toString());
                }
                double i= ((double)availableBedsCcc/totalBedsCcc)*100;
                double j= ((double)availableOxygensCcc/totalOxygensCcc)*100;
                double k= ((double)availableIcusCcc/totalIcusCcc)*100;
                double l= ((double)availableVentilatorsCcc/totalVentilatorsCcc)*100;

                if(i <= 33 || totalBedsCcc == 0){
                    cardOneCcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(i <= 66){
                    cardOneCcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(i <= 100){
                    cardOneCcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(j <= 33 || totalOxygensCcc == 0){
                    cardTwoCcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(j <= 66){
                    cardTwoCcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(j <= 100){
                    cardTwoCcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(k <= 33 || totalIcusCcc==0){
                    cardThreeCcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(k <= 66){
                    cardThreeCcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(k <= 100){
                    cardThreeCcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(l <= 33 || totalVentilatorsCcc==0){
                    cardFourCcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(l <= 66){
                    cardFourCcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(l <= 100){
                    cardFourCcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                totalBedCcc.setText(String.valueOf(totalBedsCcc));
                availableBedCcc.setText(String.valueOf(availableBedsCcc));
                totalOxygenCcc.setText(String.valueOf(totalOxygensCcc));
                availableOxygenCcc.setText(String.valueOf(availableOxygensCcc));
                totalIcuCcc.setText(String.valueOf(totalIcusCcc));
                availableIcuCcc.setText(String.valueOf(availableIcusCcc));
                totalVentilatorCcc.setText(String.valueOf(totalVentilatorsCcc));
                availableVentilatorCcc.setText(String.valueOf(availableVentilatorsCcc));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalBedsDhc=0, availableBedsDhc=0, totalOxygensDhc=0, availableOxygensDhc=0, totalIcusDhc=0, availableIcusDhc=0, totalVentilatorsDhc=0, availableVentilatorsDhc=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    totalBedsDhc += Integer.parseInt(dataSnapshot.child("totalBeds").getValue().toString());
                    availableBedsDhc += Integer.parseInt(dataSnapshot.child("availableBeds").getValue().toString());
                    totalOxygensDhc += Integer.parseInt(dataSnapshot.child("totalOxygen").getValue().toString());
                    availableOxygensDhc += Integer.parseInt(dataSnapshot.child("availableOxygen").getValue().toString());
                    totalIcusDhc += Integer.parseInt(dataSnapshot.child("totalIcus").getValue().toString());
                    availableIcusDhc += Integer.parseInt(dataSnapshot.child("availableIcus").getValue().toString());
                    totalVentilatorsDhc += Integer.parseInt(dataSnapshot.child("totalVentilators").getValue().toString());
                    availableVentilatorsDhc += Integer.parseInt(dataSnapshot.child("availableVentilators").getValue().toString());
                }
                double i= ((double)availableBedsDhc/totalBedsDhc)*100;
                double j= ((double)availableOxygensDhc/totalOxygensDhc)*100;
                double k= ((double)availableIcusDhc/totalIcusDhc)*100;
                double l= ((double)availableVentilatorsDhc/totalVentilatorsDhc)*100;

                if(i <= 33 || totalBedsDhc == 0){
                    cardOneDhc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(i <= 66){
                    cardOneDhc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(i <= 100){
                    cardOneDhc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(j <= 33 || totalOxygensDhc == 0){
                    cardTwoDhc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(j <= 66){
                    cardTwoDhc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(j <= 100){
                    cardTwoDhc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(k <= 33 || totalIcusDhc==0){
                    cardThreeDhc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(k <= 66){
                    cardThreeDhc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(k <= 100){
                    cardThreeDhc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(l <= 33 || totalVentilatorsDhc==0){
                    cardFourDhc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(l <= 66){
                    cardFourDhc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(l <= 100){
                    cardFourDhc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                totalBedDhc.setText(String.valueOf(totalBedsDhc));
                availableBedDhc.setText(String.valueOf(availableBedsDhc));
                totalOxygenDhc.setText(String.valueOf(totalOxygensDhc));
                availableOxygenDhc.setText(String.valueOf(availableOxygensDhc));
                totalIcuDhc.setText(String.valueOf(totalIcusDhc));
                availableIcuDhc.setText(String.valueOf(availableIcusDhc));
                totalVentilatorDhc.setText(String.valueOf(totalVentilatorsDhc));
                availableVentilatorDhc.setText(String.valueOf(availableVentilatorsDhc));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalBedsDhcc=0, availableBedsDhcc=0, totalOxygensDhcc=0, availableOxygensDhcc=0, totalIcusDhcc=0, availableIcusDhcc=0, totalVentilatorsDhcc=0, availableVentilatorsDhcc=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    totalBedsDhcc += Integer.parseInt(dataSnapshot.child("totalBeds").getValue().toString());
                    availableBedsDhcc += Integer.parseInt(dataSnapshot.child("availableBeds").getValue().toString());
                    totalOxygensDhcc += Integer.parseInt(dataSnapshot.child("totalOxygen").getValue().toString());
                    availableOxygensDhcc += Integer.parseInt(dataSnapshot.child("availableOxygen").getValue().toString());
                    totalIcusDhcc += Integer.parseInt(dataSnapshot.child("totalIcus").getValue().toString());
                    availableIcusDhcc += Integer.parseInt(dataSnapshot.child("availableIcus").getValue().toString());
                    totalVentilatorsDhcc += Integer.parseInt(dataSnapshot.child("totalVentilators").getValue().toString());
                    availableVentilatorsDhcc += Integer.parseInt(dataSnapshot.child("availableVentilators").getValue().toString());
                }
                double i= ((double)availableBedsDhcc/totalBedsDhcc)*100;
                double j= ((double)availableOxygensDhcc/totalOxygensDhcc)*100;
                double k= ((double)availableIcusDhcc/totalIcusDhcc)*100;
                double l= ((double)availableVentilatorsDhcc/totalVentilatorsDhcc)*100;

                if(i <= 33 || totalBedsDhcc == 0){
                    cardOneDhcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(i <= 66){
                    cardOneDhcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(i <= 100){
                    cardOneDhcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(j <= 33 || totalOxygensDhcc == 0){
                    cardTwoDhcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(j <= 66){
                    cardTwoDhcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(j <= 100){
                    cardTwoDhcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(k <= 33 || totalIcusDhcc==0){
                    cardThreeDhcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(k <= 66){
                    cardThreeDhcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(k <= 100){
                    cardThreeDhcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                if(l <= 33 || totalVentilatorsDhcc==0){
                    cardFourDhcc.setCardBackgroundColor(Color.parseColor("#dc3545"));
                }

                else  if(l <= 66){
                    cardFourDhcc.setCardBackgroundColor(Color.parseColor("#ffc107"));
                }

                else  if(l <= 100){
                    cardFourDhcc.setCardBackgroundColor(Color.parseColor("#28a745"));
                }

                totalBedDhcc.setText(String.valueOf(totalBedsDhcc));
                availableBedDhcc.setText(String.valueOf(availableBedsDhcc));
                totalOxygenDhcc.setText(String.valueOf(totalOxygensDhcc));
                availableOxygenDhcc.setText(String.valueOf(availableOxygensDhcc));
                totalIcuDhcc.setText(String.valueOf(totalIcusDhcc));
                availableIcuDhcc.setText(String.valueOf(availableIcusDhcc));
                totalVentilatorDhcc.setText(String.valueOf(totalVentilatorsDhcc));
                availableVentilatorDhcc.setText(String.valueOf(availableVentilatorsDhcc));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}
