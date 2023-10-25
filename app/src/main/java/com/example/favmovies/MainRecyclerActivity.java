package com.example.favmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.favmovies.datos.AppDatabase;
import com.example.favmovies.datos.InterpretePeliculaCrossRef;
import com.example.favmovies.datos.PeliculaDAO;
import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Interprete;
import com.example.favmovies.modelo.Pelicula;
import com.example.favmovies.util.ImageManager;
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

    private String filtroCategoria = null;

    private AppDatabase appDatabase;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.filtroCategoria = sharedPreferences.getString("filtroCategoria", null);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);


        this.appDatabase = AppDatabase.getDatabase(this);


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
        cargarInterpretes();
        cargarRelacionInterpretePelicula();



        //cargamos las peliculas de la bd
        if(this.filtroCategoria == null)
            this.listaPeli = new ArrayList(appDatabase.getPeliculaDAO().getAll());
        else
            this.listaPeli = new ArrayList(appDatabase.getPeliculaDAO().findByCategoriaNombre(filtroCategoria));

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

    private void cargarRelacionInterpretePelicula() {

        InterpretePeliculaCrossRef ref;
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            file = getAssets().open("peliculas-reparto.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null) {
                    if (data.length==2) {
                        ref = new InterpretePeliculaCrossRef(Integer.parseInt(data[1]),Integer.parseInt(data[0]));
                        this.appDatabase.getInterpretePeliculaCrossRefDAO().add(ref);
                    }
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
    }
    private void cargarInterpretes() {

        Interprete interprete;
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            file = getAssets().open("interpretes.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null) {
                    if (data.length==4) {
                        interprete = new Interprete(Integer.parseInt(data[0]),data[1],data[2],data[3]);
                        this.appDatabase.getInterpreteDAO().add(interprete);
                    }
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void cargarReparto() {

        InterpretePeliculaCrossRef interpretePeliculaCrossRef;
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            file = getAssets().open("peliculas-reparto.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data != null) {
                    if (data.length==2) {
                        interpretePeliculaCrossRef  = new InterpretePeliculaCrossRef(
                                Integer.parseInt(data[1]), Integer.parseInt(data[0]));

                        //añadimos los datos a la bd
                        appDatabase.getInterpretePeliculaCrossRefDAO()
                                .add(interpretePeliculaCrossRef);

                    }
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
            file = getAssets().open("peliculas.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            int lineIndex = 1;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if(lineIndex++ > 1) {
                    if (data != null && data.length >= 5) {
                        //id;titulo;argumento;categoria;duracion;fecha;caratula;fondo;trailer
                        if (data.length == 9) {
                            peli = new Pelicula(Integer.parseInt(data[0]),data[1], data[2], new Categoria(data[3], ""), data[4], data[5],
                                    ImageManager.getImageCaratula(data[6]), data[7], data[8]);

                        } else {
                            peli = new Pelicula(Integer.parseInt(data[0]),data[1], data[2], new Categoria(data[3], ""), data[4], data[5],
                                    ImageManager.getImageCaratula(Caratula_por_defecto), fondo_por_defecto, trailer_por_defecto);
                        }
                        //cargamos en el DAO la pelicula
                        appDatabase.getPeliculaDAO().add(peli);
                        //listaPeli.add(peli);
                    }
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

    /**
     * Lee lista de películas desde el fichero csv en assets
     * Crea listaPeli como un ArrayList<Pelicula>
     */
    protected void cargarPeliculas(String filtro) {

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
            file = getAssets().open("peliculas.csv");
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            int lineIndex = 1;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                if(lineIndex++ > 1) {
                    if (data != null && data.length >= 5) {
                        //id;titulo;argumento;categoria;duracion;fecha;caratula;fondo;trailer
                        //id;titulo;argumento;categoria;duracion;fecha;caratula;fondo;trailer
                        if (data.length == 9) {
                            peli = new Pelicula(Integer.parseInt(data[0]),data[1], data[2], new Categoria(data[3], ""), data[4], data[5],
                                    data[6], data[7], data[8]);
                        } else {
                            peli = new Pelicula(Integer.parseInt(data[0]),data[1], data[2], new Categoria(data[3], ""), data[4], data[5],
                                    Caratula_por_defecto, fondo_por_defecto, trailer_por_defecto);
                        }
                        listaPeli.add(peli);
                    }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.settings) {
            //lanzamos settingsactivity
            Intent intentSettings = new Intent(MainRecyclerActivity.this, SettingsActivity.class);

            // lanzamos activity para gestion cateogria esperando por un resultado
            startActivity(intentSettings);
        }
        return super.onOptionsItemSelected(item);
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

}