package com.example.tokomebel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokomebel.R;
import com.example.tokomebel.model.ModelMebel;
import com.example.tokomebel.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMebel extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelMebel> item;

    public AdapterMebel(Activity activity, List<ModelMebel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_mebel, null);


        TextView kodeBarang             = (TextView) convertView.findViewById(R.id.txtkodeBarang);
        TextView namabarang         = (TextView) convertView.findViewById(R.id.txtnamabarang);
        TextView hargabarang        = (TextView) convertView.findViewById(R.id.txthargabarang);
        TextView jumblahBarang       = (TextView) convertView.findViewById(R.id.txtjumblahbarang);
        TextView tanggalmasukbarang  = (TextView) convertView.findViewById(R.id.txttanggalmasukbarang);
        ImageView gambarmebel         = (ImageView) convertView.findViewById(R.id.gambarMebel);



        kodeBarang.setText(item.get(position).getKodeBarang());
        namabarang.setText(item.get(position).getNamabarang());
        hargabarang.setText(item.get(position).getHargabarang());
        jumblahBarang.setText("Rp." + item.get(position).getJumlahbarang());
        tanggalmasukbarang.setText(item.get(position).getTanggalmasukbarang());

        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarmebel);
        return convertView;
    }
}
