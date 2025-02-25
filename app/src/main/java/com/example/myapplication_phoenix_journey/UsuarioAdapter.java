package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private OnAgregarClickListener agregarClickListener;
    private OnEliminarClickListener eliminarClickListener;
    private Context context;
    private boolean mostrarEliminar;

    public UsuarioAdapter(List<Usuario> usuarios, OnAgregarClickListener agregarClickListener, OnEliminarClickListener eliminarClickListener, Context context, boolean mostrarEliminar) {
        this.usuarios = usuarios;
        this.agregarClickListener = agregarClickListener;
        this.eliminarClickListener = eliminarClickListener;
        this.context = context;
        this.mostrarEliminar = mostrarEliminar;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.nombreUsuario.setText(usuario.getNombre());

        // Cargar la imagen de perfil
        if (usuario.getImagenPerfilRuta() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(usuario.getImagenPerfilRuta());
            holder.imagenPerfil.setImageBitmap(bitmap);
        } else {
            holder.imagenPerfil.setImageResource(R.drawable.ic_profile_placeholder);
        }

        holder.agregarButton.setOnClickListener(v -> mostrarDialogoConfirmacion(usuario, "Agregar", () -> {
            agregarClickListener.onAgregarClick(usuario);
            holder.agregarButton.setVisibility(View.GONE); // Deshabilitar el botón de agregar
            if (mostrarEliminar) {
                holder.eliminarButton.setVisibility(View.VISIBLE); // Mostrar el botón de eliminar
            }
        }));

        if (mostrarEliminar) {
            holder.eliminarButton.setOnClickListener(v -> mostrarDialogoConfirmacion(usuario, "Eliminar", () -> {
                eliminarClickListener.onEliminarClick(usuario);
                holder.eliminarButton.setVisibility(View.GONE); // Deshabilitar el botón de eliminar
                holder.agregarButton.setVisibility(View.VISIBLE); // Mostrar el botón de agregar
                holder.imagenPerfil.setImageResource(R.drawable.ic_profile_placeholder); // Eliminar la imagen del usuario
            }));
        } else {
            holder.eliminarButton.setVisibility(View.GONE); // Ocultar el botón de eliminar
        }

        // Asegurarse de que el botón de agregar esté visible solo si el usuario no ha sido agregado
        if (usuario.isAmigo()) {
            holder.agregarButton.setVisibility(View.GONE);
            if (mostrarEliminar) {
                holder.eliminarButton.setVisibility(View.VISIBLE);
            }
        } else {
            holder.agregarButton.setVisibility(View.VISIBLE);
            holder.eliminarButton.setVisibility(View.GONE);
        }
    }

    private void mostrarDialogoConfirmacion(Usuario usuario, String accion, Runnable onConfirm) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar " + accion)
                .setMessage("¿Estás seguro de que deseas " + accion.toLowerCase() + " a " + usuario.getNombre() + "?")
                .setPositiveButton("Sí", (dialog, which) -> onConfirm.run())
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void actualizarUsuarios(List<Usuario> nuevosUsuarios) {
        usuarios.clear();
        usuarios.addAll(nuevosUsuarios);
        notifyDataSetChanged();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView nombreUsuario;
        ImageView imagenPerfil;
        ImageButton agregarButton;
        ImageButton eliminarButton;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreUsuario = itemView.findViewById(R.id.nombre_usuario);
            imagenPerfil = itemView.findViewById(R.id.imagen_perfil);
            agregarButton = itemView.findViewById(R.id.agregar_button);
            eliminarButton = itemView.findViewById(R.id.eliminar_button);
        }
    }

    public interface OnAgregarClickListener {
        void onAgregarClick(Usuario usuario);
    }

    public interface OnEliminarClickListener {
        void onEliminarClick(Usuario usuario);
    }
}
