package com.weihuan.buzzbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.weihuan.buzzbuzz.db.DatabaseHelper;
import com.weihuan.buzzbuzz.db.recipe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigationAdapter adapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private int[] tabColors;

    // UI
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    private TextView textView;

    // Database
    private DatabaseHelper db;
    private List<recipe> recipeList = new ArrayList<>();

    public String url = "https://api.github.com/users/codepath";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        db = new DatabaseHelper(this);
        recipeList.addAll(db.getAllRecipes());

        run();


    }

    void run(){
        textView = findViewById(R.id.textView);
        Log.i("running", "11111");


        OkHttpClient client = getOKHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("fail", "11111");
                e.printStackTrace();

                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("success", "11111");
                final String result = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            final String owner = jsonObject.getString("name");
                            Log.i("content from the web", owner);
                            textView.setText(owner);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    private static OkHttpClient getOKHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("wh", "log: message = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }



    private void initUI() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem(R.string.bottomTitle1, R.drawable.specs, R.color.tab1);
        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem(R.string.bottomTitle2, R.drawable.specs, R.color.tab2);
        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem(R.string.bottomTitle3, R.drawable.specs, R.color.tab3);


        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                return true;
            }
        });

    }



}
