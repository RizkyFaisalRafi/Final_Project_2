package com.lindauswatun.final2.Admin;

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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityAddStockBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddStock extends AppCompatActivity {

    ActivityAddStockBinding binding; // ViewBinding

    private ArrayList<String> arrayKategori;
    ArrayAdapter<String> adapter;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // View Binding
        binding = ActivityAddStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(AddStock.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan....");
        progressDialog.setCancelable(false);
        db = FirebaseFirestore.getInstance();

        arrayKategori = new ArrayList<>();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayKategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.kategoriBrg.setAdapter(adapter);
        getData();

        // Back
        binding.imgBack1.setOnClickListener(view -> startActivity(new Intent(this, AdminHomePage.class)));

        binding.imgbarang.setOnClickListener(view -> selectImage());

        binding.addStock.setOnClickListener(view -> {
            if (binding.namaBrg.getText().length() > 0 && binding.totalStok.getText().length() > 0 && binding.hargaBarang.getText().length() > 0) {
                uploadImage(binding.namaBrg.getText().toString(), binding.totalStok.getText().toString(), binding.hargaBarang.getText().toString());
            } else {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getData() {
        db.collection("kategori").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.size() > 0) {
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    arrayKategori.add(doc.getString("name"));
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AddStock.this, "Data Tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(AddStock.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    //dialog mengambil gambar
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStock.this);
        builder.setTitle("Choose Image");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Choose from Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 20);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //menampilkan gambar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null) {
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    binding.imgbarang.post(() -> binding.imgbarang.setImageBitmap(bitmap));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        if (requestCode == 10 && resultCode == RESULT_OK) {
            assert data != null;
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap = (Bitmap) extras.get("data");
                binding.imgbarang.post(() -> binding.imgbarang.setImageBitmap(bitmap));
            });
            thread.start();
        }
    }

    private void uploadImage(String nama, String stok, String harga) {
        progressDialog.show();
        binding.imgbarang.setDrawingCacheEnabled(true);
        binding.imgbarang.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) binding.imgbarang.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //upload gambar ke database
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("IMG" + new Date().getTime() + ".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(e -> {
            Toast.makeText(AddStock.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }).addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(task -> {
                        if (task.getResult() != null) {
                            saveData(nama, stok, harga, task.getResult().toString());
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(AddStock.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData(String nama, String stok, String harga, String gambar) {
        if (binding.kategoriBrg.getSelectedItem().toString().equals("Elektronik - Komputer")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("elektronik - Komputer").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Elektronik - Handphone")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("elektronik - Handphone").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Wanita - Shoes")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Shoes").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Wanita - Dress")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Dress").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Wanita - Skirt")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Skirt").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana wanita - Formal")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Wanita - Formal").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Pria - Formal")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Formal").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Pria - T Shirt")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - T Shirt").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Pria - Shoes")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Shoes").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Busana Pria - Bottom Wear")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Busana Pria - Bottom Wear").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Buku")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Buku").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else if (binding.kategoriBrg.getSelectedItem().toString().equals("Lainnya")) {
            Map<String, Object> barang = new HashMap<>();
            barang.put("nama barang", nama);
            barang.put("stok", stok);
            barang.put("harga", harga);
            barang.put("gambar", gambar);
            progressDialog.show();
            db.collection("Lainnya").add(barang).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddStock.this, "berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(AddStock.this, "gagal disimpan " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else {
            Toast.makeText(this, "Gagal Menyimpan, Pastikan anda telah memilih Kategori barang.", Toast.LENGTH_SHORT).show();
        }
    }

}