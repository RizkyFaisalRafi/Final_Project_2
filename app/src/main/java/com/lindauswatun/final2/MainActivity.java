package com.lindauswatun.final2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lindauswatun.final2.Admin.LoginAdmin;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.Staff.LoginStaff;
import com.lindauswatun.final2.User.LoginUser;

public class MainActivity extends AppCompatActivity {

    Button user,admin,staff,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        user = findViewById(R.id.btn_user);
        admin = findViewById(R.id.btn_admin);
        staff = findViewById(R.id.btn_staff);
        about = findViewById(R.id.btn_about);

        user.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginUser.class));
        });
        admin.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginAdmin.class));
        });
        staff.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginStaff.class));
        });
        about.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
    }
}