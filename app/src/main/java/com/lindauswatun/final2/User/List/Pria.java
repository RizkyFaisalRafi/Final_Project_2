package com.lindauswatun.final2.User.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.HomePageUser;
import com.lindauswatun.final2.User.LoginUser;
import com.lindauswatun.final2.User.ui.busana.BusanaFragment;
import com.lindauswatun.final2.User.ui.elektronik.ElektronikFragment;

public class Pria extends AppCompatActivity {
    ImageView back, kaos, formal, celana, shoes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pria);
        getSupportActionBar().hide();

        back = findViewById(R.id.imgBackPria);
        back.setOnClickListener(view -> {
            Intent moveIntent = new Intent(Pria.this, HomePageUser.class);
            startActivity(moveIntent);
            startActivity(new Intent(Pria.this, HomePageUser.class));
            Toast.makeText(Pria.this, "Kembali", Toast.LENGTH_SHORT).show();
        });

        kaos = findViewById(R.id.kaos);
        formal = findViewById(R.id.formalP);
        celana = findViewById(R.id.celana);
        shoes = findViewById(R.id.shoes);

        kaos.setOnClickListener(view -> {
            startActivity(new Intent(this, PriaKaos.class));
        });
        formal.setOnClickListener(view -> {
            startActivity(new Intent(this, PriaFormal.class));
        });
        celana.setOnClickListener(view -> {
            startActivity(new Intent(this, PriaCelana.class));
        });
        shoes.setOnClickListener(view -> {
            startActivity(new Intent(this, PriaShoes.class));
        });
    }
}
