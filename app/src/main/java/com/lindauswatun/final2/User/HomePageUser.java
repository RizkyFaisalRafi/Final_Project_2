package com.lindauswatun.final2.User;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lindauswatun.final2.AboutActivity;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.Staff.LoginStaff;
import com.lindauswatun.final2.User.ui.main.SectionsPagerAdapter;
import com.lindauswatun.final2.databinding.ActivityHomePageUserBinding;
//import com.lindauswatun.final2.User.databinding.ActivityHomePageUserBinding;


public class HomePageUser extends AppCompatActivity {

    private ActivityHomePageUserBinding binding;

//    @Override
//    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.logout) {
//            startActivity(new Intent(this, LoginUser.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());


        binding.viewPager.setAdapter(sectionsPagerAdapter);

        binding.tabs.setupWithViewPager(binding.viewPager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageUser.this, AboutActivity.class));
            }
        });

        binding.keluarAkunUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageUser.this, LoginUser.class));
                Toast.makeText(HomePageUser.this, "Keluar Akun", Toast.LENGTH_SHORT).show();
            }
        });

    }
}