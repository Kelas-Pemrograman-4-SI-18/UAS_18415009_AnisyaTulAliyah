package com.example.tokomebel.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.tokomebel.admin.HomeAdminActivity;
import com.example.tokomebel.pembeli.HomePembeli;
import com.example.tokomebel.session.PrefSetting;
import com.example.tokomebel.session.SessionManager;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.tokomebel.server.BaseURL.login;
import static com.example.tokomebel.server.BaseURL.registrasi;

public class loginActivity extends AppCompatActivity {

    Button btnRegis;
    NoboButton btnlogin;
    EditText edtuserName, edtpassword;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnRegis = (Button) findViewById(R.id.btnRegis);
        btnlogin =(NoboButton) findViewById(R.id.loginBtn);

        edtuserName = (EditText) findViewById(R.id.edtuserName);
        edtpassword = (EditText) findViewById(R.id.edtpassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(this);
        prefSetting.checklogin(session, prefs);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginActivity.this, RegistrasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String strUserName = edtuserName.getText().toString();
                String strPassword = edtpassword.getText().toString();

                if (strUserName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "User Name Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if (strPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Name Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else {
                    login(strUserName, strPassword);
                }
            }
        });
    }

    public void login(String userName, String password) {

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("password", password);

        pDialog.setMessage("MOhon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String userName = jsonObject.getString("userName");
                                String namaLengkap = jsonObject.getString("namaLengkap");
                                String email = jsonObject.getString("email");
                                String noTelp = jsonObject.getString("noTelp");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(loginActivity.this, _id, userName, namaLengkap, email, noTelp, role, prefs);

                                if (role.equals("1")) {
                                     Intent i = new Intent(loginActivity.this, HomeAdminActivity.class);
                                     startActivity(i);
                                     finish();
                                }else {
                                    Intent i = new Intent(loginActivity.this, HomePembeli.class);
                                    startActivity(i);
                                    finish();
                                }
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