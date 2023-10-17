package com.example.favmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Pelicula;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainRecyclerActivity extends AppCompatActivity {

    public static final String PELICULA_SELECCIONADA = "Pelicula seleccionada";
    public static final String PELICULA_CREADA = "Pelicula creada";
    public static final int GESTION_ACTIVITY = 1;


    private ArrayList<Pelicula> listaPeli;
    private RecyclerView listaPeliView;
    private FloatingActionButton btnFloatingAddFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);



        // Recuperamos referencia y configuramos recyclerView con la lista de usuarios
        listaPeliView = (RecyclerView)findViewById(R.id.reciclerView);
        listaPeliView.setHasFixedSize(true);

        /* Un RecyclerView necesita un Layout Manager para manejar el posicionamiento de los
           elementos en cada línea. Se podría definir un LayoutManager propio extendendiendo la clase
           RecyclerView.LayoutManager. Sin embargo, en la mayoría de los casos, simplemente se usa
           una de las subclases LayoutManager predefinidas: LinearLayoutManager, GridLayoutManager,
           StaggeredGridLayoutManager*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaPeliView.setLayoutManager(layoutManager);

        //cargamos las peliculas
        cargarPeliculas();

        //Pasamos la lista de peliculas al RecyclerView con el ListaPeliculaAdapter
        // Instanciamos el adapter con los datos de la petición y lo asignamos a RecyclerView
        // Generar el adaptador, le pasamos la lista de peliculas
        // y el manejador para el evento click sobre un elemento
        ListaPeliculaAdapter lpAdapter= new ListaPeliculaAdapter(listaPeli,
                new ListaPeliculaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Pelicula peli) {
                        clickonItem(peli);
                    }
                });
        listaPeliView.setAdapter(lpAdapter);

        //añadimos la logica de añadir una pelicula
        /*btnFloatingAddFilm = findViewById(R.id.BtnAddFloating);
        //on click -> abrimos la pantalla main activity
        btnFloatingAddFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPantallaAddPelicula();
            }
        });*/



    }

    /**
     * Lee lista de películas desde el fichero csv en assets
     * Crea listaPeli como un ArrayList<Pelicula>
     */
    protected void cargarPeliculas() {

        /*si una película le falta la caratual, el fondo o el trailer, le pongo unos por defecto. De esta manera me aseguro
        estos campos en las películas*/

        String Caratula_por_defecto="https://image.tmdb.org/t/p/original/jnFCk7qGGWop2DgfnJXeKLZFuBq.jpg\n";
        String fondo_por_defecto="https://image.tmdb.org/t/p/original/xJWPZIYOEFIjZpBL7SVBGnzRYXp.jpg\n";
        String trailer_por_defecto="https://www.youtube.com/watch?v=lpEJVgysiWs\n";
        Pelicula peli;
        listaPeli = new ArrayList<Pelicula>();
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            file = getAssets().open("lista_peliculas_url_utf8.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null && data.length >= 5) {
                    if (data.length==8) {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""), data[3], data[4],
                                data[5], data[6], data[7]);
                    } else {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""), data[3], data[4],
                                Caratula_por_defecto, fondo_por_defecto, trailer_por_defecto);
                    }
                    Log.d("cargarPeliculas", peli.toString());
                    listaPeli.add(peli);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ListaPeliculaAdapter adapter = new ListaPeliculaAdapter(listaPeli,
                (pelicula) ->{
                    clickonItem(pelicula);
                });
        listaPeliView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GESTION_ACTIVITY){
            if(resultCode == MainActivity.RESULT_OK){
                Pelicula peli = data.getParcelableExtra(PELICULA_CREADA);

                listaPeli.add(peli);

                //actualizamos la info del adapter
                ListaPeliculaAdapter adapter = new ListaPeliculaAdapter(listaPeli,
                        (pelicula) ->{
                            clickonItem(pelicula);
                        });
                listaPeliView.setAdapter(adapter);
            }
        }

    }

    private void mostrarPantallaAddPelicula() {
        // En el intent pasamos datos de una categoria a otra
        Intent addViewIntent = new Intent(MainRecyclerActivity.this, MainActivity.class);
        //lanzamos la pantalla esperando respuesta
        startActivityForResult(addViewIntent, GESTION_ACTIVITY);

    }

    public void clickonItem (Pelicula peli){
        Log.i("Click adapter","Item Clicked "+peli.getCategoria().getNombre());
        //Toast.makeText(MainActivity.this, "Item Clicked "+user.getId(), Toast.LENGTH_LONG).show();

        //Paso el modo de apertura
        Intent intent=new Intent (MainRecyclerActivity.this, ShowMovie.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    private void rellenarLista() {
        listaPeli = new ArrayList<Pelicula>();

        Categoria cataccion = new Categoria("Accion","Pelisaccion");
        Pelicula peli = new Pelicula("Tenet","Una accion epica blabla", cataccion,"150","10/01/2020");
        Pelicula peli2 = new Pelicula("BabyDriver","conduce y tal", cataccion,"150","10/01/2020");
        
        
        listaPeli.add(peli);
        listaPeli.add(peli2);
    }
}