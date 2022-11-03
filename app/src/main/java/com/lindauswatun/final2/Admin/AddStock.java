package com.lindauswatun.final2.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.RegisterUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddStock extends AppCompatActivity {
    Spinner kategori;
    EditText namaBrg, stokBrg, hargaBrg;
    ImageView gambarbrg, back;
    Button addstok;
    private ArrayList<String> arrayKategori;
    ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(AddStock.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan....");
        progressDialog.setCancelable(false);
        back = findViewById(R.id.imgBack1);
        back.setOnClickListener(view -> {
            startActivity(new Intent(this, AdminHomePage.class));
        });

        kategori = findViewById(R.id.kategoriBrg);
        namaBrg = findViewById(R.id.namaBrg);
        stokBrg = findViewById(R.id.totalStok);
        hargaBrg = findViewById(R.id.hargaBarang);
        gambarbrg = findViewById(R.id.imgbarang);
        addstok = findViewById(R.id.addStock);
        db = FirebaseFirestore.getInstance();

        arrayKategori = new ArrayList<>();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayKategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adapter);
        getData();

        gambarbrg.setOnClickListener(view -> {
            selectImage();
        });

        addstok.setOnClickListener(view -> {
            if (namaBrg.getText().length() > 0 && stokBrg.getText().length() > 0 && hargaBrg.getText().length() > 0) {
                uploadImage(namaBrg.getText().toString(), stokBrg.getText().toString(), hargaBrg.getText().toString());
            }else {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getData() {
        db.collection("kategori").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size()>0){
                    for (DocumentSnapshot doc: queryDocumentSnapshots){
                        arrayKategori.add(doc.getString("name"));
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(AddStock.this, "Data Tidak tersedia", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddStock.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //dialog mengambil gambar
    private void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStock.this);
        builder.setTitle("Choose Image");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items,(dialog,item) ->{
            if (items[item].equals("Take Photo")){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }else if (items[item].equals("Choose from Gallery")){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,20);
            }else if(items[item].equals("Cancel")){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //menampilkan gambar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20 && resultCode == RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    gambarbrg.post(()->{
                        gambarbrg.setImageBitmap(bitmap);
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        if (requestCode == 10 && requestCode == RESULT_OK ){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap =(Bitmap) extras.get("data");
                gambarbrg.post(() ->{
                    gambarbrg.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }
    
    private void uploadImage(String nama, String stok, String harga){
        progressDialog.show();
        gambarbrg.setDrawingCacheEnabled(true);
        gambarbrg.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) gambarbrg.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //upload gambar ke database
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("IMG"+new Date().getTime()+".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddStock.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata()!=null) {
                    if (taskSnapshot.getMetadata().getReference()!=null) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.getResult() != null) {
                                    saveData(nama, stok, harga, task.getResult().toString());
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String nama, String stok, String harga, String gambar){
        if (kategori.getSelectedItem().toString().equals("Elektronik - Komputer")){
            Map<String,Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("elektronik - Komputer")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if (kategori.getSelectedItem().toString().equals("Elektronik - Handphone")){
            Map<String,Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("elektronik - Handphone")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Wanita - Shoes")){
            Map<String,Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Shoes")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Wanita - Dress")){
            Map<String,Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Dress")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Wanita - Skirt")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Skirt")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana wanita - Formal")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Formal")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Pria - Formal")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Formal")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Pria - T Shirt")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - T Shirt")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Pria - Shoes")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Shoes")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Busana Pria - Bottom Wear")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Bottom Wear")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Buku")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Buku")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else if(kategori.getSelectedItem().toString().equals("Lainnya")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Lainnya")
                    .add(barang)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }else {
            Toast.makeText(this, "Gagal Menyimpan, Pastikan anda telah memilih Kategori barang.", Toast.LENGTH_SHORT).show();
        }
    }

}