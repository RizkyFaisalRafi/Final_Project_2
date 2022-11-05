package com.lindauswatun.final2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.lindauswatun.final2.Admin.LoginAdmin;
import com.lindauswatun.final2.Staff.LoginStaff;
import com.lindauswatun.final2.User.LoginUser;
import com.lindauswatun.final2.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding; // View Binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.btnUser.setOnClickListener(this);
        binding.btnAdmin.setOnClickListener(this);
        binding.btnStaff.setOnClickListener(this);
        binding.btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_user) {
            startActivity(new Intent(MainActivity.this, LoginUser.class));
        } else if (v.getId() == R.id.btn_admin) {
            startActivity(new Intent(MainActivity.this, LoginAdmin.class));
        } else if (v.getId() == R.id.btn_staff) {
            startActivity(new Intent(MainActivity.this, LoginStaff.class));
        } else if (v.getId() == R.id.btn_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
    }
}