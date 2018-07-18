package com.aki.bustool.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aki.bustool.Interfaces.GetLocalMessageListener;
import com.aki.bustool.R;
import com.aki.bustool.bean.LocationMessage;
import com.aki.bustool.service.BaseLocationService;
import com.aki.bustool.utils.Initialize;
import com.amap.api.services.core.LatLonPoint;

public class UsualPlaceActivity extends AppCompatActivity implements
        GetLocalMessageListener,
        View.OnClickListener{

    private LocationMessage locationMessage;

    private ImageView homeImage;
    private ImageView companyImage;
    private ImageView otherImage;
    private TextView homeText;
    private TextView companyText;
    private TextView otherText;

    private Button backButton;
    private Button homeButton;
    private Button companyButton;
    private Button otherButton;

    private RelativeLayout protectBridge;

    private Intent inputIntent;
    private int pointType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usualplace);
        inputIntent = new Intent(this,RouteInputActivity.class);
        /*if(null != getIntent().getParcelableExtra("LocationMessage")){
            locationMessage = getIntent().getParcelableExtra("LocationMessage");
        }else {

        }*/
        this.setGetLocalMessageListener(this);
        initView();
    }

    private void initView(){

        homeImage = $(R.id.home_image);
        companyImage = $(R.id.company_image);
        otherImage = $(R.id.other_image);
        homeText = $(R.id.home_text);
        companyText = $(R.id.company_text);
        otherText = $(R.id.other_text);
        backButton = $(R.id.back);
        homeButton = $(R.id.home_button);
        companyButton = $(R.id.company_button);
        otherButton = $(R.id.other_button);
        protectBridge = $(R.id.protect_bridge);

        backButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        companyButton.setOnClickListener(this);
        otherButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.home_button:
                pointType = 0;
                inputIntent.putExtra(Initialize.PLACE,Initialize.HOME);
                startActivityForResult(inputIntent, Initialize.REQUEST_CODE_HOME);
                break;
            case R.id.company_button:
                pointType = 1;
                inputIntent.putExtra(Initialize.PLACE, Initialize.COMPANY);
                startActivityForResult(inputIntent, Initialize.REQUEST_CODE_COMPANY);
                break;
            case R.id.other_button:
                pointType = 2;
                inputIntent.putExtra(Initialize.PLACE, Initialize.OTHERPLACE);
                startActivityForResult(inputIntent, Initialize.REQUEST_CODE_OTHERPLACE);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T $(int resId){
        return (T)super.findViewById(resId);
    }

    @Override
    public void getLocalMessage(LocationMessage mLocationMessage) {
        /*ui逻辑*/
        if(null == inputIntent.getParcelableExtra(Initialize.LOCATION)){
            inputIntent.putExtra(Initialize.LOCATION,locationMessage);
            protectBridge.setVisibility(View.GONE);
        }
    }


    private Intent getLocationMessage;
    @Override
    protected void onStart() {
        super.onStart();
        getLocationMessage = new Intent(this, BaseLocationService.class);
        IntentFilter receiverFilter = new IntentFilter();
        receiverFilter.addAction("com.BaseLocationReceiver");
        registerReceiver(localMessageReceiver,receiverFilter);
        startService(getLocationMessage);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(localMessageReceiver);
        if(null != getLocationMessage){
            stopService(getLocationMessage);
        }
    }

    private LatLonPoint fromPoint;
    private LatLonPoint toPoint;
    Intent pathShowIntent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fromPoint = data.getParcelableExtra(Initialize.PLACE_POINT);
        if(requestCode == Initialize.REQUEST_CODE_HOME&& resultCode == Initialize.RESULT_CODE_HOME){
            homeText.setText(data.getStringExtra(Initialize.PLACE_NAME));
            toPoint = data.getParcelableExtra(Initialize.PLACE_POINT);
        }
        else if(requestCode == Initialize.REQUEST_CODE_COMPANY && resultCode == Initialize.RESULT_CODE_COMPANY){
            companyText.setText(data.getStringExtra(Initialize.PLACE_NAME));
            toPoint = data.getParcelableExtra(Initialize.PLACE_POINT);
        }
        else if(requestCode == Initialize.REQUEST_CODE_OTHERPLACE && resultCode == Initialize.RESULT_CODE_OTHERPLACE){
            otherText.setText(data.getStringExtra(Initialize.PLACE_NAME));
            toPoint = data.getParcelableExtra(Initialize.PLACE_POINT);
        }
        handleMessageTo();
    }

    public void handleMessageTo(){
        pathShowIntent = new Intent(this,ShowPathActivity.class);
        if(null == fromPoint){
            fromPoint = new LatLonPoint(locationMessage.getLatitude(),locationMessage.getLongitude());
        }else if(null == toPoint){
            toPoint = new LatLonPoint(locationMessage.getLatitude(),locationMessage.getLongitude());
        }

        pathShowIntent.putExtra(Initialize.LOCATION,locationMessage);
        pathShowIntent.putExtra("FromName","我的位置");
        if(pointType == 0)
            pathShowIntent.putExtra("ToName",homeText.getText().toString());
        if(pointType == 1)
            pathShowIntent.putExtra("ToName",companyText.getText().toString());
        if(pointType == 2)
            pathShowIntent.putExtra("ToName",otherText.getText().toString());
        pathShowIntent.putExtra("From",fromPoint);
        pathShowIntent.putExtra("To",toPoint);
        startActivity(pathShowIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver localMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            locationMessage = intent.getParcelableExtra(Initialize.LOCATION);
            if(null != locationMessage){
                mGetLocalMessageListener.getLocalMessage(locationMessage);
            }else{
                /**
                 *
                 * 错误处理逻辑
                 *
                 * **/
            }
        }
    };

    private GetLocalMessageListener mGetLocalMessageListener;
    public void setGetLocalMessageListener(GetLocalMessageListener listener){
        this.mGetLocalMessageListener = listener;
    }
}
