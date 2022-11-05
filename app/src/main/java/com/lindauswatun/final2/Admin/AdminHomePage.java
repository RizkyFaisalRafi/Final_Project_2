package com.lindauswatun.final2.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;

import java.security.PrivateKey;

public class AdminHomePage extends AppCompatActivity {
    Button addstock, addstaff;
    SharedPreferences preferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, LoginAdmin.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

//        getSupportActionBar().hide();

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        addstock = findViewById(R.id.add_stock);
        addstaff = findViewById(R.id.add_staff);

        addstock.setOnClickListener(view -> {
            startActivity(new Intent(AdminHomePage.this, AddStock.class));
        });
        addstaff.setOnClickListener(view -> {
            startActivity(new Intent(AdminHomePage.this, AddStaff.class));
        });

    }
}