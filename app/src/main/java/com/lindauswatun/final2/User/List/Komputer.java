package com.lindauswatun.final2.User.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.Adapter.DataAdapter;
import com.lindauswatun.final2.User.Model.ListModel;
import com.lindauswatun.final2.User.ui.elektronik.ElektronikFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Komputer extends AppCompatActivity {
    ImageView back;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<ListModel> list = new ArrayList<>();
    private DataAdapter dataAdapter;

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
        back.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.imgBack2, new ElektronikFragment())
                    .commit();
        });

        db.collection("elektronik - Komputer")
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
                        Toast.makeText(Komputer.this, "data gagal diambil", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {

                });
    }
}