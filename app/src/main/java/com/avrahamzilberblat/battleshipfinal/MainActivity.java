package com.avrahamzilberblat.battleshipfinal;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static com.avrahamzilberblat.battleshipfinal.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.avrahamzilberblat.battleshipfinal.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private Button topPlayersButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Fragment mFragment;
    private boolean rmvFlag=false;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.newGameButton);
        radioGroup = findViewById(R.id.radioGroup);
        topPlayersButton=findViewById(R.id.showTopPlayers);

        boolean check=checkMapServices();
        if(check==true)
        {
            getLocationPermission();
        }
        //check whether to remove to replace the fragment
        topPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rmvFlag==true) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                    rmvFlag=false;
                }
                else{

                    rmvFlag=true;
                    mFragment = new SQliteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewside, mFragment).commit();
                }
            }
        });
        //Radio Buttons options
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), gameActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                switch (radioButton.getText().toString()) {
                    case "Easy":
                        intent.putExtra("levelDifficulty", Constants.TABLE_NAME_Easy);
                        intent.putExtra("numOfButtons", 4);
                        intent.putExtra("numOfShips", 1);
                        intent.putExtra("shipCounter",1);
                        break;

                    case "Normal":
                        intent.putExtra("levelDifficulty", Constants.TABLE_NAME_Normal);
                        intent.putExtra("numOfButtons", 5);
                        intent.putExtra("numOfShips", 3);
                        intent.putExtra("shipCounter",6);

                        break;

                    case "Hard":
                        intent.putExtra("levelDifficulty", Constants.TABLE_NAME_Hard);
                        intent.putExtra("numOfButtons", 6);
                        intent.putExtra("numOfShips", 4);
                        intent.putExtra("shipCounter",7);
                        break;
                }


                startActivity(intent);
                finish();
            }


        });


    }



    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){

            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, Constants.ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    public boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                getLocationPermission();
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG);
                    }
                else{
                    getLocationPermission();
                }
            }
        }

    }
}
