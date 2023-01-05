package com.codigoEnigma.consumohttpvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText etUsuario, etContra;
    Button btnLogin;
    ImageView imLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        controles();
        setup();
        imLogo.setVisibility(View.VISIBLE);
    }

    private void setup() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String user = etUsuario.getText().toString();
        String pass = etContra.getText().toString();
        if (!etUsuario.getText().toString().isEmpty() && !etContra.getText().toString().isEmpty()) {
            String url = Uri.parse(Constantes.URL_BASE + "login.php")
                    .buildUpon()
                    .appendQueryParameter("user", user)
                    .appendQueryParameter("password", pass)
                    .build().toString();

            JsonObjectRequest JOR = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    respuesta(jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MainActivity.this, "Error de red: "+ volleyError, Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue cola = Volley.newRequestQueue(this);
            cola.add(JOR);
        } else {
            Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void respuesta(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("auth").compareTo("si") == 0) {
                Constantes.AUTH = jsonObject.getString("token");

               startActivity(new Intent(this,Listas_HTTP.class));
               Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error de usuario/contraseña", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void controles() {
        etUsuario = findViewById(R.id.et_usuario);
        etContra = findViewById(R.id.et_contrasena);
        btnLogin = findViewById(R.id.btn_login);
        imLogo = findViewById(R.id.imageView);
    }
}