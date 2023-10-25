package com.example.favmovies.datos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.favmovies.modelo.Pelicula;
import com.example.favmovies.modelo.PeliculaConReparto;

import java.util.List;


/*
    Al utilizar @Dao, Room generará el código necesario en tiempo de compilación
    Puede ser una interface o una clase abstracta (casos más complejos).
 */
@Dao
public interface InterpretePeliculaCrossRefDAO {

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
    void add(InterpretePeliculaCrossRef interpretePeliculaCrossRef);


    @Transaction
    @Query("SELECT * FROM peliculas WHERE id = (:peliculaId)")
    public PeliculaConReparto getPeliculaConReparto(int peliculaId);


/*

    @Transaction
    @Query("SELECT * FROM peliculas WHERE id = (:peliculaId) LIMIT 1")
    public PeliculaConReparto getPeliculaConReparto(int peliculaId);*/

}
