package com.example.WAHGWAAN;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.WAHGWAAN.Components.LoginFragment;
import com.example.WAHGWAAN.Components.MessageFragment;
import com.example.WAHGWAAN.Components.ProfileFragment;
import com.example.WAHGWAAN.Components.TimelineFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "onCreate: ", Toast.LENGTH_SHORT).show();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment(), "Timeline_Fragment").commit();
        }else{
            Toast.makeText(this,"Application Reloaded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("Timeline_Fragment");
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else if(myFragment == null || myFragment.isVisible() == false){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment(), "Timeline_Fragment").commit();
        }else{
            super.onBackPressed();
        }
    }

    public void onMenuBtnSelected(View view){
        switch (view.getId()) {
            case R.id.settingsBtn:
                Toast.makeText(this, "Settings: ", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.messageBtn:
                Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
                break;
            case R.id.searchBtn:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.timelineBtn:
                Toast.makeText(this, "Timeline", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment()).commit();
                break;
            case R.id.usernameLabel:
                Toast.makeText(this, "Log-Out", Toast.LENGTH_SHORT).show();
                OutputStreamWriter outputStreamWriter = null;
                try {
                    outputStreamWriter = new OutputStreamWriter(this.openFileOutput("wg-cred-14.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write("false");
                    outputStreamWriter.close();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
    }
}