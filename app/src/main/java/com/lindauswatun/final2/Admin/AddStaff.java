package com.lindauswatun.final2.Admin;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.databinding.ActivityAddStaffBinding;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddStaff extends AppCompatActivity {

    ActivityAddStaffBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        binding = ActivityAddStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        binding.imgBack.setOnClickListener(view -> startActivity(new Intent(AddStaff.this, AdminHomePage.class)));

        binding.btnAddstaf.setOnClickListener(view -> {
            String getNama = binding.etNama.getText().toString();
            String getEmail = binding.etEmail.getText().toString();
            String getPass = binding.etpass.getText().toString();
            String getPass2 = binding.etKonfpass.getText().toString();

            if (getNama.isEmpty()) {
                binding.etNama.setError("Masukkan Nama..");
                return;
            }
            if (getEmail.isEmpty()) {
                binding.etEmail.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                binding.etpass.setError("Masukkan Password");
                return;
            }
            if (!getPass.equals(getPass2)) {
                binding.etKonfpass.setError("Password salah");
                binding.etKonfpass.setText("");
                return;
            }

            mAuth.createUserWithEmailAndPassword(getEmail, getPass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddStaff.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DocumentReference documentReference = firestore.collection("staff").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", getNama);
                    user.put("email", getEmail);
                    user.put("password", getPass);
                    documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "SUKSES : " + userID));
                    binding.etNama.setText("");
                    binding.etEmail.setText("");
                    binding.etpass.setText("");
                    binding.etKonfpass.setText("");
                } else {
                    Toast.makeText(AddStaff.this, "gagal" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Show Password
        binding.showPass.setOnClickListener(view -> {
            if (binding.showPass.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                binding.etpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                binding.etpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        binding.konfShowPass.setOnClickListener(view -> {
            if (binding.konfShowPass.isChecked()) {
                binding.etKonfpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                binding.etKonfpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(AddStaff.this, AdminHomePage.class);
        startActivity(intent);
    }
}