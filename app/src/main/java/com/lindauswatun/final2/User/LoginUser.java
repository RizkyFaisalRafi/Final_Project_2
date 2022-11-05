package com.lindauswatun.final2.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityLoginUserBinding;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginUserBinding binding; // View Binding

//    SharedPreferences sharedPreferences;
//    boolean isRemembered2 = false;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        binding = ActivityLoginUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance(); // Firestore get data name

//        // Shared Preferences
//        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE); // Inisialisasi SharedPreferences
//        isRemembered2 = sharedPreferences.getBoolean("autoLogin", false); // Default value is False

//        if (isRemembered2) {
//            Intent intent = new Intent(LoginUser.this, HomePageUser.class);
//            startActivity(intent);
//            finish();
//        }

        mAuth = FirebaseAuth.getInstance(); // Inisialisasi firebaseAuth
        progressDialog = new ProgressDialog(LoginUser.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        // SetOnClickListener
        binding.showPassUser.setOnClickListener(this);
        binding.tvCreateAccount.setOnClickListener(this);
        binding.btnLoginUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Create Account
        if (v.getId() == R.id.tvCreateAccount) {
            startActivity(new Intent(LoginUser.this, RegisterUser.class));
        }

        // Button Sign In
        else if (v.getId() == R.id.btnLogin_user) {
            String getEmail = binding.etUsernameUser.getText().toString(); // Mengambil value yang ada di email
            String getPass = binding.etPasswordUser.getText().toString(); // Mengambil value yang ada di password

            if (getEmail.isEmpty()) {
                binding.etUsernameUser.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                binding.etPasswordUser.setError("Masukkan Password");
                return;
            }

            // save di Share Preferences
            storeLogInUser(getEmail, getPass);

        }

        // Show Pass
        else if (v.getId() == R.id.show_Pass_user) {
            if (binding.showPassUser.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                binding.etPasswordUser.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                binding.etPasswordUser.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    // yang disetor email dan password
    private void storeLogInUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) { // Berhasil

                    // Get UID
                    FirebaseUser user = mAuth.getCurrentUser();
                    String uid = user.getUid();

                    // Query ke Firestore DB berdasarkan Collections Users And Uid
                    processData(uid);

//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("autoLogin", true);
//                    editor.apply();

                    // ketika button log in di klik lalu menuju ke HomePage gunakan startActivity
//                    startActivity(new Intent(LoginUser.this, HomePageUser.class));
                    Toast.makeText(LoginUser.this, "Berhasil Login", Toast.LENGTH_SHORT).show(); // Toast
                }
                else { // Gagal
                    progressDialog.dismiss();
                    Toast.makeText(LoginUser.this, "Email Dan Password Salah!", Toast.LENGTH_SHORT).show();
                    showLoading(true);
                }
            }
        });
    }

    private void processData(String uid) {
        // query ke firestore
        db.collection("users").document(uid).get().addOnCompleteListener(task -> {
            Toast.makeText(LoginUser.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomePageUser.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
            showLoading(false);
            finish();
        });
    }

    // Back Button
    public void onBackPressed() {
        Intent intent = new Intent(LoginUser.this, MainActivity.class);
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