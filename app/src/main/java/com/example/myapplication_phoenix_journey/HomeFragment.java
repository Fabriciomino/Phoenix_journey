package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class HomeFragment extends Fragment {

    private MiBaseDeDatos db;

    public HomeFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar la base de datos
        db = new MiBaseDeDatos(getActivity());
        int usuarioId = db.obtenerUsuarioIdActivo();

        // Inicializar los botones

        LinearLayout botonLunes = view.findViewById(R.id.lunes);
        LinearLayout botonMartes = view.findViewById(R.id.martes);
        LinearLayout botonMiercoles = view.findViewById(R.id.miercoles);
        LinearLayout botonJueves = view.findViewById(R.id.jueves);
        LinearLayout botonViernes = view.findViewById(R.id.viernes);
        LinearLayout botonSabado = view.findViewById(R.id.sabado);
        LinearLayout botonDomingo = view.findViewById(R.id.domingo);


        botonLunes.setOnClickListener(v -> startEditarRutina("Lunes", usuarioId));
        botonMartes.setOnClickListener(v -> startEditarRutina("Martes", usuarioId));
        botonMiercoles.setOnClickListener(v -> startEditarRutina("Miércoles", usuarioId));
        botonJueves.setOnClickListener(v -> startEditarRutina("Jueves", usuarioId));
        botonViernes.setOnClickListener(v -> startEditarRutina("Viernes", usuarioId));
        botonSabado.setOnClickListener(v -> startEditarRutina("Sábado", usuarioId));
        botonDomingo.setOnClickListener(v -> startEditarRutina("Domingo", usuarioId));

        return view;
    }

    private void startEditarRutina(String dia, int usuarioId) {
        Intent intent = new Intent(getActivity(), EditarRutinaActivity.class);
        intent.putExtra("DIA_SELECCIONADO", dia);
        intent.putExtra("USUARIO_ID", usuarioId);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
