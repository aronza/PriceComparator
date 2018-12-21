package com.southstreet.nextbigthing;


import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lyft.lyftbutton.LyftButton;
import com.lyft.lyftbutton.RideParams;
import com.lyft.lyftbutton.RideTypeEnum;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.ServerTokenSession;

import com.lyft.networking.ApiConfig;

public class CompareActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);



        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Double desLatitude = intent.getDoubleExtra(MapsActivity.DES_LAT_MESSAGE,0);
        Double desLongitude = intent.getDoubleExtra(MapsActivity.DES_LONG_MESSAGE,0);
        Double curLatitude = intent.getDoubleExtra(MapsActivity.CUR_LAT_MESSAGE,0);
        Double curLongitude = intent.getDoubleExtra(MapsActivity.CUR_LONG_MESSAGE,0);
        String address = intent.getStringExtra(MapsActivity.ADD_MESSAGE);
        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("OJIXSjent-mZAHu6jo6yZgqSSmvku2cp")
                // required for enhanced button features
                .setServerToken("7Obb6klA9LZxHrViNhzwHDjhQBgXiD6AKDD4Mym8")
                // required for implicit grant authentication
                //.setRedirectUri("<REDIRECT_URI>")
                // optional: set sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();
        UberSdk.initialize(config);



        // get the context by invoking ``getApplicationContext()``, ``getContext()``, ``getBaseContext()`` or ``this`` when in the activity class
        RideRequestButton requestButton = new RideRequestButton(this);
        // get your layout, for instance:
        LinearLayout layout = findViewById(R.id.layout);

        layout.addView(requestButton);

        RideParameters rideParams = new RideParameters.Builder()
                // Optional product_id from /v1/products endpoint (e.g. UberX). If not provided, most cost-efficient product will be used
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                // Required for price estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of dropoff location
                .setDropoffLocation(
                        desLatitude, desLongitude, "", address)
                // Required for pickup estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of pickup location
                .setPickupLocation(curLatitude, curLongitude, "", "")
                .build();
// set parameters for the RideRequestButton instance
        requestButton.setRideParameters(rideParams);

        ServerTokenSession session = new ServerTokenSession(config);
        requestButton.setSession(session);

        requestButton.loadRideInformation();

        //----------------------Lyft-----------------
        ApiConfig apiConfig = new ApiConfig.Builder()
                .setClientId("ytxgHiJV6Uj-")
                .setClientToken("BeC2HYyz1mt1cUFXVA1Gkp8Sy78ORnv59F8Us4kBJO6FkC7kY6H/JTiKQhcZ2PK24U2qUNPveystuNlirttp3ndN3xegNlUaUkLThqeyA5T6/LGt8ffGXYY=")
                .build();

        LyftButton lyftButton = findViewById(R.id.lyft_button);
        lyftButton.setApiConfig(apiConfig);

        RideParams.Builder rideParamsBuilder = new RideParams.Builder()
                .setPickupLocation(curLatitude, curLongitude)
                .setDropoffLocation(desLatitude, desLongitude);
        rideParamsBuilder.setRideTypeEnum(RideTypeEnum.CLASSIC);

        lyftButton.setRideParams(rideParamsBuilder.build());
        lyftButton.load();
    }



}
