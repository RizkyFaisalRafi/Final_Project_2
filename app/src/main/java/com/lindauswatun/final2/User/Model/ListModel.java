package com.lindauswatun.final2.User.Model;

public class ListModel {
    private String nama, stok, harga, gambar;

    public ListModel(String nama, String stok, String harga, String gambar) {
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public ListModel() {

    }
}
