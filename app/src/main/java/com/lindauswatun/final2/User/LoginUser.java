package com.lindauswatun.final2.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.google.firebase.auth.FirebaseUser;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.Staff.LoginStaff;
import com.lindauswatun.final2.Staff.StaffHomePage;

public class LoginUser extends AppCompatActivity {
    ProgressBar loading;
    CheckBox showPassUser;
    EditText username, pass;
    TextView register;
    Button btLogin;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        loading = findViewById(R.id.progressBar);
        username = findViewById(R.id.et_Username_user);
        pass = findViewById(R.id.et_Password_user);
        showPassUser = findViewById(R.id.show_Pass_user);
        register = findViewById(R.id.tvCreateAccount);
        btLogin = findViewById(R.id.btnLogin_user);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginUser.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        register.setOnClickListener(view -> {
            startActivity(new Intent(LoginUser.this, RegisterUser.class));
        });

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


            mAuth.signInWithEmailAndPassword(getEmail, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.show();
                    showLoading(true);
                    if (task.isSuccessful()) {
                        showLoading(false);
                        Toast.makeText(LoginUser.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginUser.this, HomePageUser.class));
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginUser.this, "Email Dan Password Salah!", Toast.LENGTH_SHORT).show();
                        showLoading(true);
                    }
                    progressDialog.dismiss();
                }
            });
        });

        // Show Password
        showPassUser.setOnClickListener(view -> {
            if (showPassUser.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(LoginUser.this, MainActivity.class);
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