package com.example.yusuf.aybu;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;




public class MainActivity extends AppCompatActivity {

    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = findViewById(R.id.toolbar);
        myTabLayout = findViewById(R.id.tabs);
        myViewPager = findViewById(R.id.container);

        setSupportActionBar(myToolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());


        myViewPager.setAdapter(adapter);
        myTabLayout.setupWithViewPager(myViewPager);

        myTabLayout.getTabAt(0).setText("Food List");
        myTabLayout.getTabAt(1).setText("Announcements");
        myTabLayout.getTabAt(2).setText("News");
    }

}
