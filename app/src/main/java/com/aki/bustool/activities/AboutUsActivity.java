package com.aki.bustool.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aki.bustool.Interfaces.OnBusStationClickListener;
import com.aki.bustool.Interfaces.PoiSearchedListener;
import com.aki.bustool.R;
import com.aki.bustool.bean.ErrorStatus;
import com.aki.bustool.bean.LocationMessage;
import com.aki.bustool.utils.Initialize;
import com.aki.bustool.utils.PoiAddressUtil;
import com.aki.bustool.view.BusLineView;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

import java.util.List;

public class AboutUsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
    }
}


