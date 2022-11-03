package com.lindauswatun.final2.User;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.Admin.AddStaff;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    ProgressBar loading;
    CheckBox showPassword, showKonfPass;
    EditText regName, regEmail, regPass, regPassCon;
    Button btnRegister;
    ImageView back;
    TextView login;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    static DatabaseReference databaseUsers;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();

        loading = findViewById(R.id.progressBar);
        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPass = findViewById(R.id.reg_pass);
        regPassCon = findViewById(R.id.ConPassword_reg);
        showPassword = findViewById(R.id.show_pass_register_user);
        showKonfPass = findViewById(R.id.show_konfpass_register_user);
        btnRegister = findViewById(R.id.btnRegister_user);
        login = findViewById(R.id.tvRegister);
        back = findViewById(R.id.imgBack2);
        back.setOnClickListener(view -> {
            startActivity(new Intent(RegisterUser.this, LoginUser.class));
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(RegisterUser.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        login.setOnClickListener(view -> {
            finish();
        });

        btnRegister.setOnClickListener(view -> {
            String getName = regName.getText().toString();
            String getEmail = regEmail.getText().toString();
            String getPass = regPass.getText().toString();
            String getPass2 = regPassCon.getText().toString();

            if (getName.isEmpty()) {
                regName.setError("Masukkan Nama..");
                return;
            }
            if (getEmail.isEmpty()) {
                regEmail.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                regPass.setError("Masukkan Password");
                return;
            }
            if (!getPass.equals(getPass2)) {
                regPassCon.setError("Password salah");
                regPassCon.setText("");
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
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "SUKSES : " + userID);
                            }
                        });
                    } else { // Gagal
                        progressDialog.dismiss();
                        Toast.makeText(RegisterUser.this, "Register Gagal, Harap isi Form Dengan Benar!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        // Show Password
        showPassword.setOnClickListener(view -> {
            if (showPassword.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                regPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                regPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        // Show Password
        showKonfPass.setOnClickListener(view -> {
            if (showKonfPass.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                regPassCon.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                regPassCon.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    // Back Button
    public void onBackPressed() {
        Intent intent = new Intent(RegisterUser.this, MainActivity.class);
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