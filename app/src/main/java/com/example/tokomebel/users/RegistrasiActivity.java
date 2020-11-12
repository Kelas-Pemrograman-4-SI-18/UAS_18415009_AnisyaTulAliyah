package com.example.tokomebel.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tokomebel.R;
import com.example.tokomebel.server.BaseURL;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.tokomebel.server.BaseURL.registrasi;

public class RegistrasiActivity extends AppCompatActivity {

    Button btnBack;
    NoboButton btnRegis;
    EditText edtUsername, edtNamaLengkap, edtEmail, edtNoTelp, edtPassword;
    ProgressDialog pDialog;


    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtUsername = (EditText) findViewById(R.id.edtUserName);
        edtNamaLengkap = (EditText) findViewById(R.id.edtNmLengkap);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtNoTelp = (EditText) findViewById(R.id.edtNotlp);
        edtPassword = (EditText) findViewById(R.id.edtPassword);


        btnBack = (Button) findViewById(R.id.btnBack);
        btnRegis = (NoboButton) findViewById(R.id.btnregis);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrasiActivity.this, loginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = edtUsername.getText().toString();
                String strNamaLengkap = edtNamaLengkap.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strNoTelp = edtNoTelp.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strUserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNamaLengkap.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNoTelp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Telpon Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Passwo Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    Regis(strUserName, strNamaLengkap, strEmail, strNoTelp, strPassword);
                }
            }
        });
    }


    public void Regis(String userName, String namaLengkap, String email, String noTelp, String password) {

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("namaLengkap", namaLengkap);
        params.put("email", email);
        params.put("noTelp", noTelp);
        params.put("password", password);
        params.put("role", String.valueOf(2));

        pDialog.setMessage("MOhon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(registrasi, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegistrasiActivity.this, loginActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegistrasiActivity.this, loginActivity.class);
        startActivity(i);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }

    }
}