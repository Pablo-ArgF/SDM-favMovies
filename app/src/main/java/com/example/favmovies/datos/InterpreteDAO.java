package com.example.favmovies.datos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.favmovies.modelo.Interprete;
import com.example.favmovies.modelo.Pelicula;

import java.util.List;


/*
    Al utilizar @Dao, Room generará el código necesario en tiempo de compilación
    Puede ser una interface o una clase abstracta (casos más complejos).
 */
@Dao
public interface InterpreteDAO {

    /*
        Más info sobre anotaciones en:
            https://developer.android.com/training/data-storage/room/accessing-data?hl=es-419
     */


    /*
        @Insert nos permite serializar un objeto.
        El retorno puede ser int (devolverá la id generada).

        El onConflict indica cómo proceder en caso de encontrarse con claves primarias repetidas.
        En este claso, indicamos que ignore la inserción.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Interprete interprete);

    /*
        @Query nos permite crear una consulta.
        Fíjate en el segundo caso,
            findById recibe el párametro peliculaId
            y lo utiliza mediante (:nombre_parametro) en la consulta.
     */

    @Query("SELECT * FROM interpretes")
    List<Interprete> getAll();


    @Query("SELECT * FROM interpretes WHERE id = (:interpreteId)")
    Interprete findById(int interpreteId);


/*

    @Transaction
    @Query("SELECT * FROM peliculas WHERE id = (:peliculaId) LIMIT 1")
    public PeliculaConReparto getPeliculaConReparto(int peliculaId);*/

}
