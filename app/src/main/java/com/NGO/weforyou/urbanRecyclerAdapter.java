package com.NGO.weforyou;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class urbanRecyclerAdapter extends RecyclerView.Adapter<urbanRecyclerAdapter.ViewHolder> {
    Context context;
    private List<urbanmodel> urbanmodels;
    private List<urbanmodel> urbanmodelList;
    DatabaseReference reference;

    public urbanRecyclerAdapter(Context context, List<urbanmodel> urbanmodels) {
        this.urbanmodels = urbanmodels;
        this.urbanmodelList = urbanmodels;
        this.context =context;
    }

    @NonNull
    @Override
    public urbanRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_urbanrecycler, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull urbanRecyclerAdapter.ViewHolder viewHolder, int position) {
        urbanmodel urbanmodel = urbanmodels.get(position);
        viewHolder.name.setText(urbanmodel.getName());
        viewHolder.area.setText("Serving Area : " + urbanmodel.getArea());
        viewHolder.specialization.setText(urbanmodel.getSpecialization());
    }

    @Override
    public int getItemCount() {
        return urbanmodels.size();
    }

    public Filter getFilter() {
        return UrbanFilter;
    }

    private Filter UrbanFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<urbanmodel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(urbanmodelList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (urbanmodel urbanmodel : urbanmodelList) {
                    if (urbanmodel.getName() != null) {
                        if (urbanmodel.getName().toLowerCase().contains(filterPattern) || urbanmodel.getSpecialization().toLowerCase().contains(filterPattern)) {
                            filteredList.add(urbanmodel);
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
            urbanmodels.clear();
            urbanmodels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public void filterList(String city,String s) {
        if(city.equals("Select Taluka") && s.equals("All")){
            urbanmodels = new ArrayList<urbanmodel>();
            for (urbanmodel item : urbanmodelList) {
                    urbanmodels.add(item);
            }
            notifyDataSetChanged();
            Log.d(TAG, "filterList: 1 " + urbanmodels);
        }

        else if (!city.equals("Select Taluka") && s.equals("All")){
            urbanmodels = new ArrayList<urbanmodel>();
            for (urbanmodel item : urbanmodelList) {
                if (item.getArea().equals(city)) {
                    urbanmodels.add(item);
                }
            }
            notifyDataSetChanged();
            Log.d(TAG, "filterList: 2 " + urbanmodels);
        }
        else if (city.equals("Select Taluka") && !s.equals("All")){
            urbanmodels = new ArrayList<urbanmodel>();
            for (urbanmodel item : urbanmodelList) {
                if (item.getSpecialization().equals(s)) {
                    urbanmodels.add(item);
                }
            }
            notifyDataSetChanged();
            Log.d(TAG, "filterList: 3 " + urbanmodels);
        }
        else {
            urbanmodels = new ArrayList<urbanmodel>();
            for (urbanmodel item : urbanmodelList) {
                if (item.getArea().equals(city) && item.getSpecialization().equals(s)) {
                    urbanmodels.add(item);
                }
            }
        }
        notifyDataSetChanged();
        Log.d(TAG, "filterList: 4 " + urbanmodels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, area,specialization;
        Button call;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            name = itemView.findViewById(R.id.name);
            area = itemView.findViewById(R.id.area);
            call = itemView.findViewById(R.id.call);
            specialization = itemView.findViewById(R.id.specialization);
            call.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == call) {
                int position = getAdapterPosition();
                final urbanmodel urbanmodel = urbanmodelList.get(position);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + urbanmodel.getPhoneNumber()));
                context.startActivity(callIntent);
                reference=FirebaseDatabase.getInstance().getReference("Form").child(urbanmodel.getPhoneNumber());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("count").exists()){
                            String a=snapshot.child("count").getValue().toString();
                            int c=Integer.parseInt(a);
                            c=c+1;
                            String scount=String.valueOf(c);
                            HashMap hashMap=new HashMap();
                            hashMap.put("count",scount);
                            reference.updateChildren(hashMap);
                        }
                        else {
                            String one="1";
                            HashMap hashMap=new HashMap();
                            hashMap.put("count",one);
                            reference.updateChildren(hashMap);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }
    }
}



