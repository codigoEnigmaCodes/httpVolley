package com.codigoEnigma.consumohttpvolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarDatos extends AppCompatActivity {
    EditText etAdMatricula, etAdModelo, etAdMarca, etAdColor, etAdAnio, etAdCombustible, etAdRenta;
    Button btnCancelar, btnActualizar;
    Bundle parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        parametros = this.getIntent().getExtras();
        controles();
        acciones();
        if (parametros.getString("identificador").equals("PUT")) {
            btnActualizar.setText("Actualizar");
            llenado();
        }
    }

    private void llenado() {
        etAdMatricula.setText(parametros.getString("matricula"));
        etAdMatricula.setEnabled(false);
        etAdMarca.setText(parametros.getString("marca"));
        etAdModelo.setText(parametros.getString("modelo"));
        etAdColor.setText(parametros.getString("color"));
        etAdAnio.setText(parametros.getString("anio"));
        etAdCombustible.setText(parametros.getString("combustible"));
        etAdRenta.setText(parametros.getString("renta"));
    }

    private void acciones() {

        if (parametros.getString("identificador").equals("PUT")) {
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodoPUT();
                }
            });
        } else {
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodoPOST();
                }
            });

        }


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            }
        });
    }

    private void metodoPOST() {
        String url = Uri.parse(Constantes.URL_BASE +"rentas.php")
                .buildUpon()
                .build().toString();
        StringRequest peticion = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        respuestaPOST(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActualizarDatos.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> encabezado = new HashMap<>();
                encabezado.put("Authorization", Constantes.AUTH);
                return encabezado;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> encabezado = new HashMap<>();
                encabezado.put("placa", etAdMatricula.getText().toString().toUpperCase());
                encabezado.put("marca", etAdMarca.getText().toString());
                encabezado.put("modelo", etAdModelo.getText().toString());
                encabezado.put("color", etAdColor.getText().toString());
                encabezado.put("anio", etAdAnio.getText().toString());
                encabezado.put("combustible", etAdCombustible.getText().toString());
                encabezado.put("renta", etAdRenta.getText().toString());
                return encabezado;
            }
        };
        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(peticion);

    }

    private void respuestaPOST(String response) {
        try {
            JSONObject res = new JSONObject(response);
            if (res.getString("result").compareTo("ok") == 0){
                Toast.makeText(this, "El auto se agrego", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            }else{
                Toast.makeText(this, "El auto no se agrego", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){}
    }

    private void metodoPUT() {
        if (!etAdMatricula.getText().toString().isEmpty() &&
                !etAdMarca.getText().toString().isEmpty() &&
                !etAdModelo.getText().toString().isEmpty() &&
                !etAdColor.getText().toString().isEmpty() &&
                !etAdAnio.getText().toString().isEmpty() &&
                !etAdCombustible.getText().toString().isEmpty() &&
                !etAdRenta.getText().toString().isEmpty()) {
            String url = Uri.parse(Constantes.URL_BASE + "rentas.php")
                    .buildUpon()
                    .appendQueryParameter("placa", etAdMatricula.getText().toString())
                    .appendQueryParameter("marca", etAdMarca.getText().toString())
                    .appendQueryParameter("modelo", etAdModelo.getText().toString())
                    .appendQueryParameter("color", etAdColor.getText().toString())
                    .appendQueryParameter("anio", etAdAnio.getText().toString())
                    .appendQueryParameter("combustible", etAdCombustible.getText().toString())
                    .appendQueryParameter("renta", etAdRenta.getText().toString())
                    .build().toString();
            JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.PUT, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            respuesta(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ActualizarDatos.this, "Error de red " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> encabezado = new HashMap<>();
                    encabezado.put("Authorization", Constantes.AUTH);
                    return encabezado;
                }
            };
            RequestQueue cola = Volley.newRequestQueue(this);
            cola.add(peticion);
        } else {
            Toast.makeText(this, "Algun campo esta vacio", Toast.LENGTH_SHORT).show();
        }

    }

    private void respuesta(JSONObject response) {
        try {
            if (response.getString("result").compareTo("ok") == 0) {
                Toast.makeText(this, "Actualizacion Exitosa", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Listas_HTTP.class));
                finish();
            } else {
                Toast.makeText(this, "Actualizacion Fallida, intenta de nuevo", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
        }
    }

    private void controles() {
        etAdMatricula = findViewById(R.id.et_ad_matricula);
        etAdMarca = findViewById(R.id.et_ad_marca);
        etAdModelo = findViewById(R.id.et_ad_modelo);
        etAdColor = findViewById(R.id.et_ad_color);
        etAdAnio = findViewById(R.id.et_ad_anio);
        etAdCombustible = findViewById(R.id.et_ad_combustible);
        etAdRenta = findViewById(R.id.et_ad_renta);
        btnCancelar = findViewById(R.id.btn_ad_cancelar);
        btnActualizar = findViewById(R.id.btn_ad_actualizar);
    }
}