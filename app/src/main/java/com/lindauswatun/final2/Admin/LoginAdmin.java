package com.lindauswatun.final2.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.databinding.ActivityLoginAdminBinding;

public class LoginAdmin extends AppCompatActivity {

    ActivityLoginAdminBinding binding; // ViewBinding

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    boolean isRemembered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        binding = ActivityLoginAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(LoginAdmin.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false); // Default value CheckBock is False

        if (isRemembered) {
            Intent intent = new Intent(LoginAdmin.this, AdminHomePage.class);
            startActivity(intent);
            finish();
        }

        binding.btnLoginAdmin.setOnClickListener(view -> {
            showLoading(true);
            if (!((TextUtils.isEmpty(binding.etEmailAdmin.getText().toString())) || (TextUtils.isEmpty(binding.etPasswordAdmin.getText().toString())) || (TextUtils.isEmpty(binding.checkbox.getText().toString())))) {
                if (binding.etEmailAdmin.getText().toString().equals("admin") && binding.etPasswordAdmin.getText().toString().equals("admin123") && binding.checkbox.isChecked()) {

                    boolean checked = binding.checkbox.isChecked();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("CHECKBOX", checked);
                    editor.apply();
                    Toast.makeText(LoginAdmin.this, "Berhasil Login Admin!", Toast.LENGTH_SHORT).show();

                    showLoading(false);
                    progressDialog.show();
                    Intent intent = new Intent(LoginAdmin.this, AdminHomePage.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();

                } else {
                    binding.checkbox.setError("Silahkan Konfirmasi Password");

                    showLoading(false);
                    Toast.makeText(this, "Email atau Password Salah dan Silahkan Ceklis Konfirmasi Admin", Toast.LENGTH_SHORT).show();
                }
            } else {
                showLoading(false);
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

        // Show Password
        binding.showPassAdmin.setOnClickListener(view -> {
            if (binding.showPassAdmin.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                binding.etPasswordAdmin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                binding.etPasswordAdmin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

//    // Back Button
//    public void onBackPressed() {
//        Intent intent = new Intent(LoginAdmin.this, MainActivity.class);
//        startActivity(intent);
//    }

    // Progress Bar
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}