package com.example.katalog_smartphone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

import com.example.katalog_smartphone.model.Smartphone;

public class FormSmartphoneActivity extends AppCompatActivity {

    Button btnSimpan;
    TextInputLayout tilSpesifikasi,tilHarga;
    EditText edtTgl;
    Spinner spJenisSmartphone;
    Date tanggalinSmartphone;
    final String[] tipeSmartphone = {Smartphone.SAMSUNG,Smartphone.IPHONE,Smartphone.REALME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_smartphone);
        inisialisasiView();
    }

    private void inisialisasiView() {
        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(view -> simpan());
        edtTgl = findViewById(R.id.edt_tgl);
        edtTgl.setOnClickListener(view -> pickDate());
        tilSpesifikasi = findViewById(R.id.til_spesifikasi);
        tilHarga = findViewById(R.id.til_harga);
        spJenisSmartphone = findViewById(R.id.sm_jenis);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                tipeSmartphone
        );
        spJenisSmartphone.setAdapter(adapter);
        spJenisSmartphone.setSelection(0);
    }

    private void simpan() {
        if (isDataValid()) {
            Smartphone sm = new Smartphone();
            sm.setSpesifikasi(tilSpesifikasi.getEditText().getText().toString());
            Double harga = Double.parseDouble(tilHarga.getEditText().getText().toString());
            sm.setHarga(harga);
            sm.setJenis(spJenisSmartphone.getSelectedItem().toString());
            sm.setTanggal(tanggalinSmartphone);
            SharedPreferenceUtility.addSmartphone(this,sm);
            Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();

            // Kembali ke layar sebelumnya setelah 500 ms (0.5 detik)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);



        }
    }

    private boolean isDataValid() {
        if (tilSpesifikasi.getEditText().getText().toString().isEmpty()
                || tilHarga.getEditText().getText().toString().isEmpty()
                || spJenisSmartphone.getSelectedItem().toString().isEmpty()
        ) {
            Toast.makeText(this,"Lengkapi semua isian",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /*
        Dipanggil saat user ingin mengisi tanggal transaksi
        Menampilkan date picker dalam popup dialog
     */
    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int thn = c.get(Calendar.YEAR);
        int bln = c.get(Calendar.MONTH);
        int tgl = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, yyyy, mm, dd) -> {
                    edtTgl.setText(dd + "-" + (mm + 1) + "-" + yyyy);
                    c.set(yyyy,mm,dd);
                    tanggalinSmartphone = c.getTime();
                },
                thn, bln, tgl);
        datePickerDialog.show();
    }

}
