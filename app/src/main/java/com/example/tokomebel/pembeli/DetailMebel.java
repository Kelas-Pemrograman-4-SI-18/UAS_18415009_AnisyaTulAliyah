package com.example.tokomebel.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tokomebel.R;
import com.example.tokomebel.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailMebel extends AppCompatActivity {

    EditText edtkodeBarang, edtnamabarang, edthargabarang, edtjumblahbarang, edttanggalmasukbarang;
    ImageView  imgGambarMebel ;

    String strkodeBarang, strnamabarang, strhargabarang, strjumblahbarang, strtanggalmasukbarang, strGamabr, _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mebel);


        edtkodeBarang = (EditText) findViewById(R.id.edtkodeBarang);
        edtnamabarang = (EditText) findViewById(R.id.edtnamabarang);
        edthargabarang = (EditText) findViewById(R.id.edthargabarang);
        edtjumblahbarang = (EditText) findViewById(R.id.edtjumblahbarang);
        edttanggalmasukbarang = (EditText) findViewById(R.id.edttanggalmasukbarang);

        imgGambarMebel  = (ImageView) findViewById(R.id.gambar);


        Intent i = getIntent();
        strkodeBarang = i.getStringExtra("kodeBarang");
        strnamabarang= i.getStringExtra("namabarang");
        strhargabarang = i.getStringExtra("hargabarang");
        strjumblahbarang = i.getStringExtra("jumblahbarang");
        strtanggalmasukbarang = i.getStringExtra("tanggalmasukbarang");
        strGamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");


        edtkodeBarang.setText(strkodeBarang);
        edtnamabarang.setText(strnamabarang);
        edthargabarang.setText(strhargabarang);
        edtjumblahbarang.setText(strjumblahbarang );
        edttanggalmasukbarang.setText( strtanggalmasukbarang);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGamabr)
                .into( imgGambarMebel );
    }
}