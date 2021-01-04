package com.shariful.alquran2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final int PERMISION_STORAGE_CODE = 1000;
    private static final int WRITE_PERMISION = 1001 ;

    String tag;
    String arbiAudioUrl;
    String banglaAudioUrl;

    String suraName;
    String suraNameBangla;

    private DatabaseAccess dbAccess;
    private DetailsAdapter detailsAdapter;
    private List<DetailsModel> suraDetailsList;
    private RecyclerView recyclerView_detailsList;

    //Media Player
    TextView currentTimeTV,totalTimeTV;
    SeekBar seekBar;
    MediaPlayer mediaPlayer ;
    LinearLayout playerLayout;
    ImageView playBtn;
    Toolbar toolbar;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

         Intent intent = getIntent();
         tag =String.valueOf(intent.getIntExtra("tag",0));
         String bangla =intent.getStringExtra("bangla");
         String place =intent.getStringExtra("place");
         String ayat =intent.getStringExtra("ayat");
         suraName =intent.getStringExtra("englishName");
         suraNameBangla =intent.getStringExtra("banglaName");

         arbiAudioUrl =intent.getStringExtra("arbiUrl");
         banglaAudioUrl =intent.getStringExtra("banglaUrl");

        ActionBar supportActionBar = getSupportActionBar();
        getSupportActionBar().setTitle("সূরা "+bangla);
        supportActionBar.setSubtitle("মোট আয়াত "+place+", "+ayat+"য় অবতীর্ণ");


        recyclerView_detailsList = findViewById(R.id.recyclerView_detailsID);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_detailsList.setLayoutManager(layoutManager);
        suraDetailsList =new ArrayList<>();


        dbAccess = new DatabaseAccess(this);
        suraDetailsList =dbAccess.getSuraDetails(tag); //ok
        detailsAdapter = new DetailsAdapter(SecondActivity.this,suraDetailsList);
        recyclerView_detailsList.setAdapter(detailsAdapter);

        //for media player
        currentTimeTV = findViewById(R.id.currentTimeTv_id);
        totalTimeTV = findViewById(R.id.totaltimeTv_id);
        seekBar = findViewById(R.id.seekbar_id);
        playerLayout = findViewById(R.id.playerLayout_id);
        playBtn = findViewById(R.id.playBtnID);
        mediaPlayer = new MediaPlayer();
        seekBar.setMax(100);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    playBtn.setImageResource(R.drawable.ic_play_blue);
                    updateSeekbar();
                }
                else{
                    mediaPlayer.start();
                    playBtn.setImageResource(R.drawable.ic_pause_blue);
                    // this.toolbar.getMenu().findItem(R.id.playBtn_id).setIcon(R.drawable.ic_pause);
                    updateSeekbar();
                }

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem searchViewItem =menu.findItem(R.id.search_suraId);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("আয়াত নং লিখুন");
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<DetailsModel> newList = new ArrayList();
        for (DetailsModel modelList : this.suraDetailsList) {
            if (modelList.getVerseid().toLowerCase().contains(newText)) {
                newList.add(modelList);
            }
        }
        this.detailsAdapter.setFilter(newList);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id==R.id.playBtn_id){
            showDialog();
        }
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }


    //Audio Player Section
    private void prepareMediaPlayer(String url){
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            totalTimeTV.setText(miliSecondsToTimer(mediaPlayer.getDuration()));

        }
        catch (Exception e){
           // Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
           updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
          currentTimeTV.setText(miliSecondsToTimer(currentDuration));
        }
    };

    private String miliSecondsToTimer(long miliSeconds){
        String timerString = "";
        String secondsString;

        int houres = (int)(miliSeconds /(1000*60*60));
        int minutes = (int)(miliSeconds %(1000*60*60))/(1000*60);
        int seconds = (int)((miliSeconds % (1000*60*60))%(1000*60)/1000);
        if (houres>0){
            timerString = houres+":";
        }
        if (seconds<10) {
            secondsString = "0" + seconds;
        }
        else{
            secondsString = "" + seconds;
        }
        timerString = timerString + minutes +":"+secondsString;
        return timerString;
    }

    private  void updateSeekbar(){
        if (mediaPlayer.isPlaying()){
            seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }

    }
    public void onBackPressed() {
        try {
            if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    //Check Internet Connection
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    //Alert dialog for no internet connection
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Turn On Internet !!\nPress ok to Exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 builder.setCancelable(true);
               // finish();
            }
        });

        return builder;
    }

    //alertdialog for download and play Audio
    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog, null);

        Button playArbiBtn = view.findViewById(R.id.playArbi_id);
        Button playArbiBanglaBtn = view.findViewById(R.id.playArbiBangla_id);
        Button downloadArbiBtn = view.findViewById(R.id.downloadArbi_id);
        Button downloadArbiBanglaBtn = view.findViewById(R.id.downloadArbiBangla_id);

        AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
        alertDialog.show();

        playArbiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //
                mediaPlayer.stop();

                if (isConnected(SecondActivity.this)){
                    prepareMediaPlayer(arbiAudioUrl);
                    playerLayout.setVisibility(View.VISIBLE);
                     if (mediaPlayer.isPlaying()){
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        playBtn.setImageResource(R.drawable.ic_play_blue);
                        updateSeekbar();
                    }
                    else{
                        mediaPlayer.start();
                        playBtn.setImageResource(R.drawable.ic_pause_blue);
                        // this.toolbar.getMenu().findItem(R.id.playBtn_id).setIcon(R.drawable.ic_pause);
                        updateSeekbar();
                    }
                }
                else{
                    buildDialog(SecondActivity.this).show();
                }
                  alertDialog.dismiss();
            }
        });

        playArbiBanglaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //
                mediaPlayer.stop();
                if (isConnected(SecondActivity.this)){

                    prepareMediaPlayer(banglaAudioUrl);
                    playerLayout.setVisibility(View.VISIBLE);
                     if (mediaPlayer.isPlaying()){
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        playBtn.setImageResource(R.drawable.ic_play_blue);
                        updateSeekbar();
                    }
                    else{
                        mediaPlayer.start();
                        playBtn.setImageResource(R.drawable.ic_pause_blue);
                        updateSeekbar();
                    }
                }
                else{
                    buildDialog(SecondActivity.this).show();
                }
                alertDialog.dismiss();
            }
        });

        downloadArbiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                        String fileName = suraName+".mp3";
                        download(fileName,arbiAudioUrl);
                    }
                    else {
                        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISION );

                    }
                }
                else
                {
                    String fileName = suraName+".mp3";
                    download(fileName,arbiAudioUrl);
                }

                alertDialog.dismiss();
            }
        });


        downloadArbiBanglaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                            String fileName = suraNameBangla+".mp3";
                            download(fileName,banglaAudioUrl);
                        }
                        else {
                            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISION );

                        }
                    }
                    else
                    {
                        String fileName = suraNameBangla+".mp3";
                        download(fileName,banglaAudioUrl);
                    }

                alertDialog.dismiss();
            }
        });

    }


    // new file download system
    private void download(String fileName, String url) {
        Uri downloadUri= Uri.parse(url);
        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        try {
            if (manager != null){
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setTitle(fileName)
                        .setDescription("Downloading"+fileName)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
                        .setMimeType(getMimType(downloadUri));
                manager.enqueue(request);
                Toast.makeText(this, "Download Started !", Toast.LENGTH_SHORT).show();

            }
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW,downloadUri);
                startActivity(intent);
            }
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }



    } //start

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==WRITE_PERMISION){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permision granted from popup, now perform download
                String fileName = suraName+".mp3";
                download(fileName,arbiAudioUrl);
            }
            else{
                Toast.makeText(this, "Permision Denied !!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private  String getMimType(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return  mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }  //end


}