package com.example.applicationtest001.UI.Function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.applicationtest001.R;
import com.example.applicationtest001.UI.Fragment.FragmentPeople;
import com.example.applicationtest001.UI.Fragment.FragmentPerson;
import com.example.applicationtest001.UI.Fragment.FragmentRoom;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new FragmentRoom()).commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(1).getItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.people:
                        fragment = new FragmentPeople();
                        break;
                    case R.id.room:
                        fragment = new FragmentRoom();
                        break;
                    case R.id.person:
                        fragment = new FragmentPerson();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });
    }
}