package com.example.estudiante.social;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Home extends AppCompatActivity implements Observer, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Usuario perfil_usuario;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_comentarios;

    private LinkedList<Publicacion> publicaciones = null;
    private LinkedList<Comentario> comentarios = null;

    private PublicacionAdapter publicacionAdapter;
    private ComentarioAdapter comentarioAdapter;

    private Button bt_publicar;
    private Button fab;
    private FloatingActionButton bt_actualizar;
    private EditText escribir_publicacion;
    private boolean estan_actualizadas;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        estan_actualizadas = false;
        ComunicacionCliente.getInstance().addObserver(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            perfil_usuario = (Usuario) extras.get("USUARIO");
            System.out.println("Se recibio el perfil: " + perfil_usuario.getNombre());
        }

        recyclerView = (RecyclerView) findViewById(R.id.r_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        bt_publicar = (Button) findViewById(R.id.bt_publicar);
        bt_publicar.setOnClickListener(this);

        bt_actualizar = (FloatingActionButton)  findViewById(R.id.bt_actualizar);
        bt_actualizar.setOnClickListener(this);

        escribir_publicacion = (EditText) findViewById(R.id.escribir_publicacion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        View inflatedView = getLayoutInflater().inflate(R.layout.item, null);
//        fab = (Button) inflatedView.findViewById(R.id.fab);
//        fab.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ComunicacionCliente.getInstance().enviar(new Mensaje("r"));
        ComunicacionCliente.getInstance().enviar(new Mensaje("r"));
    }

    private void draw(final int casos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                switch (casos) {
                    case 0:
                        if (publicaciones != null) {
                            recyclerView.setAdapter(publicacionAdapter);
                            System.out.println("Fin");
                            onToast(findViewById(R.id.activity_home), "Publicaciones Actualizadas");
                        }
                        break;
                    case 1:
                        onToast(findViewById(R.id.activity_home), "Publicada");
                        break;
                }
            }
        });
    }

    public void onToast(View v, String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Publicacion) {

            System.out.println("llega la publicacion");
            Publicacion p = (Publicacion) o;
            System.out.println(p.getTexto());
            publicaciones.addFirst(p);
            draw(1);
            System.out.println(publicaciones.size());

        } else if (o instanceof LinkedList) {
            System.out.println("Llega una Lista: " + ((LinkedList) o).size());
            publicaciones = (LinkedList) o;
            publicacionAdapter = new PublicacionAdapter(this, publicaciones);
            System.out.println("La agrego y adciones el adaptador a la vista");
            draw(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_publicar:

                if (publicaciones != null) {
                    if (escribir_publicacion.getText().toString().length() > 0) {

                        String nombre_usuario = perfil_usuario.getNombre();
                        String contenido = escribir_publicacion.getText().toString();
                        String correo = perfil_usuario.getCorreo();

                        Publicacion p = new Publicacion(nombre_usuario, contenido, correo);
                        ComunicacionCliente.getInstance().enviar(p);
                    }
                } else {
                    publicaciones = new LinkedList<>();
                }

                break;
            case R.id.bt_actualizar:
                ComunicacionCliente.getInstance().enviar(new Mensaje("r"));
                break;

            case R.id.fab:
                //Snackbar.make(view, "Genial! Enviaste una solicitud de ayuda", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;

            default:
            break;
        }
    }

}
