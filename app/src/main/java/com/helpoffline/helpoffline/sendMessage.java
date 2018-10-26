package com.helpoffline.helpoffline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class sendMessage extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private String TAG = "sendMessage";

    Button sendbutton,appendLocationButton,broadcastLocationButton;
    EditText message;

    Double latitude,longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationManager mLocationManager;
    private LatLng mCurrentLocation, mLastLocation;
    private String locationData;
    private static final long INTERVAL = 500;
    private static final long FAST_INTERVAL = 1;

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_send_message);

        message = (EditText)findViewById(R.id.sendMessage);
        sendbutton = (Button)findViewById(R.id.button);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("message", message.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        appendLocationButton = (Button)findViewById(R.id.appendLocationButton);
        appendLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText(message.getText().insert(message.getSelectionStart()," ("+mCurrentLocation.latitude+","+mCurrentLocation.longitude+") "));
                message.setSelection(message.getText().length());
                message.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        broadcastLocationButton = (Button)findViewById(R.id.broadcastLocationButton);
        broadcastLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("message", "Help required at ("+mCurrentLocation.latitude+","+mCurrentLocation.longitude+")");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        latitude = -1.0;
        longitude = -1.0;

        buildGoogleApiClient();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria cri=new Criteria();
        mLocationManager.requestLocationUpdates(INTERVAL, FAST_INTERVAL, cri, this, null);
        String provider = mLocationManager.getBestProvider(cri,false);
        Location location = mLocationManager.getLastKnownLocation(provider);
        try {
            onLocationChanged(location);
        }catch (Exception e){
            Log.e(TAG, "No Last Location found...");
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("LOC","New loc : "+location.getLatitude()+","+location.getLongitude());
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        mCurrentLocation = latLng;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
