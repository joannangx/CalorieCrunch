package com.example.wingsoffreedom.caloriecrunch;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager) findViewById(R.id.activity_landing);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

    }
}
