package com.example.katalog_smartphone;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.katalog_smartphone.model.Smartphone;

public class SharedPreferenceUtility {

    private static final String PREF_FILE = "simple.example.katalog_smartphone.DATA";
    // PREF_FILE -> Nama file utk penyimpanan,
    // biasanya menyertakan app.id agar tidak bentrok dgn app lain
    private static final String TRANS_KEY = "TRANS"; // KEY utk daftar transaksi
    private static final String USER_NAME_KEY = "USERNAME"; // KEY untuk nama user

    private static SharedPreferences getSharedPref(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref;
    }
    /*
        Ambil data username dari Shared Preference
     */
    public static String getUserName(Context ctx) {
        return getSharedPref(ctx).getString(USER_NAME_KEY,"NO NAME");
    }
    /*
        Simpan data username ke Shared Preference
     */
    public static void saveUserName(Context ctx, String userName) {
        Log.d("SH PREF","Change user name to"+userName);
        getSharedPref(ctx).edit().putString(USER_NAME_KEY,userName).apply();
    }
    /*
        Ambil data transaksi dari Shared Preference
        Perhatikan bahwa data disimpan dalam format JSON String
     */
    public static List<Smartphone> getAllSmartphone(Context ctx) {
        String jsonString = getSharedPref(ctx).getString(TRANS_KEY, null);
        List<Smartphone> smr = new ArrayList<Smartphone>();
        if (jsonString != null) {
            Log.d("SH PREF","json string is:"+jsonString);
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    smr.add(Smartphone.fromJSONObject(obj));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(smr,(smartphone, s1) -> {
            return smartphone.getTanggal().compareTo(s1.getTanggal());
        }); // urutkan transaksi berdasarkan tanggal
        return smr;
    }
    /*
        Simpan data transaksi ke Shared Preference
        Perhatikan bahwa data disimpan dalam format JSON String
     */
    private static void saveAllSmartphone(Context ctx, List<Smartphone> smr) {
        List<JSONObject> jsonSmr = new ArrayList<JSONObject>();
        JSONArray jsonArr = new JSONArray();
        for (Smartphone sm : smr) {
            jsonArr.put(sm.toJSONObject());
        }
        getSharedPref(ctx).edit().putString(TRANS_KEY,jsonArr.toString()).apply();
    }

    /*
        Tambah data transaksi baru ke Shared Preference
     */
    public static void addSmartphone(Context ctx, Smartphone sm) {
        List<Smartphone> smr = getAllSmartphone(ctx);
        smr.add(sm);
        saveAllSmartphone(ctx,smr);
    }

    public static void editSmartphone(Context ctx, Smartphone sm) {
        List<Smartphone> smr = getAllSmartphone(ctx);
        for (Smartphone smat:smr) {
            if (sm.getModel().equals(smat.getModel())) {
                smr.remove(smat);
                smr.add(sm);
            }
        }
        saveAllSmartphone(ctx,smr);
    }

    public static void deleteSmartphone(Context ctx, String model) {
        List<Smartphone> smr = getAllSmartphone(ctx);
        for (Smartphone sm:smr) {
            if (sm.getModel().equals(model)) {
                smr.remove(sm);
            }
        }
        saveAllSmartphone(ctx,smr);
    }

}