package com.lindauswatun.final2.User.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.HomePageUser;

import java.util.Objects;

public class Wanita extends AppCompatActivity {
    ImageView back, rok, formal, dress, shoes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wanita);
        Objects.requireNonNull(getSupportActionBar()).hide();

        back = findViewById(R.id.imgBack2);
        back.setOnClickListener(view -> startActivity(new Intent(this, HomePageUser.class)));
        rok = findViewById(R.id.skirt);
        rok.setOnClickListener(view -> startActivity(new Intent(this, WanitaKaos.class)));
        formal = findViewById(R.id.formal);
        formal.setOnClickListener(view -> startActivity(new Intent(this, WanitaFormal.class)));
        dress = findViewById(R.id.dress);
        dress.setOnClickListener(view -> startActivity(new Intent(this, WanitaDress.class)));
        shoes = findViewById(R.id.shoesW);
        shoes.setOnClickListener(view -> startActivity(new Intent(this, WanitaShoes.class)));
    }
}
