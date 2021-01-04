package com.shariful.alquran2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder>   {
    Context context;
    List<DetailsModel> detailsList;

    public DetailsAdapter(Context context, List<DetailsModel> detailsList) {
        this.context = context;
        this.detailsList = detailsList;

    }

    @NonNull
    @Override
    public DetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.details_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.MyViewHolder holder, int position) {
        holder.ayatNumberTV.setText(detailsList.get(position).getVerseid());
        holder.arbiTV.setText(detailsList.get(position).getArbitxt());
        holder.banglaTv.setText("উচ্চারনঃ "+detailsList.get(position).getBanglatxt());
        holder.banglaMeaningTv.setText("অর্থঃ "+detailsList.get(position).getBanglameaning());
        holder.englishTxtTv.setText(detailsList.get(position).getEnglishmeaning());



    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView arbiTV,banglaTv,banglaMeaningTv,englishTxtTv,ayatNumberTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            arbiTV = itemView.findViewById(R.id.arbiTXT_id);
            banglaTv = itemView.findViewById(R.id.banla_meaningTXT_Id);
            banglaMeaningTv = itemView.findViewById(R.id.english_meaningTXT_Id);
            englishTxtTv = itemView.findViewById(R.id.englishTxtID);
            ayatNumberTV = itemView.findViewById(R.id.ayatNumberID);

        }
    }
    public void setFilter(List<DetailsModel> newList) {
        this.detailsList = new ArrayList();
        this.detailsList.addAll(newList);
        notifyDataSetChanged();

    }
    
}
