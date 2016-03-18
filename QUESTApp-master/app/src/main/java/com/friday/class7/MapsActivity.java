package com.friday.class7;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnPolygonClickListener {

    private static final String FIREBASE_URL = "https://smartpark1.firebaseio.com";
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest request;
    long[][][] parkSystem;
    long[] noZones;
    long noParks;
    Polygon[] polygons;
    Location myLocation;
    GroundOverlayOptions parkingMap;
    GroundOverlay imageOverlay;
    private ArrayList<Zone> zones = new ArrayList<Zone>();







    public void firebaseQuery(){
        Firebase parkArray = new Firebase(FIREBASE_URL);


        parkArray.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("ESTACIONAMIENTO: " + dataSnapshot.getChildrenCount());
                noParks = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        noZones = new long[(int)noParks];

        Firebase[] park = new Firebase[(int)noParks];
        for (int i = 0; i < noParks; i++){
            String temp = "Estacionamiento"+(i+1);
            park[i] = new Firebase(FIREBASE_URL).child(temp);

            final int finalI = i;
            park[i].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    noZones[finalI] = dataSnapshot.getChildrenCount();
                    System.out.println("noZones"+noZones[finalI]+"");

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        //testin purposes
        Firebase zone = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona0");
        zone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("children of zones"+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        parkSystem = new long[1][9][2];
        //orientado a objetos pendiente!!
        //corregir con datos dinámicos

        //(int)noZones[0]

        //implementar iteracion con json corregido Zona0 no ZonaA
        Firebase carLimit = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona0/carLimit");
        Firebase currentCars = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona0/currentCars");
        Firebase carLimit2 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona1/carLimit");
        Firebase currentCars2 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona1/currentCars");
        Firebase carLimit3 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona2/carLimit");
        Firebase currentCars3 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona2/currentCars");
        Firebase carLimit4 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona3/carLimit");
        Firebase currentCars4 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona3/currentCars");
        Firebase carLimit5 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona4/carLimit");
        Firebase currentCars5 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona4/currentCars");
        Firebase carLimit6 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona5/carLimit");
        Firebase currentCars6 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona5/currentCars");
        Firebase carLimit7 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona6/carLimit");
        Firebase currentCars7 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona6/currentCars");
        Firebase carLimit8 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona7/carLimit");
        Firebase currentCars8 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona7/currentCars");
        Firebase carLimit9 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona8/carLimit");
        Firebase currentCars9 = new Firebase(FIREBASE_URL).child("Estacionamiento1/Zona8/currentCars");

        carLimit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][0][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][0][1] = (long) dataSnapshot.getValue();
                polygons[0].setFillColor(evalColor(parkSystem[0][0][0],parkSystem[0][0][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][1][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][1][1] = (long) dataSnapshot.getValue();
                polygons[1].setFillColor(evalColor(parkSystem[0][1][0],parkSystem[0][1][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][2][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][2][1] = (long) dataSnapshot.getValue();
                polygons[2].setFillColor(evalColor(parkSystem[0][2][0],parkSystem[0][2][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][3][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][3][1] = (long) dataSnapshot.getValue();
                polygons[3].setFillColor(evalColor(parkSystem[0][3][0],parkSystem[0][3][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][4][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][4][1] = (long) dataSnapshot.getValue();
                polygons[4].setFillColor(evalColor(parkSystem[0][4][0],parkSystem[0][4][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][5][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][5][1] = (long) dataSnapshot.getValue();
                polygons[5].setFillColor(evalColor(parkSystem[0][5][0],parkSystem[0][5][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][6][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][6][1] = (long) dataSnapshot.getValue();
                polygons[6].setFillColor(evalColor( parkSystem[0][6][0],parkSystem[0][6][1] ));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][7][0] = (long) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][7][1] = (long) dataSnapshot.getValue();
                polygons[7].setFillColor(evalColor(parkSystem[0][7][0],parkSystem[0][7][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        carLimit9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][8][0] = (long) dataSnapshot.getValue();
                System.out.println("CarLimit "+parkSystem[0][8][0]+"");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        currentCars9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkSystem[0][8][1] = (long) dataSnapshot.getValue();
                System.out.println("Current "+parkSystem[0][8][1] + "");
                polygons[8].setFillColor(evalColor(parkSystem[0][8][0], parkSystem[0][8][1]));
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Firebase.setAndroidContext(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        parkSystem = new long[1][3][2];
        if(intent.getStringExtra("value") != null){
            loadLocalInfo();
            setLocalValues();
        }
        else{
            firebaseQuery();
        }

        startLocation();


    }

    private void setLocalValues() {
        for (int i=0; i<parkSystem.length; i++){
            for (int j = 0; j < parkSystem[0].length; j++){
                parkSystem[i][j][0] = zones.get(j).getMaxCar();
                parkSystem[i][j][1] = zones.get(j).getCurCar();

            }
        }
    }

    public void loadLocalInfo(){
        try{
            InputStream is = getResources().openRawResource(R.raw.parklog);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            StringTokenizer st = null;
            int row = 0;
            Zone tmp = null;
            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                if (tmp == null){
                    tmp = new Zone(st.nextToken(),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
                } else {
                    Zone tmp2 = new Zone(st.nextToken(),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
                    if(!tmp.getName().equals(tmp2.getName())){
                        zones.add(tmp);
                        System.out.println(tmp.getName()+" Max "+tmp.getMaxCar()+" Cur "+tmp.getCurCar());
                    }else{
                        tmp2.setCurCar(tmp.getCurCar()+tmp2.getCurCar());
                    }
                    tmp = tmp2;
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public void startLocation(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // fused location services
        request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(1000);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        polygons = new Polygon[9];
        //(int)noZones[0]
        // Add a marker in Sydney and move the camera
        // LATITUDE, LONGITUDE
        LatLng parkCoord = new LatLng(20.736218, -103.454357);
        LatLng carCood = new LatLng(20.737091, -103.453091);
        mMap.addMarker(new MarkerOptions()
                .position(parkCoord)
                .title("ITESM Parking LOT")
                        //.visible(false)
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .alpha(0.5f));

        parkingMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.car))
                .position(carCood, 10f, 13f);
                //.position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 10f, 13f);


        imageOverlay = googleMap.addGroundOverlay(parkingMap);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkCoord, 16));
        LatLng zone1 = new LatLng(20.736866, -103.453614);
        LatLng zone2 = new LatLng(20.735528, -103.453767);
        LatLng[] pointMap1 = new LatLng[]{new LatLng(20.737596, -103.453963),
                new LatLng(20.737456, -103.453070),
                new LatLng(20.736209, -103.453290),
                new LatLng(20.736269, -103.454081)};
        LatLng[] pointMap2 = new LatLng[]{new LatLng(20.736112, -103.454094),
                new LatLng(20.736054, -103.453300),
                new LatLng(20.734704, -103.453509),
                new LatLng(20.734758, -103.454222)};
        LatLng[] pointMap3 = new LatLng[]{new LatLng(20.734685, -103.454223),
                new LatLng(20.734625, -103.453523),
                new LatLng(20.733316, -103.453716),
                new LatLng(20.733371, -103.454352)};
        LatLng[] pointMap4 = new LatLng[]{new LatLng(20.733297, -103.454354),
                new LatLng(20.733285, -103.453890),
                new LatLng(20.733254, -103.453715),
                new LatLng(20.732261, -103.453945),
                new LatLng(20.732154, -103.4539620),
                new LatLng(20.732061, -103.453956),
                new LatLng(20.732007, -103.454001),
                new LatLng(20.732035, -103.454479)};
        LatLng[] pointMap5 = new LatLng[]{new LatLng(20.733437, -103.453575),
                new LatLng(20.733334, -103.452870),
                new LatLng(20.732590, -103.453027),
                new LatLng(20.732728, -103.453700)};
        LatLng[] pointMap6 = new LatLng[]{new LatLng(20.732868, -103.455014),
                new LatLng(20.732839, -103.454566),
                new LatLng(20.732331, -103.454653),
                new LatLng(20.731960, -103.455206),
                new LatLng(20.732043, -103.455847),
                new LatLng(20.732430, -103.456033),
                new LatLng(20.732440, -103.455564)};
        LatLng[] pointMap7 = new LatLng[]{new LatLng(20.732931, -103.456427),
                new LatLng(20.732284, -103.456017),
                new LatLng(20.731895, -103.456117),
                new LatLng(20.732275, -103.457099),
                new LatLng(20.732620, -103.457268),
                new LatLng(20.732959, -103.457226)};
        LatLng[] pointMap8 = new LatLng[]{new LatLng(20.733906, -103.457845),
                new LatLng(20.733822, -103.457212),
                new LatLng(20.732944, -103.457285),
                new LatLng(20.732944, -103.457285),
                new LatLng(20.732638, -103.457939),
                new LatLng(20.733334, -103.457881),
                new LatLng(20.733486, -103.457805),
                new LatLng(20.733726, -103.457780),
                new LatLng(20.733879, -103.457813)};
        LatLng[] pointMap9 = new LatLng[]{new LatLng(20.736649, -103.453076),
                new LatLng(20.736519, -103.452251),
                new LatLng(20.735809, -103.452380),
                new LatLng(20.735939, -103.453155)};

        LatLng[][] pointSet = new LatLng[][]{pointMap1, pointMap2, pointMap3, pointMap4, pointMap5, pointMap6, pointMap7, pointMap8, pointMap9};

        //
        for (int i = 0; i < polygons.length; i++){
            polygons[i] = mMap.addPolygon(new PolygonOptions()
                    .add(pointSet[i])
                    //for unrelated polygons
                    .fillColor(evalColor(parkSystem[0][i][0],parkSystem[0][i][1]))
                    .strokeWidth(0));

            polygons[i].setClickable(true);
        }


        googleMap.setOnPolygonClickListener(this);


    }



    @Override
    public void onConnected(Bundle bundle) {

        // check for permissions (android 6+)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            // if we don't have the fine location permission ask for it
            Log.d("ON CONNECTED", "NO PERMISSION GRANTED YET");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        mMap.setMyLocationEnabled(true);
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


        if(location == null){

            // request a location update
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);

        } else {
            Log.d("location", location.toString());
            myLocation = location;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        /*
        if (location == null)
            return;

        if (mPositionMarker == null) {

            mPositionMarker = mMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.car))
                    .anchor(0.5f, 0.5f)
                    .position(
                            new LatLng(location.getLatitude(), location
                                    .getLongitude())));
        }

        //animateMarker(mPositionMarker, location); // Helper method for smooth
        // animation

        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
                .getLatitude(), location.getLongitude())));
                */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        googleApiClient.connect();

    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(getApplicationContext(),"Zona"+polygon.getId().substring(2),Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Main2Activity.class);
        long maxCar = parkSystem[0][Integer.parseInt(polygon.getId().substring(2))][0],
                curCar = parkSystem[0][Integer.parseInt(polygon.getId().substring(2))][1];
        i.putExtra("values",
                new String[]{"0",
                        polygon.getId().substring(2),
                        maxCar+"",
                        curCar+"",
                        (maxCar - curCar)+""});
        System.out.println("Estacionamiento: 0");
        System.out.println("Zona: "+polygon.getId().substring(2));
        System.out.println("Max: "+maxCar);
        System.out.println("ActualCars: "+curCar);
        System.out.println("Delta: " + (maxCar - curCar) + "");
        startActivity(i);
    }

    public int evalColor(long maxCar, long curCar){
        int deltaCar = (int) (maxCar-curCar);
        System.out.println("maxCar "+maxCar);
        System.out.println("curCar "+curCar);
        if(deltaCar == maxCar) return 0x3F0000FF;
        else if(deltaCar < maxCar && deltaCar >= maxCar/2) return 0x3F00FF00;
        else if(deltaCar < maxCar/2 && deltaCar >= maxCar/3) return 0x3FFFFF00;
        else if(deltaCar < maxCar/3 && deltaCar > 0) return 0x3FFFA500;
        else if(deltaCar == 0) return 0x3FFF0000;

        return 0x3FFFFFFF;
    }

    public void exit(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
