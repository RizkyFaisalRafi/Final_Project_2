package com.lindauswatun.final2.User;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.databinding.ActivityDetailProductBinding;

public class DetailProduct extends AppCompatActivity {

    String barang, stok, harga, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // View Binding
        ActivityDetailProductBinding binding = ActivityDetailProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get
        barang = getIntent().getStringExtra("NAMA_BARANG");
        stok = "Stok: " + getIntent().getStringExtra("STOK_BARANG");
        harga = "Rp. " + getIntent().getStringExtra("HARGA_BARANG");
        gambar = getIntent().getStringExtra("GAMBAR_BARANG");

        // Set Text
        binding.tvNamaBarang.setText(barang);
        binding.tvStok.setText(stok);
        binding.tvHargaBarang.setText(harga);

        Glide.with(getApplicationContext())
                .load(gambar)
                .into(binding.ivProduct);


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