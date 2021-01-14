package com.example.katalog_smartphone.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Smartphone {
    public static final String SAMSUNG="SAMSUNG";
    public static final String IPHONE="IPHONE";
    public static final String REALME="REALME";
    private String model;
    private Date tanggal;
    private String spesifikasi;
    private double harga;
    private String jenis;


    public Smartphone() {
        this.model = UUID.randomUUID().toString();
        this.tanggal = new Date();
    }
    public String getModel(){ return model;}

    public void setModel(String model){this.model = model;}

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public static Smartphone fromJSONObject(JSONObject obj) {
        Smartphone sm = new Smartphone();
        try {
            sm.setModel(obj.getString("model"));
            sm.setTanggal(new Date(obj.getLong("tanggal")));
            sm.setSpesifikasi(obj.getString("spesifikasi"));
            sm.setHarga(obj.getDouble("harga"));
            sm.setJenis(obj.getString("jenis"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sm;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("model",this.model);
            jsonObj.put("tanggal",this.tanggal.getTime());
            jsonObj.put("jenis",this.jenis);
            jsonObj.put("harga",this.harga);
            jsonObj.put("spesifikasi",this.spesifikasi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}