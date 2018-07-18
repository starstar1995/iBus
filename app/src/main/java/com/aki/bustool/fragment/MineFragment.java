package com.aki.bustool.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aki.bustool.activities.AboutUsActivity;
import com.aki.bustool.activities.LoginInActivity;
import com.aki.bustool.activities.MainActivity;
import com.aki.bustool.R;
import com.aki.bustool.activities.RegistPageActivity;
import com.aki.bustool.activities.RegisterActivity;
import com.aki.bustool.activities.RouteActivity;
import com.aki.bustool.activities.UsualPlaceActivity;
import com.aki.bustool.utils.Initialize;

public class MineFragment extends Fragment implements  View.OnClickListener  {

    private MainActivity mainActivity;
    private Button btn_Register;
    private Button btn_login;
    private Button btn_UsualPlace;
    private Button btn_AboutUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);

        mainActivity = (MainActivity) getActivity();
        mainActivity.switchIco(3);

        initview(view);
        return  view;
    }

    public void initview(View view)
    {
        btn_Register = $(view, R.id.Register);
        btn_login = $(view, R.id.btn_login);
        btn_UsualPlace = $(view,R.id.btn_usualplace);
        btn_AboutUs = $(view,R.id.btn_aboutus);

        btn_Register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_UsualPlace.setOnClickListener(this);
        btn_AboutUs.setOnClickListener(this);
    }
    private <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Register:
                regist();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_usualplace:
                goUsualPlace();
                break;
            case R.id.btn_aboutus:
                AboutUs();
                break;

        }
    }
    private void regist() {
        Intent intent = new Intent(getActivity(),RegisterActivity.class);
        startActivity(intent);
    }
    private void login() {
        Intent intent = new Intent(getActivity(),LoginInActivity.class);
        startActivity(intent);
    }

    public void goUsualPlace ()
    {
        Intent intent = new Intent(getActivity(),UsualPlaceActivity.class);
        startActivity(intent);
    }

    public void AboutUs ()
    {
        //Toast.makeText(this.getActivity(),"要显示的内容",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),AboutUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainActivity.switchIco(33);
    }
}
