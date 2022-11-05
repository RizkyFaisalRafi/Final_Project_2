package com.lindauswatun.final2.User;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityRegisterUserBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    ActivityRegisterUserBinding binding; // View Binding

    ProgressDialog progressDialog;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    //    static DatabaseReference databaseUsers;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).hide();

        // SetOnClickListener
        binding.imgBack2.setOnClickListener(this);
        binding.tvSignIn.setOnClickListener(this);
        binding.btnRegisterUser.setOnClickListener(this);
        binding.showPassRegisterUser.setOnClickListener(this);
        binding.showKonfpassRegisterUser.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(RegisterUser.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        // Sign In
        if (v.getId() == R.id.tv_sign_in) {
            finish();
        }

        // Button Back
        else if (v.getId() == R.id.imgBack2) {
            startActivity(new Intent(RegisterUser.this, LoginUser.class));
        }

        // Btn Register
        else if (v.getId() == R.id.btnRegister_user) {
            String getName = binding.regName.getText().toString();
            String getEmail = binding.regEmail.getText().toString();
            String getPass = binding.regPass.getText().toString();
            String getPass2 = binding.ConPasswordReg.getText().toString();

            if (getName.isEmpty()) {
                binding.regName.setError("Masukkan Nama..");
                return;
            }
            if (getEmail.isEmpty()) {
                binding.regEmail.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                binding.regPass.setError("Masukkan Password");
                return;
            }
            if (!getPass.equals(getPass2)) {
                binding.ConPasswordReg.setError("Password salah");
                binding.ConPasswordReg.setText("");
                return;
            }

            mAuth.createUserWithEmailAndPassword(getEmail, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    showLoading(true);
                    progressDialog.show();
                    if (task.isSuccessful()) { // Sukses
                        showLoading(true);
                        progressDialog.dismiss();
                        Toast.makeText(RegisterUser.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterUser.this, LoginUser.class));
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firestore.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", getName);
                        user.put("email", getEmail);
                        user.put("password", getPass);
                        documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "SUKSES : " + userID));
                    } else { // Gagal
                        progressDialog.dismiss();
                        Toast.makeText(RegisterUser.this, "Register Gagal, Harap isi Form Dengan Benar!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Show Password
        else if (v.getId() == R.id.show_pass_register_user) {
            if (binding.showPassRegisterUser.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                binding.regPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                binding.regPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

        // Show Konfirmasi Password
        else if (v.getId() == R.id.show_konfpass_register_user) {
            if (binding.showKonfpassRegisterUser.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                binding.ConPasswordReg.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                binding.ConPasswordReg.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

    }


    // Back Button
    public void onBackPressed() {
        Intent intent = new Intent(RegisterUser.this, MainActivity.class);
        startActivity(intent);
    }

    // Progress Bar
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

}