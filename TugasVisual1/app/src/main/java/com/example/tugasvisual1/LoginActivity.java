package com.example.tugasvisual1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasvisual1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    // deklarasi objek
    TextInputLayout      validasiUsername, validasiPassword;
    TextInputEditText    txtUsername, txtPassword;
    Button               btnLogin;
    TextView             txtRegistrasi;

    // deklarasi variabel
    String username, password;

    // deklarasi variabel alamat host
    public static String URL = "http://192.168.43.182/api_android/seminfo/login.php";

    // deklarasi variabel session
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inisialisasi variabel session
        sessionManager = new SessionManager(this);

        // inisialisasi variabel objek
        validasiUsername   = findViewById(R.id.validasiUsername);
        validasiPassword = findViewById(R.id.validasiPassword);
        txtUsername        = findViewById(R.id.txtUsername);
        txtPassword      = findViewById(R.id.txtPassword);
        btnLogin         = findViewById(R.id.btnLogin);
        txtRegistrasi    = findViewById(R.id.txtRegister);

        // jika tombol login diklik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username  = txtUsername.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if(username.isEmpty()){
                    validasiUsername.setError("ID User harus diisi!");
                }else if(password.isEmpty()){
                    validasiPassword.setError("Password harus diisi!");
                }else{
                    auth_user(username, password);
                }
            }
        });

        // jika tombol register diklik
        txtRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sintak untuk pindah activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    // method login
    private void auth_user(final String id_user, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if(success.equals("1")){
                                for(int i=0 ; i<jsonArray.length() ; i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String nama_user = jsonObject1.getString("nama_user").trim();


                                    sessionManager.createSession(id_user, nama_user); // membuat session

                                    Toast.makeText(LoginActivity.this,
                                            "Login berhasil ! \n Nama : "+nama_user,
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this,
                                        "ID User dan Password tidak ditemukan! ",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,
                                    "Error login : " + e.toString(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,
                                "Error login : " + error.toString(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}