package com.shariful.alquran2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private DatabaseAccess dbAccess;
    private SuraAdapter suraAdapter2;
    private List<SuraListModel> suraList;
    private RecyclerView recyclerView_suraList;

    AlertDialog alertDialog;

    //Ads
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_suraList=findViewById(R.id.recyclerView_suralist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_suraList.setLayoutManager(layoutManager);

         suraList =new ArrayList<>();

         dbAccess = new DatabaseAccess(this);
         suraList =dbAccess.getSuraList();

         suraAdapter2 = new SuraAdapter(MainActivity.this,suraList);
         recyclerView_suraList.setAdapter(suraAdapter2);

         //Facebook Ads section
        adView = new AdView(this, LanguageName.FB_BANNER_ID, AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = findViewById(R.id.favourite_banner_container);
        adContainer.addView(adView);
        adView.loadAd();
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchViewItem =menu.findItem(R.id.search_suraId);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<SuraListModel> newList = new ArrayList();
        for (SuraListModel modelList : this.suraList) {
            String serial = modelList.getSerial().toLowerCase();
            String bang = modelList.getName_bangla().toLowerCase();
            String eng = modelList.getName_english().toLowerCase();
            if (serial.contains(newText)) {
                newList.add(modelList);
            } else if (bang.contains(newText)) {
                newList.add(modelList);
            } else if (eng.contains(newText)) {
                newList.add(modelList);
            }
        }
        this.suraAdapter2.setFilter(newList);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id==R.id.aboutUsID){
          showDialog();
        }
        if (id==R.id.shareID){
            Intent myIntent=new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String body="http://play.google.com/store/apps/details?id=com.shariful.alquran2";
            myIntent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myIntent,"Share Using"));
            return true;
        }

        if (id==R.id.privacyID){
               startActivity(new Intent(Intent.ACTION_VIEW,
                       Uri.parse("https://google.com")));

        }
        if (id==R.id.rateUsID){

            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //dialog
    void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.aboutus_dialog, null);
        alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();
    }




}



