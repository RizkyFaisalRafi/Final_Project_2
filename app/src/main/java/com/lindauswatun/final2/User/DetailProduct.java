package com.lindauswatun.final2.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.Adapter.DataAdapter;
import com.lindauswatun.final2.User.List.PriaKaos;
import com.lindauswatun.final2.User.Model.ListModel;

import java.util.ArrayList;
import java.util.List;

public class DetailProduct extends AppCompatActivity {
    public static final String ITEM_EXTRA = "item_extra";

    TextView vbarang, vstok, vharga;
    ImageView vgambar;
    String barang, stok, harga, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        vbarang = findViewById(R.id.tv_nama_barang);
        vstok = findViewById(R.id.tv_stok);
        vharga = findViewById(R.id.tv_harga_barang);
        vgambar = findViewById(R.id.iv_product);

        // Get
        barang = getIntent().getStringExtra("NAMA_BARANG");
        stok = "Stok: " + getIntent().getStringExtra("STOK_BARANG");
        harga = "Rp. " + getIntent().getStringExtra("HARGA_BARANG");
        gambar = getIntent().getStringExtra("GAMBAR_BARANG");

        // Set Text
        vbarang.setText(barang);
        vstok.setText(stok);
        vharga.setText(harga);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Produk");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}