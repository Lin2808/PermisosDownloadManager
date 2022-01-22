package com.example.permisosdownloadmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder>
{
    LayoutInflater inflater;
    List<Fichero> ficheros;
    Context context;
    Permisos permisos;

    public listAdapter(Context context, List<Fichero> ficheros)
    {
        this.inflater = LayoutInflater.from(context);
        this.ficheros = ficheros;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.ly_ficheros, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.idFichero.setText(ficheros.get(position).getIdFichero());
        holder.ficheros.setText(ficheros.get(position).getFichero());
        holder.fecha.setText(ficheros.get(position).getFecha());
        holder.url.setText(ficheros.get(position).getUrl());
        Glide.with(context)
                .load(R.drawable.icono)
                .into(holder.imgFoto);
        holder.imgFoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    String nombre = holder.ficheros.getText().toString();
                    String link = holder.url.getText().toString();
                    permisos.BajarDoc(nombre, link);
                }
                catch(Exception ex)
                {
                    String error = ex.toString();
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ficheros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView idFichero, ficheros, fecha, url;
        ImageView imgFoto;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            idFichero = itemView.findViewById(R.id.idFichero);
            ficheros = itemView.findViewById(R.id.fichero);
            fecha = itemView.findViewById(R.id.fecha);
            url = itemView.findViewById(R.id.url);
            imgFoto = itemView.findViewById(R.id.imgFoto);
        }
    }
}
