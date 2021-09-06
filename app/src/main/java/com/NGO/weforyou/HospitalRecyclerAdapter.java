package com.NGO.weforyou;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HospitalRecyclerAdapter extends RecyclerView.Adapter<HospitalRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Hospital> hospitals;
    private List<Hospital> hospitalListFull;

    public HospitalRecyclerAdapter(Context context, List<Hospital> hospitals) {
        this.context = context;
        this.hospitals = hospitals;
        hospitalListFull = new ArrayList<>(hospitals);
    }

    @NonNull
    @Override
    public HospitalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.hospital, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalRecyclerAdapter.ViewHolder viewHolder, int position) {
        final Hospital hospital = hospitals.get(position);

        viewHolder.center.setText(hospital.getCenter());
        viewHolder.name.setText(hospital.getName());
        viewHolder.adress.setText(hospital.getAdress());
        viewHolder.availableBeds.setText(hospital.getAvailableBeds());
        viewHolder.totalBeds.setText(hospital.getTotalBeds());
        viewHolder.availableOxygen.setText(hospital.getAvailableOxygen());
        viewHolder.totalOxygen.setText(hospital.getTotalOxygen());
        viewHolder.availableIcus.setText(hospital.getAvailableIcus());
        viewHolder.totalIcus.setText(hospital.getTotalIcus());
        viewHolder.availableVentilators.setText(hospital.getAvailableVentilators());
        viewHolder.totalVentilators.setText(hospital.getTotalVentilators());

        if(hospital.getType().equals("Government")){
            viewHolder.type.setText("Government (Free)");
        }
        else{
            viewHolder.type.setText("Private (Paid)");
        }

        long currenttime = System.currentTimeMillis();
        long t = hospital.getTime();

        if(TimeUnit.MILLISECONDS.toSeconds(currenttime - t) < 60){
            if(TimeUnit.MILLISECONDS.toSeconds(currenttime - t) < 2){
                int f = (int) TimeUnit.MILLISECONDS.toSeconds(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f)+" second ago");
            }
            else {
                int f = (int) TimeUnit.MILLISECONDS.toSeconds(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f) + " seconds ago");
            }
        }
        else if(TimeUnit.MILLISECONDS.toMinutes(currenttime - t) < 60){
            if(TimeUnit.MILLISECONDS.toMinutes(currenttime - t) < 2){
                int f = (int) TimeUnit.MILLISECONDS.toMinutes(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f)+" minute ago");
            }
            else {
                int f = (int) TimeUnit.MILLISECONDS.toMinutes(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f) + " minutes ago");
            }
        }
        else if(TimeUnit.MILLISECONDS.toHours(currenttime - t) < 24){
            if(TimeUnit.MILLISECONDS.toHours(currenttime - t) < 2){
                int f = (int) TimeUnit.MILLISECONDS.toHours(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f)+" hour ago");
            }
            else{
                int f = (int) TimeUnit.MILLISECONDS.toHours(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f)+" hours ago");
            }

        }
        else if(TimeUnit.MILLISECONDS.toDays(currenttime - t) < 7){
            if(TimeUnit.MILLISECONDS.toDays(currenttime - t) < 2) {
                int f = (int) TimeUnit.MILLISECONDS.toDays(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f) + " day ago");
            }
            else {
                int f = (int) TimeUnit.MILLISECONDS.toDays(currenttime - t);
                viewHolder.time.setText("Updated " + String.valueOf(f) + " days ago");
            }
        }
        else {
            viewHolder.time.setText("Not updated recently");
        }

        long a = Long.parseLong(hospital.getAvailableBeds());
        long b = Long.parseLong(hospital.getTotalBeds());
        long c = Long.parseLong(hospital.getAvailableOxygen());
        long d = Long.parseLong(hospital.getTotalOxygen());
        long e = Long.parseLong(hospital.getAvailableIcus());
        long f = Long.parseLong(hospital.getTotalIcus());
        long g = Long.parseLong(hospital.getAvailableVentilators());
        long h = Long.parseLong(hospital.getTotalVentilators());

        double i= ((double)a/b)*100;
        double j= ((double)c/d)*100;
        double k= ((double)e/f)*100;
        double l= ((double)g/h)*100;

        if(i <= 33 || b == 0){
            viewHolder.cardView1.setCardBackgroundColor(Color.parseColor("#dc3545"));
        }

        else  if(i <= 66){
            viewHolder.cardView1.setCardBackgroundColor(Color.parseColor("#ffc107"));
        }

        else  if(i <= 100){
            viewHolder.cardView1.setCardBackgroundColor(Color.parseColor("#28a745"));
        }

        if(j <= 33 || d == 0){
            viewHolder.cardView2.setCardBackgroundColor(Color.parseColor("#dc3545"));
        }

        else  if(j <= 66){
            viewHolder.cardView2.setCardBackgroundColor(Color.parseColor("#ffc107"));
        }

        else  if(j <= 100){
            viewHolder.cardView2.setCardBackgroundColor(Color.parseColor("#28a745"));
        }

        if(k <= 33 || f==0){
            viewHolder.cardView3.setCardBackgroundColor(Color.parseColor("#dc3545"));
        }

        else  if(k <= 66){
            viewHolder.cardView3.setCardBackgroundColor(Color.parseColor("#ffc107"));
        }

        else  if(k <= 100){
            viewHolder.cardView3.setCardBackgroundColor(Color.parseColor("#28a745"));
        }

        if(l <= 33 || h==0){
            viewHolder.cardView4.setCardBackgroundColor(Color.parseColor("#dc3545"));
        }

        else  if(l <= 66){
            viewHolder.cardView4.setCardBackgroundColor(Color.parseColor("#ffc107"));
        }

        else  if(l <= 100){
            viewHolder.cardView4.setCardBackgroundColor(Color.parseColor("#28a745"));
        }
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public Filter getFilter() {
        return HospitalFilter;
    }

    private Filter HospitalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Hospital> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(hospitalListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Hospital hospital : hospitalListFull) {
                    if (hospital.getName() != null) {
                        if (hospital.getName().toLowerCase().contains(filterPattern) || hospital.getTaluka().toLowerCase().contains(filterPattern)) {
                            filteredList.add(hospital);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hospitals.clear();
            hospitals.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, adress, totalOxygen, availableOxygen, totalBeds, availableBeds, totalIcus, availableIcus, totalVentilators, availableVentilators, type, time, center;
        Button call, link;
        View view;
        CardView cardView1, cardView2, cardView3, cardView4;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            center = itemView.findViewById(R.id.center_type);
            cardView1 = itemView.findViewById(R.id.card1);
            cardView2 = itemView.findViewById(R.id.card2);
            cardView3 = itemView.findViewById(R.id.card3);
            cardView4 = itemView.findViewById(R.id.card4);
            name = itemView.findViewById(R.id.name);
            adress = itemView.findViewById(R.id.address);
            link = itemView.findViewById(R.id.location);
            totalOxygen = itemView.findViewById(R.id.total_withs);
            availableOxygen = itemView.findViewById(R.id.available_withs);
            totalBeds = itemView.findViewById(R.id.total_withouts);
            availableBeds = itemView.findViewById(R.id.available_withouts);
            totalIcus = itemView.findViewById(R.id.total_icus);
            availableIcus = itemView.findViewById(R.id.available_icus);
            totalVentilators = itemView.findViewById(R.id.total_ventilators);
            availableVentilators = itemView.findViewById(R.id.available_ventilators);
            call = itemView.findViewById(R.id.call);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);

            call.setOnClickListener(this);
            link.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == call) {
                int position = getAdapterPosition();
                final Hospital hospital = hospitals.get(position);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + hospital.getMobile()));
                context.startActivity(callIntent);

            }
            if (v == link){
                int position = getAdapterPosition();
                final Hospital hospital = hospitals.get(position);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(hospital.getLink()));
                context.startActivity(intent);
            }
        }
    }
}