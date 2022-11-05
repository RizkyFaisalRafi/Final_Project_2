package com.lindauswatun.final2.User.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.Adapter.DataAdapter;
import com.lindauswatun.final2.User.Model.ListModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PriaCelana extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<ListModel> list = new ArrayList<>();
    private DataAdapter dataAdapter;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_barang);
        Objects.requireNonNull(getSupportActionBar()).hide();

        RecyclerView recyclerView = findViewById(R.id.rv_elektronik);
        dataAdapter = new DataAdapter(getApplicationContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(dataAdapter);

        back = findViewById(R.id.imgBack2);
        back.setOnClickListener(view -> startActivity(new Intent(this, Pria.class)));

        db.collection("Busana Pria - Bottom Wear")
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            //adapter
                            ListModel listModel = new ListModel(documentSnapshot.getString("nama barang"), documentSnapshot.getString("stok"), documentSnapshot.getString("harga"), documentSnapshot.getString("gambar"));
                            list.add(listModel);
                        }
                        dataAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(PriaCelana.this, "data gagal diambil", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(PriaCelana.this, "gagal", Toast.LENGTH_SHORT).show());
    }
}
