package com.codigoEnigma.consumohttpvolley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listas_HTTP extends AppCompatActivity {
    Button btnAgrega, btnElimina;
    RecyclerView rvLista;
    List<ModeloAuto> modeloList = new ArrayList<ModeloAuto>();
    //para el dialog
    EditText etDelMatricula;

    public static int listPosicion = 0;
    private adaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_http);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        controles();
        setup();

    }

    private void setup() {
        metodoGET();

        btnAgrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoPOST();
            }
        });

        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodoDELETE();
            }
        });


    }

    private void rvEventos() {
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val ="PUT";
                listPosicion = rvLista.getChildAdapterPosition(view);
                Intent intent = new Intent(getApplicationContext(), ActualizarDatos.class);
                intent.putExtra("matricula", modeloList.get(listPosicion).getMatricula());
                intent.putExtra("marca", modeloList.get(listPosicion).getMarca());
                intent.putExtra("modelo", modeloList.get(listPosicion).getModelo());
                intent.putExtra("color", modeloList.get(listPosicion).getColor());
                intent.putExtra("anio", modeloList.get(listPosicion).getAnio());
                intent.putExtra("combustible", modeloList.get(listPosicion).getCombustible());
                intent.putExtra("renta", modeloList.get(listPosicion).getRenta());
                intent.putExtra("identificador",val.toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void metodoGET() {
        String url = Uri.parse(Constantes.URL_BASE + "rentas.php")
                .buildUpon()
                .build().toString();
        JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        llenarRespuestaGET(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de red", Toast.LENGTH_SHORT).show();
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
    }

    private void llenarRespuestaGET(JSONArray response) {
        try {
            ModeloAuto ma = new ModeloAuto();
            for (int i = 0; i < response.length(); i++) {
                //modeloList.add(response.getJSONObject(i).getString("matricula"));
                ma.setMatricula(response.getJSONObject(i).getString("matricula"));
                ma.setMarca(response.getJSONObject(i).getString("marca"));
                ma.setModelo(response.getJSONObject(i).getString("modelo"));
                ma.setColor(response.getJSONObject(i).getString("color"));
                ma.setAnio(response.getJSONObject(i).getString("anio"));
                ma.setCombustible(response.getJSONObject(i).getString("tipo_combustible"));
                ma.setRenta(response.getJSONObject(i).getString("en_renta"));
                modeloList.add(new ModeloAuto(ma.matricula, ma.marca, ma.modelo, ma.color, ma.anio, ma.combustible, ma.renta));
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvLista.setLayoutManager(layoutManager);
            adapter = new adaptador(modeloList);
            rvLista.setAdapter(adapter);
            rvEventos();

        } catch (Exception e) {
        }
    }

    private void metodoPOST() {
        String val ="POST";
        Intent intent = new Intent(getApplicationContext(), ActualizarDatos.class);
        intent.putExtra("identificador",val.toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            finish();
        }
    }

    private void metodoDELETE() {
        String key = "";
        
        if(!etDelMatricula.getText().toString().isEmpty()){
            for (int i = 0; i < modeloList.size(); i++) {
                if (modeloList.get(i).getMatricula().equals(etDelMatricula.getText().toString().toUpperCase())){
                    key = "pass";
                    break;
                }else{
                    key ="";
                }
            }
            if (key.equals("pass")){
                String url = Uri.parse(Constantes.URL_BASE + "rentas.php")
                        .buildUpon()
                        .appendQueryParameter("placa", etDelMatricula.getText().toString().toUpperCase())
                        .build().toString();
                JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.DELETE, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                confirmacionDELETE(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Listas_HTTP.this, "Error de red", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> encabezado = new HashMap<>();
                        encabezado.put("Authorization", Constantes.AUTH);
                        return encabezado;
                    }
                };
                RequestQueue cola = Volley.newRequestQueue(this);
                cola.add(peticion);
            }else{
                Toast.makeText(this, "La matricula no existe", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmacionDELETE(JSONObject response) {
        try{
            if (response.getString("result").compareTo("ok") == 0){
                Toast.makeText(this, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                //para actualizar la lista hacemos un nuevo get
                metodoGET();
            }else{
                Toast.makeText(this, "No se ha eliminado, intenta otra ves", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){}
    }


    private void controles() {
        btnAgrega = findViewById(R.id.btn_agregar);
        btnElimina = findViewById(R.id.btn_eliminar);
        rvLista = findViewById(R.id.rv_lista);
        etDelMatricula = findViewById(R.id.et_delete);
    }
}