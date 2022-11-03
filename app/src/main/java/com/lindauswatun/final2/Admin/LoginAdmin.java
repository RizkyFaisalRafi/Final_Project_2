package com.lindauswatun.final2.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;

public class LoginAdmin extends AppCompatActivity {
    ProgressBar loading;
    CheckBox showPassword;
    EditText email, password;
    Button btnLoginAdmin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        progressDialog = new ProgressDialog(LoginAdmin.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        loading = findViewById(R.id.progressBar);
        email = findViewById(R.id.et_Username_admin);
        password = findViewById(R.id.et_Password_admin);
        showPassword = findViewById(R.id.show_Pass_admin);
        btnLoginAdmin = findViewById(R.id.btnLogin_admin);

        btnLoginAdmin.setOnClickListener(view -> {
            showLoading(true);
            if(!((TextUtils.isEmpty(email.getText().toString()))||(TextUtils.isEmpty(password.getText().toString())))){
                if (email.getText().toString().equals("admin")&&password.getText().toString().equals("admin123")){
                    showLoading(false);
                    progressDialog.show();
                    Intent intent=new Intent(LoginAdmin.this,AdminHomePage.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                }else{
                    showLoading(false);
                    Toast.makeText(this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }else{
                showLoading(false);
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

        // Show Password
        showPassword.setOnClickListener(view -> {
            if (showPassword.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

    }

    // Back Button
    public void onBackPressed(){
        Intent intent=new Intent(LoginAdmin.this, MainActivity.class);
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