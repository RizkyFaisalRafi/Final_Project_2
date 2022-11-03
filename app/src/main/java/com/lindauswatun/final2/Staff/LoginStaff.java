package com.lindauswatun.final2.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lindauswatun.final2.Admin.AddStaff;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.LoginUser;
import com.lindauswatun.final2.User.RegisterUser;

public class LoginStaff extends AppCompatActivity {
    ProgressBar loading;
    CheckBox showPassword;
    EditText username, pass;
    Button btLogin;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        loading = findViewById(R.id.progressBar);
        username = findViewById(R.id.et_Username_staff);
        pass = findViewById(R.id.et_Password_staff);
        showPassword = findViewById(R.id.show_Pass_staff);
        btLogin = findViewById(R.id.btnLogin_staff);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginStaff.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        btLogin.setOnClickListener(view -> {
            String getEmail = username.getText().toString();
            String getPass = pass.getText().toString();

            if (getEmail.isEmpty()) {
                username.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                pass.setError("Masukkan Password");
                return;
            }

            progressDialog.show();
            mAuth.signInWithEmailAndPassword(getEmail, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    showLoading(true);
                    if (task.isSuccessful()) { // Berhasil
                        showLoading(false);
                        Toast.makeText(LoginStaff.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginStaff.this, StaffHomePage.class));
                    } else { // Gagal
                        Toast.makeText(LoginStaff.this, "Email Dan Password Salah!", Toast.LENGTH_SHORT).show();
                        showLoading(true);
                    }
                }
            });
            progressDialog.dismiss();
        });

        // Show Password
        showPassword.setOnClickListener(view -> {
            if (showPassword.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    // Back Button
    public void onBackPressed() {
        Intent intent = new Intent(LoginStaff.this, MainActivity.class);
        startActivity(intent);
    }

    // Progress Bar
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }
}