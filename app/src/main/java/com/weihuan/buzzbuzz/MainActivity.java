package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        // Create items
        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem(R.string.bottomTitle1, R.drawable.specs, R.color.tab1);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem(R.string.bottomTitle2, R.drawable.specs, R.color.tab2);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem(R.string.bottomTitle3, R.drawable.specs, R.color.tab3);

        // add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        // Set background
//        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#C0C0C0"));

        // Set most default active item
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                return true;
            }
        });


    }

}
