package com.lindauswatun.final2.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityAdminHomePageBinding;

public class AdminHomePage extends AppCompatActivity {
    SharedPreferences preferences;

    ActivityAdminHomePageBinding binding; // ViewBinding

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

        // View Binding
        binding = ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        binding.addstock.setOnClickListener(view -> startActivity(new Intent(AdminHomePage.this, AddStock.class)));
        binding.addstaff.setOnClickListener(view -> startActivity(new Intent(AdminHomePage.this, AddStaff.class)));

    }
}