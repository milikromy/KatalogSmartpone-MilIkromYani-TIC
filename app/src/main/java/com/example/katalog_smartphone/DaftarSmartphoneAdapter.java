package com.example.katalog_smartphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import com.example.katalog_smartphone.model.Smartphone;

public class DaftarSmartphoneAdapter extends ArrayAdapter<Smartphone> {
    Context context;

    public DaftarSmartphoneAdapter(@NonNull Context context, @NonNull List<Smartphone> objects) {
        super(context, R.layout.row_smartphone, objects);
        this.context = context;
    }

    class ViewHolder {
        TextView txTgl;
        TextView txHarga;
        TextView txModel;
        TextView txSpesifikasi;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Smartphone sm = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_smartphone,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txTgl = convertView.findViewById(R.id.row_tx_tgl_smartphone);
            viewHolder.txSpesifikasi = convertView.findViewById(R.id.row_tx_spek_smartphone);
            viewHolder.txHarga = convertView.findViewById(R.id.row_tx_harga_smartphone);
            viewHolder.txModel = convertView.findViewById(R.id.row_tx_model);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txTgl.setText(GenericUtility.DATE_FORMAT.format(sm.getTanggal()));
        viewHolder.txSpesifikasi.setText(sm.getSpesifikasi());
        viewHolder.txModel.setText(sm.getModel());
        if (sm.getJenis().equals(Smartphone.SAMSUNG)) {
            viewHolder.txHarga.setText("-"+GenericUtility.formatUang(sm.getHarga()));
        } else {
            viewHolder.txHarga.setText(GenericUtility.formatUang(sm.getHarga()));
        }
        return convertView;
    }
}
