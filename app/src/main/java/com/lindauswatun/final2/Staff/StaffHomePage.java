package com.lindauswatun.final2.Staff;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityStaffHomePageBinding;

import java.util.Objects;

public class StaffHomePage extends AppCompatActivity {

    ActivityStaffHomePageBinding binding;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home_page);

        // View Binding
        binding = ActivityStaffHomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        uid = getIntent().getStringExtra("uid");

        binding.logOut.setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getSharedPreferences("autoLogin", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();

            startActivity(new Intent(StaffHomePage.this, LoginStaff.class));
            finish();
        });


        firestore.collection("staff")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            Log.d(TAG, document.getId() + " => " + document.getData() + " ==> " + document.getData().get("name"));

                            if (uid.equals(document.getId())) {
                                binding.nameStaff.setText(Objects.requireNonNull(document.getData().get("name")).toString());
                            }
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }
}