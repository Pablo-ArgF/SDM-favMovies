package com.example.favmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favmovies.modelo.Pelicula;
import com.example.favmovies.ui.ActoresFragment;
import com.example.favmovies.ui.ArgumentoFragment;
import com.example.favmovies.ui.InfoFragment;
import com.example.favmovies.util.Conexion;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//import es.uniovi.eii.sdm.modelo.Pelicula;
//import es.uniovi.eii.sdm.util.Conexion;


public class ShowMovie extends AppCompatActivity {

    private Pelicula pelicula;

    CollapsingToolbarLayout toolBarLayout;
    ImageView imagenFondo;
    TextView categoria;
    TextView estreno;
    TextView duracion;
    TextView argumento;
    ImageView caratula;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /* Cuando se selecciona uno de los botones / ítems*/
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (pelicula == null)
                return false;

            int itemId = item.getItemId();

            /* Según el caso, crearemos un Fragmento u otro */
            if (itemId == R.id.navigation_argumento)
            {
                /* Haciendo uso del FactoryMethod pasándole todos los parámetros necesarios */

                /* Argumento solamente necesita.... El argumento de la película */

                ArgumentoFragment argumentoFragment=ArgumentoFragment.newInstance
                        (pelicula.getArgumento());

                /* ¿Qué estaremos haciendo aquí? */
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, argumentoFragment).commit();
                return true;
            }

            if (itemId == R.id.navigation_actores)
            {
                ActoresFragment actoresFrag =
                        ActoresFragment.newInstance(pelicula.getId());

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, actoresFrag).commit();
                return true;
            }

            if (itemId == R.id.navigation_info)
            {
                InfoFragment infoFrag = InfoFragment.newInstance(pelicula.getDuracion(),
                        pelicula.getFecha(), pelicula.getUrlCaratula());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, infoFrag).commit();
                return true;
            }
            //Si no es nula y no entra... Algo falla.
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Recepción datos como activity secundaria
        Intent intentPeli= getIntent();
        pelicula= intentPeli.getParcelableExtra(MainRecyclerActivity.PELICULA_SELECCIONADA);

        // Gestión barra de la app
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        imagenFondo= (ImageView)findViewById(R.id.imagenFondo);

        // Gestión de los controles que contienen los datos de la película
        categoria= (TextView)findViewById(R.id.categoria);
        estreno= (TextView)findViewById(R.id.text_fecha);
        duracion= (TextView)findViewById(R.id.text_duracion);
        argumento= (TextView)findViewById(R.id.text_argumento);
        caratula= (ImageView)findViewById(R.id.img_caratula);

        if (pelicula!=null) //apertura en modo consulta
            mostrarDatos(pelicula);

        // Gestión del FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                verTrailer(pelicula.getUrlTrailer());
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    // Carga los datos que tenemos en la instancia en los componentes de la activity para mostrarlos
    public void mostrarDatos(Pelicula pelicula){
        if (!pelicula.getTitulo().isEmpty()) { //apertura en modo consulta
            //Actualizar componentes con valores de la pelicula específica
            String fecha= pelicula.getFecha();
            toolBarLayout.setTitle(pelicula.getTitulo()+" ("+fecha.substring(fecha.lastIndexOf('/') + 1)+")");
            // Imagen de fondo
            Picasso.get()
                    .load(pelicula.getUrlFondo()).into(imagenFondo);



            /*
            categoria.setText(pelicula.getCategoria().getNombre());
            estreno.setText(pelicula.getFecha());
            duracion.setText(pelicula.getDuracion());
            argumento.setText(pelicula.getArgumento());

            // Imagen de la carátula
            Picasso.get()
                    .load(pelicula.getUrlCaratula()).into(caratula);
            */

            ArgumentoFragment argumentoFragment=ArgumentoFragment.newInstance
                    (pelicula.getArgumento());

            /* ¿Qué estaremos haciendo aquí? */
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, argumentoFragment).commit();

        }
    }


    /**
     * Abre el diálogo de compartir para que el usuario elija una app
     * Luego envia el texto que repreenta la pelicula
     */
    public void compartirPeli(){
        /* es necesario hacer un intent con la constate ACTION_SEND */
        /*Llama a cualquier app que haga un envío*/
        Intent itSend = new Intent(Intent.ACTION_SEND);
        /* vamos a enviar texto plano */
        itSend.setType("text/plain");
        // itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{para});
        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + pelicula.getTitulo());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.titulo)
                +": "+pelicula.getTitulo()+"\n"+
                getString(R.string.contenido)
                +": "+pelicula.getArgumento());

        /* iniciamos la actividad */
                /* puede haber más de una aplicacion a la que hacer un ACTION_SEND,
                   nos sale un ventana que nos permite elegir una.
                   Si no lo pongo y no hay activity disponible, pueda dar un error */
        Intent shareIntent=Intent.createChooser(itSend, null);

        startActivity(shareIntent);

    }


    /**
     * Abre una activity con YouTube y muestra el vídeo indicado en el parámetro
     * @param urlTrailer url con el vídeo que se quiere visualizar
     */
    private void verTrailer(String urlTrailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlTrailer)));
    }


}