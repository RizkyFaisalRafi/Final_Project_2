package com.lindauswatun.final2.Staff;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityLoginStaffBinding;

public class LoginStaff extends AppCompatActivity {

    ActivityLoginStaffBinding binding;

    ProgressBar loading;
    EditText username, pass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);

        // View Binding
        binding = ActivityLoginStaffBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loading = findViewById(R.id.progressBar);
        username = findViewById(R.id.et_Username_staff);
        pass = findViewById(R.id.et_Password_staff);

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginStaff.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        binding.btnLoginStaff.setOnClickListener(view1 -> {
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
            mAuth.signInWithEmailAndPassword(getEmail, getPass)
                    .addOnCompleteListener(task -> {
                        showLoading(true);
                        if (task.isSuccessful()) { // Berhasil
                            //Get UID
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String uid = user.getUid();

                            //Query ke Firestore DB berdasarkan Collections Users And Uid
                            processData(uid);

                        } else { // Gagal
                            Toast.makeText(LoginStaff.this, "Email Dan Password Salah!", Toast.LENGTH_SHORT).show();
                            showLoading(true);
                        }
                    });
            progressDialog.dismiss();
        });

        // Show Password
        binding.showPassStaff.setOnClickListener(view12 -> {
            if (binding.showPassStaff.isChecked()) {
                // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // Jika tidak, maka password akan di sembuyikan
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void processData(String uid) {
        //query ke firestore
        db.collection("staff").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    Toast.makeText(LoginStaff.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), StaffHomePage.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    showLoading(false);
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