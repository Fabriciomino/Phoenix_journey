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



public class PhoenixJourneyFragment extends Fragment {

    // Declaración de los elementos del layout
    private ImageButton bancaButton;
    private ImageButton sentadillaButton;
    private ImageButton pesomuertoButton;
    private ImageButton pasosButton;
    private ImageButton entrenamientoButton;
    private ImageButton alimentacionButton;
    private ImageButton amigosButton;

    private ImageButton backButton;  // Botón Atrás

    private TextView bancaText;
    private TextView sentadillaText;
    private TextView pesomuertoText;
    private TextView pasosText;
    private TextView entrenamientoText;
    private TextView alimentacionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragment
        View view = inflater.inflate(R.layout.fragment_phoenix_journey, container, false);



        // Inicializar las vistas
        bancaButton = view.findViewById(R.id.banca_button);
        sentadillaButton = view.findViewById(R.id.sentadilla_button);
        pesomuertoButton = view.findViewById(R.id.pesomuerto_button);
        pasosButton = view.findViewById(R.id.pasos_button);
        entrenamientoButton = view.findViewById(R.id.entrenamiento_button);
        alimentacionButton = view.findViewById(R.id.alimentacion_button);
        backButton = view.findViewById(R.id.back_button);
        amigosButton = view.findViewById(R.id.amigos_button);

        bancaText = view.findViewById(R.id.banca);
        sentadillaText = view.findViewById(R.id.sentadilla);
        pesomuertoText = view.findViewById(R.id.pesomuerto);
        pasosText = view.findViewById(R.id.pasos);
        entrenamientoText = view.findViewById(R.id.entrenamiento);
        alimentacionText = view.findViewById(R.id.alimentacion);

        // Configurar botones para que inicien ocultos
        bancaButton.setVisibility(View.GONE);
        sentadillaButton.setVisibility(View.GONE);
        pesomuertoButton.setVisibility(View.GONE);
        pasosButton.setVisibility(View.GONE);
        entrenamientoButton.setVisibility(View.GONE);
        alimentacionButton.setVisibility(View.GONE);

        // Configurar el botón Atrás para regresar a MenuActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
                getActivity().finish();  // Finaliza la actividad actual para que no quede en la pila de actividades
            }
        });

        // Configurar el botón Amigos para ir a AmigosActivity
        amigosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AmigosActivity.class);
                startActivity(intent);
            }
        });

        bancaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBancaButton();
            }
        });

        sentadillaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSentadillaButton();
            }
        });

        pesomuertoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePesomuertoButton();
            }
        });

        pasosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasosButton();
            }
        });

        entrenamientoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEntrenamientoButton();
            }
        });

        alimentacionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAlimentacionButton();
            }
        });




        return view;
    }

    // Métodos para alternar la visibilidad de los botones
    public void toggleBancaButton() {
        if (bancaButton.getVisibility() == View.GONE) {
            bancaButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            bancaButton.startAnimation(anim);

        } else {
            bancaButton.setVisibility(View.GONE);
        }
    }


    public void toggleSentadillaButton() {
        if (sentadillaButton.getVisibility() == View.GONE) {
            sentadillaButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            sentadillaButton.startAnimation(anim);

        } else {
            sentadillaButton.setVisibility(View.GONE);
        }
    }

    public void togglePesomuertoButton() {
        if (pesomuertoButton.getVisibility() == View.GONE) {
            pesomuertoButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            pesomuertoButton.startAnimation(anim);
        } else {
            pesomuertoButton.setVisibility(View.GONE);
        }
    }

    public void togglePasosButton() {
        if (pasosButton.getVisibility() == View.GONE) {
            pasosButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            pasosButton.startAnimation(anim);
        } else {
            pasosButton.setVisibility(View.GONE);
        }
    }

    public void toggleEntrenamientoButton() {
        if (entrenamientoButton.getVisibility() == View.GONE) {
            entrenamientoButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            entrenamientoButton.startAnimation(anim);
        } else {
            entrenamientoButton.setVisibility(View.GONE);
        }
    }

    public void toggleAlimentacionButton() {
        if (alimentacionButton.getVisibility() == View.GONE) {
            alimentacionButton.setVisibility(View.VISIBLE);
            Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_and_aura);
            alimentacionButton.startAnimation(anim);
        } else {
            alimentacionButton.setVisibility(View.GONE);
        }
    }
}