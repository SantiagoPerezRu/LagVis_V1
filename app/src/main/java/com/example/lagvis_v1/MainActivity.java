package com.example.lagvis_v1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

import FiniquitosPackage.DatosGeneralesDespidoFragment;
import VisualizadorPaginas.PaginaVidaLaboralFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    FirstFragment firstFragment = new FirstFragment();     // Inicio
    SecondFragment secondFragment = new SecondFragment();  // Noticias
    ThirdFragment thirdFragment = new ThirdFragment();     // Finiquitos
    FourthFragment fourthFragment = new FourthFragment();  // Tú perfil
    DatosGeneralesDespidoFragment fifhtFragment = new DatosGeneralesDespidoFragment(); // Despidos
    NoticiasGuardadasFragment fragmentNoticiasGuaradas = new NoticiasGuardadasFragment(); // Noticias guardadas fragment
    PaginaVidaLaboralFragment fragmentVidaLaboral = new PaginaVidaLaboralFragment(); // Vida Laboral Fragment
    CalendarioLaboral fragmentCalendarioLaboral = new CalendarioLaboral(); // Calendario laboral fragment


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        frameLayout = findViewById(R.id.frame_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Cargar FirstFragment automáticamente al iniciar
        if (savedInstanceState == null) {
            loadFragment(firstFragment);
            navigationView.setCheckedItem(R.id.home); // marcar como seleccionado en el menú lateral
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                loadFragment(firstFragment);
                break;
            case R.id.notice:
                loadFragment(secondFragment);
                break;
            case R.id.calc:
                loadFragment(thirdFragment);
                break;
            case R.id.settings:
                loadFragment(fourthFragment);
                break;
            case R.id.nav_vida_laboral:
                loadFragment(fragmentVidaLaboral);
                break;
            case R.id.nav_calculadoraDespidos:
                loadFragment(fifhtFragment);
                break;
            case R.id.noticiasGuardadas:
                loadFragment(fragmentNoticiasGuaradas);
                break;
            case R.id.calendarioLaboral:
                loadFragment(fragmentCalendarioLaboral);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}