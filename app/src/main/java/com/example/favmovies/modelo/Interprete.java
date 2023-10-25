package com.example.favmovies.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "interpretes")
public class Interprete implements Parcelable {

    @PrimaryKey
    @NonNull
    private int id;
    @ColumnInfo(name="nombre")
    private String nombre;
    @ColumnInfo(name = "url_interprete")
    private String imagen;
    @ColumnInfo(name = "url_imdb")
    private String urlImdb;

    public Interprete() {

    }

    public Interprete(int id, String nombre, String imagen, String urlImdb)
    {
        this.setId(id);
        this.setNombre(nombre);
        this.setImagen(imagen);
        this.setUrlImdb(urlImdb);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrlImdb() {
        return urlImdb;
    }

    public void setUrlImdb(String urlImdb) {
        this.urlImdb = urlImdb;
    }


    /* Interface Parcelable */

    @Override
    public int describeContents() {
        return 0;
    }

    protected Interprete(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        imagen = in.readString();
        urlImdb = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(imagen);
        parcel.writeString(urlImdb);
    }

    public static final Creator<Interprete> CREATOR = new Creator<Interprete>() {
        @Override
        public Interprete createFromParcel(Parcel in) {
            return new Interprete(in);
        }

        @Override
        public Interprete[] newArray(int size) {
            return new Interprete[size];
        }
    };
}
