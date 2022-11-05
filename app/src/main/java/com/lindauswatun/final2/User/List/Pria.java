package com.lindauswatun.final2.User.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.HomePageUser;

import java.util.Objects;

public class Pria extends AppCompatActivity {
    ImageView back, kaos, formal, celana, shoes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pria);
        Objects.requireNonNull(getSupportActionBar()).hide();

        back = findViewById(R.id.imgBack2);
        back.setOnClickListener(view -> startActivity(new Intent(this, HomePageUser.class)));
        kaos = findViewById(R.id.kaos);
        formal = findViewById(R.id.formalP);
        celana = findViewById(R.id.celana);
        shoes = findViewById(R.id.shoes);

        kaos.setOnClickListener(view -> startActivity(new Intent(this, PriaKaos.class)));
        formal.setOnClickListener(view -> startActivity(new Intent(this, PriaFormal.class)));
        celana.setOnClickListener(view -> startActivity(new Intent(this, PriaCelana.class)));
        shoes.setOnClickListener(view -> startActivity(new Intent(this, PriaShoes.class)));
    }
}
