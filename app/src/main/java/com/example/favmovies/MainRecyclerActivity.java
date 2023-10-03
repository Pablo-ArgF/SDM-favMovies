package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Pelicula;

import java.util.ArrayList;

public class MainRecyclerActivity extends AppCompatActivity {

    private static final String PELICULA_SELECCIONADA = "Pelicula seleccionada";
    private ArrayList<Pelicula> listaPeli;
    private RecyclerView listaPeliView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        //Rellenar lista de peliculas

        rellenarLista();

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
    }
    public void clickonItem (Pelicula peli){
        Log.i("Click adapter","Item Clicked "+peli.getCategoria().getNombre());
        //Toast.makeText(MainActivity.this, "Item Clicked "+user.getId(), Toast.LENGTH_LONG).show();

        //Paso el modo de apertura
        Intent intent=new Intent (MainRecyclerActivity.this, MainActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);

        startActivity(intent);
    }

    private void rellenarLista() {
        listaPeli = new ArrayList<Pelicula>();

        Categoria cataccion = new Categoria("Accion","Pelisaccion");
        Pelicula peli = new Pelicula("Telnet","Una accion epica blabla", cataccion,"150","10/01/2020");
        Pelicula peli2 = new Pelicula("BabyDriver","conduce y tal", cataccion,"150","10/01/2020");
        
        
        listaPeli.add(peli);
        listaPeli.add(peli2);
    }
}