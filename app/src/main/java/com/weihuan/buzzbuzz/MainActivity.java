package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.weihuan.buzzbuzz.fragment.RecipeListFragment;
import com.weihuan.buzzbuzz.fragment.ViewPagerAdapter;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.Recipe;
import com.weihuan.buzzbuzz.network.HttpRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int[] tabColors;
    private RecipeListFragment currentFragment;
    private ViewPagerAdapter adapter;
    private Handler handler = new Handler();



    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    private TextView textView;

    // Database
    private DatabaseHelper db;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private HttpRequest httpRequest;

//    public String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initBottomBar();


        db = new DatabaseHelper(this);
//        db.resetTable();
//        recipeList.addAll(db.getAllRecipes());

        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";
//        run(url);

//        httpRequest = new HttpRequest(url);
//        httpRequest.run(url);
//        httpRequest.getRecipes();
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



}
