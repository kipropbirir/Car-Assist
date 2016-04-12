package com.carassist.carassist.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.adapters.HomeViewPagerFragmentAdapter;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.github.badoualy.morphytoolbar.MorphyToolbar;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class HomeActivity extends AppCompatActivity {

    MorphyToolbar morphyToolbar;
    ViewPager viewPager;
    ResideMenu resideMenu;
    ResideMenuItem myProfile,driverProfile,garageProfile,sparesProfile,logOut;
    TabLayout tabLayout;

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout = (TabLayout)findViewById(R.id.home_tab);
        viewPager = (ViewPager)findViewById(R.id.home_view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        morphyToolbar = MorphyToolbar.builder(this,toolbar)
                .withToolbarAsSupportActionBar()
                .withTitle("")
                .withSubtitle("")
                .build();

        setSupportActionBar(toolbar);

        viewPager.setAdapter(new HomeViewPagerFragmentAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (morphyToolbar.isCollapsed()) {
                //  morphyToolbar.expand();
                //} else {
                //  morphyToolbar.collapse();
                //}
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("Car Assist");

        //ResideMenu
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.colorPrimary);
        resideMenu.attachToActivity(this);
        resideMenu.addIgnoredView(viewPager);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        //create menuItems
        final String titles[] = {"Driver","Garage","Spares","Logout","My Profile"};
        int icons[] = {R.drawable.ic_driver,R.drawable.ic_garage,R.drawable.ic_spares,R.drawable.ic_logout,R.mipmap.ic_pofilepic};

        //ResideMenuItem

        myProfile = new ResideMenuItem(this,icons[4],titles[4]);
        driverProfile = new ResideMenuItem(this,icons[0],titles[0]);
        garageProfile = new ResideMenuItem(this,icons[1],titles[1]);
        sparesProfile = new ResideMenuItem(this,icons[2],titles[2]);
        logOut = new ResideMenuItem(this,icons[3],titles[3]);

        resideMenu.addMenuItem(myProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(driverProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(garageProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(sparesProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(logOut, ResideMenu.DIRECTION_LEFT);

        driverProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("PROFILECATEGORY","driver");
                startActivity(intent);
            }
        });
        garageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("PROFILECATEGORY","garage");
                startActivity(intent);
            }
        });
        sparesProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("PROFILECATEGORY","spares");
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.unauth();
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
