package com.lindauswatun.final2.Staff;

public class ModelStaff {
    String Nama ,Email,  Password, Konfirmasi,id;

    public ModelStaff(){

    }

    public ModelStaff(String nama, String email, String password) {
        Nama = nama;
        Email = email;
        Password = password;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
