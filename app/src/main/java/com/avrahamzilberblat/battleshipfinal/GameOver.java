package com.avrahamzilberblat.battleshipfinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GameOver extends AppCompatActivity {
    private DatabaseHelper mDatabasehelper;
    private String levelDifficulty;
    private boolean isPlayerWon = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private Double latitude;
    private  Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        final Bundle extras = getIntent().getExtras();

        TextView text = (TextView) findViewById(R.id.outcome);
        String output = (String) extras.get("text");

        text.setTextColor(Color.BLUE);
        text.setText(output);

        ImageView grid=findViewById(R.id.imageView2);
        grid.setBackgroundResource(R.drawable.firework);
        AnimationDrawable anim = (AnimationDrawable) grid.getBackground();
        anim.start();

        final Intent intent = new Intent(this, MainActivity.class);
        final Intent intent1 = new Intent(this, gameActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (extras != null) {
            levelDifficulty = extras.getString("levelDifficulty");
        }
        isPlayerWon = extras.getBoolean("isPlayerWon");
        int numOfButtons = extras.getInt("numOfButtons");
        int numOfShips = extras.getInt("numOfShips");
        int buttonCount = extras.getInt("shipCounter");
        final double ratio = extras.getDouble("ratioW_L");


        intent1.putExtra("numOfButtons", numOfButtons);
        intent1.putExtra("numOfShips", numOfShips);
        intent1.putExtra("shipCounter", buttonCount);

        Button mainMenu = findViewById(R.id.mainMenu);
        Button playAgain = findViewById(R.id.playAgain);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);
                finish();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(intent1);
                finish();

            }
        });
        if (isPlayerWon == true) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                loc=location;
                                }
                        }
                    });
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("Please enter your Name")
                    .setView(input)
                    .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Editable myEditTextView = input.getText();
                           addData(levelDifficulty, new PlayerDetails(myEditTextView.toString(), ratio,loc));
                        }
                    })
                    .show();
        }

    }
    public void addData(String tableName,PlayerDetails playerDetails)
    {
        mDatabasehelper=new DatabaseHelper(this,tableName);

        boolean insertData=mDatabasehelper.addData(tableName,playerDetails);

        if(insertData)
        {
            Toast.makeText(this, "Data Successfully added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Something Went Wrong ", Toast.LENGTH_SHORT).show();

        }
    }

}
