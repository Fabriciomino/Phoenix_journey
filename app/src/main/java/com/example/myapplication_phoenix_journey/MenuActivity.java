package com.example.myapplication_phoenix_journey;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Establecer el fragmento inicial directamente
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Configurar el menú inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Establecer el primer ítem como seleccionado
        bottomNavigationView.setSelectedItemId(R.id.menu_icon_button); // Inicialmente se selecciona el Home

        bottomNavigationView.findViewById(R.id.menu_icon_button).setOnClickListener(v -> {
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.menu_icon_button);  // Actualizar el estado del item
        });

        bottomNavigationView.findViewById(R.id.fenix_journey).setOnClickListener(v -> {
            loadFragment(new PhoenixJourneyFragment());
            bottomNavigationView.setSelectedItemId(R.id.fenix_journey);  // Actualizar el estado del item
        });

        bottomNavigationView.findViewById(R.id.nutricion).setOnClickListener(v -> {
            loadFragment(new DietaFragment());
            bottomNavigationView.setSelectedItemId(R.id.nutricion);  // Actualizar el estado del item
        });

        bottomNavigationView.findViewById(R.id.calendario_button).setOnClickListener(v -> {
            loadFragment(new CalendarioFragment());
            bottomNavigationView.setSelectedItemId(R.id.calendario_button);  // Actualizar el estado del item
        });

        bottomNavigationView.findViewById(R.id.mas).setOnClickListener(v -> {
            loadFragment(new MiUsuarioFragment());
            bottomNavigationView.setSelectedItemId(R.id.mas);  // Actualizar el estado del item
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
