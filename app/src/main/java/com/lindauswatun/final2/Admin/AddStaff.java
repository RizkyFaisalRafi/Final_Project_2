package com.lindauswatun.final2.Admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lindauswatun.final2.MainActivity;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.Staff.ModelStaff;
import com.lindauswatun.final2.User.RegisterUser;

import java.util.HashMap;
import java.util.Map;

public class AddStaff extends AppCompatActivity {
    private CheckBox ShowPass, KonfShowPass; // Show Password
    EditText nama,email,password,confpass;
    Button addstaff;
    ImageView back;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        getSupportActionBar().hide();

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.etpass);
        confpass = findViewById(R.id.et_konfpass);
        ShowPass = findViewById(R.id.showPass); // Show Password
        KonfShowPass = findViewById(R.id.konf_show_Pass); // Show Password
        back = findViewById(R.id.imgBack);
        addstaff = findViewById(R.id.btAddstaf);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();



        back.setOnClickListener(view -> {
            startActivity(new Intent(AddStaff.this, AdminHomePage.class));
        });

        addstaff.setOnClickListener(view -> {
            String getNama = nama.getText().toString();
            String getEmail = email.getText().toString();
            String getPass = password.getText().toString();
            String getPass2 = confpass.getText().toString();

            if (getNama.isEmpty()){
                nama.setError("Masukkan Nama..");
                return;
            }
            if(getEmail.isEmpty()) {
                email.setError("Masukkan Email");
                return;
            }
            if (getPass.isEmpty()) {
                password.setError("Masukkan Password");
                return;
            }
            if (!getPass.equals(getPass2)) {
                confpass.setError("Password salah");
                confpass.setText("");
                return;
            }

            mAuth.createUserWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AddStaff.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firestore.collection("staff").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("name" ,getNama);
                        user.put("email", getEmail);
                        user.put("password", getPass);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "SUKSES : " + userID);
                            }
                        });
                        nama.setText("");
                        email.setText("");
                        password.setText("");
                        confpass.setText("");
                    }else{
                        Toast.makeText(AddStaff.this, "gagal" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        // Show Password
        ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShowPass.isChecked()) {
                    // Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Jika tidak, maka password akan di sembuyikan
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        KonfShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KonfShowPass.isChecked()) {
                    confpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    confpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(AddStaff.this, AdminHomePage.class);
        startActivity(intent);
    }
}