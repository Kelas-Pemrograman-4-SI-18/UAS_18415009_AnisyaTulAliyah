package com.example.tokomebel.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.CalendarView;

import com.example.tokomebel.R;
import com.example.tokomebel.session.PrefSetting;
import com.example.tokomebel.session.SessionManager;
import com.example.tokomebel.users.loginActivity;

public class HomeAdminActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataMebel, cardInputMebel, cardProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(HomeAdminActivity.this);

        prefSetting.islogin(session, prefs);



        cardExit = (CardView) findViewById(R.id.cardExit);
        cardDataMebel = (CardView) findViewById(R.id.cardDataMebel);
        cardInputMebel = (CardView) findViewById(R.id.cardInputMebel);
        cardProfile = (CardView) findViewById(R.id.cardProfile);


        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, loginActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardDataMebel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataMebel.class);
                startActivity(i);
                finish();
            }
        });

        cardInputMebel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, InputDataMebel.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

    }
}