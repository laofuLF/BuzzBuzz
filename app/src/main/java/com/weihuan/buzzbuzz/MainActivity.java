package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.fragment.RecipeListFragment;
import com.weihuan.buzzbuzz.fragment.ViewPagerAdapter;
import com.weihuan.buzzbuzz.service.MusicService;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 5453;

    private RecipeListFragment currentFragment;
    private ViewPagerAdapter adapter;
    private Boolean musicPlaying;
    private IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;
    private DatabaseHelper db;

    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        musicPlaying = false;
        initBottomBar();
        broadcastIntent();
        initDatabase();
    }

    private void initDatabase() {
        db = new DatabaseHelper(this);
        if (!db.checkForTableExists("recipes")) {
            db.resetTable();
        }
    }

    private void broadcastIntent() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_LOW.equals(intent.ACTION_BATTERY_LOW)) {
                    if (musicPlaying) {
                        stopService(new Intent(context, MusicService.class));
                        musicPlaying = false;
                        sendNotification();
                    }

                }
            }
        };
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
    }

    protected void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.notification))
                .setVibrate(new long[] {0, 1000})
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("AppTestNotificationId");
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    private void initBottomBar() {

        bottomNavigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem(R.string.bottomTitle1, R.drawable.ic_whatshot_black_24dp, R.color.tab1);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem(R.string.bottomTitle2, R.drawable.ic_search_black_24dp, R.color.tab2);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem(R.string.bottomTitle3, R.drawable.ic_local_bar_black_24dp, R.color.tab3);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }

                if (wasSelected) {
                    currentFragment.refresh();
                    if (position == 2) {
                        currentFragment.refreshDatabase();
                    }
                    return true;
                }

                if (currentFragment != null){
                    currentFragment.willBeHidden();
                }

                viewPager.setCurrentItem(position, false);
                if (currentFragment == null) {
                    return true;
                }

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                return true;
            }
        });
        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        currentFragment = adapter.getCurrentFragment();
    }

    public void startService(View view) {
        if (!musicPlaying) {
            startService(new Intent(this, MusicService.class));
            musicPlaying = true;
        }
    }

    public void stopService(View view) {
        if (musicPlaying) {
            stopService(new Intent(this, MusicService.class));
            musicPlaying = false;
        }
    }
}
