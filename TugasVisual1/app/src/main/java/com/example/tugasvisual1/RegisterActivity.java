package com.example.tugasvisual1;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.tugasvisual1.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtUsername, txtNama, txtPassword, txtKonfirmasiPassword;
    private TextInputLayout validasiUsername, validasiNama, validasiPassword, validasiKonfirmasiPassword;
    private Button btnRegister;
    private ProgressBar loading;

    // ip address laptop/pc
    private static String URL = "http://192.168.43.182/api_android/register.php";

    private String username, nama, password, konfirmasi_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUsername = findViewById(R.id.txtUsername);
        txtNama = findViewById(R.id.txtNama);
        txtPassword = findViewById(R.id.txtPassword);
        txtKonfirmasiPassword = findViewById(R.id.txtKonfirmasiPassword);

        validasiUsername = findViewById(R.id.validasiUsername);
        validasiNama = findViewById(R.id.validasiNama);
        validasiPassword = findViewById(R.id.validasiPassword);
        validasiKonfirmasiPassword = findViewById(R.id.validasiKonfirmasiPassword);

        btnRegister = findViewById(R.id.btnRegister);
        loading = findViewById(R.id.loading);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = txtUsername.getText().toString().trim();
                nama = txtNama.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                konfirmasi_password = txtKonfirmasiPassword.getText().toString().trim();
                if(username.isEmpty()) {
                    validasiUsername.setError("Username harus diisi!");
                }else if(nama.isEmpty()){
                    validasiNama.setError("Nama harus diisi!");
                }else if(password.isEmpty()){
                    validasiPassword.setError("Password harus diisi!");
                }else if(!konfirmasi_password.equals(password)){
                    validasiKonfirmasiPassword.setError("Konfirmasi password harus sama!");
                }else{
                    Registrasi();
                }

            }
        });
    }

    private void Registrasi() {
        loading.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.GONE);
        username = this.txtUsername.getText().toString().trim();
        nama = this.txtNama.getText().toString().trim();
        password = this.txtPassword.getText().toString().trim();
        konfirmasi_password = this.txtKonfirmasiPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error : " +e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Register Error : " +error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}