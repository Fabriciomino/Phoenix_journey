package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FotosAdapterActivity extends RecyclerView.Adapter<FotosAdapterActivity.FotoViewHolder> {

    private ArrayList<Foto> fotosList;
    private OnFotoEliminarListener eliminarListener;

    public interface OnFotoEliminarListener {
        void onFotoEliminar(int position);
    }


    public FotosAdapterActivity(ArrayList<Foto> fotosList, OnFotoEliminarListener eliminarListener) {
        this.fotosList = fotosList;
        this.eliminarListener = eliminarListener;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foto, parent, false);
        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoViewHolder holder, int position) {
        Foto foto = fotosList.get(position);
        holder.imagenProgreso.setImageBitmap(foto.getImagen());
        holder.textFecha.setText(foto.getFecha());

        holder.botonEliminar.setOnClickListener(v -> eliminarListener.onFotoEliminar(position));
    }

    @Override
    public int getItemCount() {
        return fotosList.size();
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenProgreso;
        TextView textFecha;
        ImageButton botonEliminar;

        public FotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenProgreso = itemView.findViewById(R.id.imagen_foto);
            textFecha = itemView.findViewById(R.id.text_fecha_foto);
            botonEliminar = itemView.findViewById(R.id.boton_eliminar_foto);
        }
    }
}
