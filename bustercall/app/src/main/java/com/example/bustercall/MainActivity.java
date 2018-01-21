package com.example.bustercall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.RECEIVE_SMS;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView logView;//메인텍스트뷰
    double latitude;//위도
    double longitude;//경도
    public GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //구글맵관련
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*권한설정*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){

        }else {
            Toast.makeText(this, "SMS 수신 권한 없음", Toast.LENGTH_LONG).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, RECEIVE_SMS)){
                Toast.makeText(this, "SMS권한 설명 필요함.", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this, new String[] {RECEIVE_SMS, ACCESS_FINE_LOCATION}, 1);
            }
        }

        //gps설정
        logView = (TextView) findViewById(R.id.gpstext);
        logView.setText("GPS정보 확인중...");
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d("Main", "isGPSENabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {  //gps가 리셋될 때 실행되는 메소드
                latitude = location.getLatitude();//위도
                longitude = location.getLongitude();//경도
                logView.setText(latitude + "," + longitude);
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {

            }

        };
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);//네트워크로 위치전송시 1초마다 좌표가 바뀜
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);//gps정보가 1초마다 좌표를 바꿔줌

    }
    /*권한설정*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this, "권한 거부됨", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    //sms실행버튼
    public void smsButton(View v) {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        String smsBody = ("http://maps.google.com/?q="+ latitude + "," + longitude); //메시지내용

        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.putExtra("address", ""); //지정된 번호
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    //카카오링크
    public void kakaoButton(View v) {
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            kakaoBuilder.addText("http://maps.google.com/?q="+ latitude + "," + longitude );//메시지내용

            kakaoLink.sendMessage(kakaoBuilder, this);
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }
    //112자동전송버튼
    public void policebutton(View v){
        SmsManager smsManager = android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage("01040322159", null, ("도와주세요 " +"http://maps.google.com/?q="+ latitude + "," + longitude ), null, null);
        finish();
    }
    //119자동전송버튼
    public void firestationbutton(View v){
        SmsManager smsManager = android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage("01040322159", null, ("도와주세요 " +"http://maps.google.com/?q="+ latitude + "," + longitude ), null, null);
        finish();
    }
    //메뉴버튼
    public void menubutton(View v){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    //구글맵버튼
    public void barbuttonclicked(View v) {
        switch (v.getId()) {

            case R.id.gps:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //LatLng seoul = new LatLng(latitude, longitude);
        // mMap.addMarker( new MarkerOptions().position(seoul).title( "현위치" ) );
        // mMap.moveCamera( CameraUpdateFactory.newLatLng(seoul) );
    }

}

