package com.example.permisosdownloadmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String URLstring = "https://my-json-server.typicode.com/Lin2808/Json/db";
    RecyclerView recyclerView;
    ArrayList<Fichero> ficheroArrayList;
    Permisos permiso = new Permisos();
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mainActivity = this;

        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permisos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permiso.otorgarPermisos(permisos);

        recyclerView = findViewById(R.id.rvFicheros);
        ExtraerFicheros();
    }

    //Este método se dispara después de haber otorgado o denegado los permisos
    //ya sea conceder o denegar servicios
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String mensaje = "";
        if(requestCode==1)
        {
            for(int i =0; i<permissions.length;i++)
            {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                {
                    mensaje = mensaje + "permiso otorgado " + permissions[i] + "\n";
                }
                else
                {
                    mensaje = mensaje + "permiso denegado " + permissions[i] + "\n";
                }
            }
            Toast.makeText(this.getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }


    private void ExtraerFicheros()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            ficheroArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("ficheros");

                            for (int i = 0; i < dataArray.length(); i++)
                            {
                                Fichero fichero = new Fichero();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                fichero.setIdFichero(dataobj.getString("id"));
                                fichero.setFichero(dataobj.getString("descripcion"));
                                fichero.setFecha(dataobj.getString("fecha"));
                                fichero.setUrl(dataobj.getString("url"));
                                ficheroArrayList.add(fichero);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            listAdapter adapter = new listAdapter(getApplicationContext(), ficheroArrayList);
                            recyclerView.setAdapter(adapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}