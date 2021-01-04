package com.shariful.alquran2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuraAdapter extends RecyclerView.Adapter<SuraAdapter.MyViewHolder>  {
      Context context;
      List<SuraListModel> suraList;

      MediaPlayer mediaPlayer;

    public SuraAdapter(Context context, List<SuraListModel> suraList) {
        this.context = context;
        this.suraList = suraList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mediaPlayer = new MediaPlayer();

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.serialTv.setText(suraList.get(position).getSerial());
        holder.name_bnTv.setText(suraList.get(position).getName_bangla());
        holder.meaning_bnTv.setText(suraList.get(position).getMeaning_bangla());
        holder.name_enTv.setText(suraList.get(position).getName_english());
        holder.meaning_enTv.setText(suraList.get(position).getMeaning_english());
        holder.placeTv.setText(suraList.get(position).getAyatNum());
        holder.ayatNumTv.setText(suraList.get(position).getPlace());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 int id = suraList.get(position).getId();
                 String suraNameBangla = suraList.get(position).getName_bangla();
                 String place = suraList.get(position).getPlace();
                 String totalAyat = suraList.get(position).getAyatNum();
                 String arbiAudio = suraList.get(position).getArbi_audioUrl();
                 String banglaAudio = suraList.get(position).getBangla_audioUrl();
                 String englishName = suraList.get(position).getName_english();
                 String banglaName = suraList.get(position).getName_bangla();

               //go to second activity with click id value
                 Intent intent = new Intent(SuraAdapter.this.context,SecondActivity.class);
                 intent.putExtra("tag",id);
                 intent.putExtra("bangla",suraNameBangla);
                 intent.putExtra("place",place);
                 intent.putExtra("ayat",totalAyat);
                 intent.putExtra("arbiUrl",arbiAudio);
                 intent.putExtra("banglaUrl",banglaAudio);
                 intent.putExtra("englishName",englishName);
                 intent.putExtra("banglaName",banglaName);

                 SuraAdapter.this.context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return suraList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serialTv,name_bnTv,meaning_bnTv,name_enTv,meaning_enTv,placeTv,ayatNumTv;
        ImageView playPauseBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            serialTv =itemView.findViewById(R.id.serial_id);
            name_bnTv = itemView.findViewById(R.id.name_banglaID);
            meaning_bnTv = itemView.findViewById(R.id.meaning_banglaID);
            name_enTv = itemView.findViewById(R.id.name_englishID);
            meaning_enTv = itemView.findViewById(R.id.meaning_englishID);
            placeTv = itemView.findViewById(R.id.placeID);
            ayatNumTv = itemView.findViewById(R.id.ayatID);
            playPauseBtn = itemView.findViewById(R.id.playPauseBtn_id);


        }
    }

    public void setFilter(List<SuraListModel> newList) {
        this.suraList = new ArrayList();
        this.suraList.addAll(newList);
        notifyDataSetChanged();
    }



    

}
