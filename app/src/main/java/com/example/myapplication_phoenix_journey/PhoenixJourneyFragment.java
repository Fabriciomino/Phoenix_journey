package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class PhoenixJourneyFragment extends Fragment {

    // Declaración de los elementos del layout
    private ImageButton bancaButton;
    private ImageButton sentadillaButton, farmerswalkbutton, flexionesconpesobutton, dominadasestrictasbutton,
            prensadepiernasbutton, plankconpesobutton;
    private ImageButton pesomuertoButton, pasosButton, entrenamientoButton, alimentacionButton, amigosButton;

    private TextView bancaText, sentadillaText, pesomuertoText, pasosText, entrenamientoText, alimentacionText,
            farmers_walkText, flexiones_con_pesoText, dominadas_estrictasText, prensa_de_piernasText, plank_con_pesoText;

    private MiBaseDeDatos miBaseDeDatos;
    private int usuarioIdActivo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoenix_journey, container, false);

        // Inicializar la base de datos
        miBaseDeDatos = new MiBaseDeDatos(getContext());

        // Obtener el ID del usuario activo
        usuarioIdActivo = miBaseDeDatos.obtenerUsuarioIdActivo();

        // Inicializar las vistas
        bancaButton = view.findViewById(R.id.banca_button);
        sentadillaButton = view.findViewById(R.id.sentadilla_button);
        pesomuertoButton = view.findViewById(R.id.pesomuerto_button);
        pasosButton = view.findViewById(R.id.pasos_button);
        entrenamientoButton = view.findViewById(R.id.entrenamiento_button);
        alimentacionButton = view.findViewById(R.id.alimentacion_button);
        farmerswalkbutton = view.findViewById(R.id.farmers_walk_button);
        flexionesconpesobutton = view.findViewById(R.id.flexiones_con_peso_button);
        dominadasestrictasbutton = view.findViewById(R.id.dominadas_estrictas_button);
        prensadepiernasbutton = view.findViewById(R.id.prensa_de_piernas_button);
        plankconpesobutton = view.findViewById(R.id.plank_con_peso_button);
        amigosButton = view.findViewById(R.id.amigos_button);

        bancaText = view.findViewById(R.id.banca);
        sentadillaText = view.findViewById(R.id.sentadilla);
        pesomuertoText = view.findViewById(R.id.pesomuerto);
        pasosText = view.findViewById(R.id.pasos);
        entrenamientoText = view.findViewById(R.id.entrenamiento);
        alimentacionText = view.findViewById(R.id.alimentacion);
        farmers_walkText = view.findViewById(R.id.farmers_walk);
        flexiones_con_pesoText = view.findViewById(R.id.flexiones_con_peso);
        dominadas_estrictasText = view.findViewById(R.id.dominadas_estrictas);
        prensa_de_piernasText = view.findViewById(R.id.prensa_de_piernas);
        plank_con_pesoText = view.findViewById(R.id.plank_con_peso);

        // Configurar la visibilidad inicial de los botones desde la base de datos
        configurarVisibilidadBotones();

        // Configurar los listeners para los botones
        bancaText.setOnClickListener(v -> toggleBancaButton());
        sentadillaText.setOnClickListener(v -> toggleSentadillaButton());
        pesomuertoText.setOnClickListener(v -> togglePesomuertoButton());
        pasosText.setOnClickListener(v -> togglePasosButton());
        entrenamientoText.setOnClickListener(v -> toggleEntrenamientoButton());
        alimentacionText.setOnClickListener(v -> toggleAlimentacionButton());
        farmers_walkText.setOnClickListener(v -> togglefarmerswalkbutton());
        flexiones_con_pesoText.setOnClickListener(v -> toggleflexionesconpesobutton());
        dominadas_estrictasText.setOnClickListener(v -> toggledominadasestrictasbutton());
        prensa_de_piernasText.setOnClickListener(v -> toggleprensadepiernasbutton());
        plank_con_pesoText.setOnClickListener(v -> toggleplankconpesobutton());

        // Configurar el botón Amigos para ir a AmigosActivity
        amigosButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AmigosActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void configurarVisibilidadBotones() {
        bancaButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "banca") ? View.VISIBLE : View.GONE);
        sentadillaButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "sentadilla") ? View.VISIBLE : View.GONE);
        pesomuertoButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "pesomuerto") ? View.VISIBLE : View.GONE);
        pasosButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "pasos") ? View.VISIBLE : View.GONE);
        entrenamientoButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "entrenamiento") ? View.VISIBLE : View.GONE);
        alimentacionButton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "alimentacion") ? View.VISIBLE : View.GONE);
        farmerswalkbutton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "farmerswalk") ? View.VISIBLE : View.GONE);
        flexionesconpesobutton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "flexionesconpeso") ? View.VISIBLE : View.GONE);
        dominadasestrictasbutton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "dominadasestrictas") ? View.VISIBLE : View.GONE);
        prensadepiernasbutton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "prensadepiernas") ? View.VISIBLE : View.GONE);
        plankconpesobutton.setVisibility(miBaseDeDatos.obtenerEstadoBoton(usuarioIdActivo, "plankconpeso") ? View.VISIBLE : View.GONE);
    }

    public void toggleBancaButton() {
        boolean esVisible = bancaButton.getVisibility() == View.GONE;
        bancaButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "banca", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        bancaButton.startAnimation(anim);
    }

    public void toggleSentadillaButton() {
        boolean esVisible = sentadillaButton.getVisibility() == View.GONE;
        sentadillaButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "sentadilla", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        sentadillaButton.startAnimation(anim);
    }

    public void togglePesomuertoButton() {
        boolean esVisible = pesomuertoButton.getVisibility() == View.GONE;
        pesomuertoButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "pesomuerto", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        pesomuertoButton.startAnimation(anim);
    }

    public void togglePasosButton() {
        boolean esVisible = pasosButton.getVisibility() == View.GONE;
        pasosButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "pasos", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        pasosButton.startAnimation(anim);
    }

    public void toggleEntrenamientoButton() {
        boolean esVisible = entrenamientoButton.getVisibility() == View.GONE;
        entrenamientoButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "entrenamiento", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        entrenamientoButton.startAnimation(anim);
    }

    public void toggleAlimentacionButton() {
        boolean esVisible = alimentacionButton.getVisibility() == View.GONE;
        alimentacionButton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "alimentacion", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        alimentacionButton.startAnimation(anim);
    }

    public void togglefarmerswalkbutton() {
        boolean esVisible = farmerswalkbutton.getVisibility() == View.GONE;
        farmerswalkbutton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "farmerswalk", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        farmerswalkbutton.startAnimation(anim);
    }

    public void toggleflexionesconpesobutton() {
        boolean esVisible = flexionesconpesobutton.getVisibility() == View.GONE;
        flexionesconpesobutton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "flexionesconpeso", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        flexionesconpesobutton.startAnimation(anim);
    }

    public void toggledominadasestrictasbutton() {
        boolean esVisible = dominadasestrictasbutton.getVisibility() == View.GONE;
        dominadasestrictasbutton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "dominadasestrictas", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        dominadasestrictasbutton.startAnimation(anim);
    }

    public void toggleprensadepiernasbutton() {
        boolean esVisible = prensadepiernasbutton.getVisibility() == View.GONE;
        prensadepiernasbutton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "prensadepiernas", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        prensadepiernasbutton.startAnimation(anim);
    }

    public void toggleplankconpesobutton() {
        boolean esVisible = plankconpesobutton.getVisibility() == View.GONE;
        plankconpesobutton.setVisibility(esVisible ? View.VISIBLE : View.GONE);
        miBaseDeDatos.actualizarEstadoBoton(usuarioIdActivo, "plankconpeso", esVisible);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
        plankconpesobutton.startAnimation(anim);
    }
}
