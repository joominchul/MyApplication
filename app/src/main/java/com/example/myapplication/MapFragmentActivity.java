package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

public class MapFragmentActivity extends FragmentActivity
        implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_fragment_activity);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);


    }


    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // ...
        //ui 추가
        //UiSettings uiSettings = naverMap.getUiSettings();
        //uiSettings.setCompassEnabled(true);
        //매물 정보(예 : 부산 시청)
        double lati = 35.1798159;
        double logi = 129.0750222;
        String apt_name = "부산광역시청";
        int apt_price = 100;
        //정보창 만들기
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "매물 이름 : " + apt_name + "\n가격 : "+apt_price+"억";
            }
        });

        //롱클릭 시 좌표 토스트
        naverMap.setOnMapLongClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                        Toast.LENGTH_SHORT).show());

        // 마커 만들기
        Marker marker = new Marker(); //마커 생성
        marker.setPosition(new LatLng(lati, logi)); //좌표 설정
        marker.setCaptionText(apt_name); //마커 이름 설정
        marker.setCaptionAligns(Align.Center); //이름 위치 설정
        marker.setMap(naverMap);
        infoWindow.open(marker);
    }
}