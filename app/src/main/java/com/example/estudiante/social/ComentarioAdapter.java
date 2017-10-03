package com.example.estudiante.social;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by josedavid on 6/05/17.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private Context context;
    private LinkedList<Comentario> comentarios;

    public ComentarioAdapter(Context context, LinkedList<Comentario> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.usuario.setText(comentarios.get(position).getDe());
        holder.contenido.setText(comentarios.get(position).getComentario());

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usuario, contenido;

        public ViewHolder(View item) {
            super(item);

            usuario = (TextView) item.findViewById(R.id.comentario_usuario);
            contenido = (TextView) item.findViewById(R.id.comentario_contenido);


        }

    }

}
