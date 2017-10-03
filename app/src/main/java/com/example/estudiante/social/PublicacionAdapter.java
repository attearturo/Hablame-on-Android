package com.example.estudiante.social;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by estudiante on 04/05/17.
 */

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.ViewHolder> {

    private Context context;
    private LinkedList<Publicacion> publicaciones;

    public PublicacionAdapter(Context context, LinkedList<Publicacion> publicaciones) {
        this.context = context;
        this.publicaciones = publicaciones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.usuario.setText(publicaciones.get(position).getNombre());
        //holder.usuario_menu.setText(publicaciones.get(position).getNombre());
        holder.contenido.setText(publicaciones.get(position).getTexto());
        holder.fecha.setText(publicaciones.get(position).getFecha());

    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usuario, contenido, fecha, usuario_menu;

        public ViewHolder(View item) {
            super(item);

            usuario = (TextView) item.findViewById(R.id.texto_publicacion_usuario);
            contenido = (TextView) item.findViewById(R.id.texto_publicacion_contenido);
            fecha = (TextView) item.findViewById(R.id.fecha);
            //usuario_menu = (TextView) item.findViewById(R.id.usuario_menu);

        }

    }

}
