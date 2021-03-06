package com.example.tokomebel.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokomebel.R;
import com.example.tokomebel.adapter.AdapterMebel;
import com.example.tokomebel.model.ModelMebel;
import com.example.tokomebel.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataMebel extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterMebel adapter;
    ListView list;

    ArrayList<ModelMebel> newsList = new ArrayList<ModelMebel>();
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mebel);

        getSupportActionBar().setTitle("Data Mebel");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterMebel(ActivityDataMebel.this, newsList);
        list.setAdapter(adapter);
        getAllMebel();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataMebel.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllMebel() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataMebel, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data mebel = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelMebel mebel = new ModelMebel();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodeBarang = jsonObject.getString("kodeBarang");
                                    final String namaBarang = jsonObject.getString("namaBarang");
                                    final String hargaBarang = jsonObject.getString("hargaBarang");
                                    final String jumblahBarang = jsonObject.getString("jumblahBarang");
                                    final String tanggalmasukBarang = jsonObject.getString("tanggalmasukBarang");
                                    final String gambar = jsonObject.getString("gambar");
                                    mebel.setKodeBarang(kodeBarang);
                                    mebel.setKodeBarang(kodeBarang);
                                    mebel.setNamabarang(namaBarang);
                                    mebel.setHargabarang(hargaBarang);
                                    mebel.setJumlahbarang(jumblahBarang);
                                    mebel.setTanggalmasukbarang(tanggalmasukBarang);
                                    mebel.setGambar(gambar);
                                    mebel.set_id(_id);

                                  list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                          Intent a = new Intent(ActivityDataMebel.this, EditMebelDanHapusActivity.class);
                                          a.putExtra("kodeBarang", newsList.get(position).getKodeBarang());                                           a.putExtra("_id", newsList.get(position).get_id());
                                           a.putExtra("namaBarang", newsList.get(position).getNamabarang());
                                          a.putExtra("hargaBarang", newsList.get(position).getHargabarang());
                                           a.putExtra("jumblahBarang", newsList.get(position).getJumlahbarang());
                                           a.putExtra("tanggalmasukBarang", newsList.get(position).getTanggalmasukbarang());
                                           a.putExtra("gambar", newsList.get(position).getGambar());
                                           startActivity(a);
                                        }                                   });
                                    newsList.add(mebel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}