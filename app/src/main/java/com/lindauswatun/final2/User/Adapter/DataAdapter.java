package com.lindauswatun.final2.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lindauswatun.final2.R;
import com.lindauswatun.final2.User.DetailProduct;
import com.lindauswatun.final2.User.Model.ListModel;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ListViewHolder> {
    private Context context;
    private List<ListModel> list;

    public DataAdapter(Context context, List<ListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DataAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_barang,parent,false);
        return new DataAdapter.ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ListViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNama());
        holder.stok.setText(list.get(position).getStok());
        holder.harga.setText(list.get(position).getHarga());
        Glide.with(context)
                .load(list.get(position)
                .getGambar())
                .into(holder.fotoBarang);

        // Detail Product
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context.getApplicationContext(), DetailProduct.class);
                detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detail.putExtra("NAMA_BARANG", list.get(position).getNama());
                detail.putExtra("STOK_BARANG", list.get(position).getStok());
                detail.putExtra("HARGA_BARANG", list.get(position).getHarga());
                // Gambar belum
//                detail.putExtra("GAMBAR_BARANG", list.get(position).getGambar());
                context.startActivity(detail);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView nama,stok,harga;
        ImageView fotoBarang;
        Button btnDetail;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaBarang);
            stok = itemView.findViewById(R.id.stokBarang);
            harga = itemView.findViewById(R.id.hrgBarang);
            fotoBarang = itemView.findViewById(R.id.imgbarang);

            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
