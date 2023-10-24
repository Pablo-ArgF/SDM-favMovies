package com.example.favmovies.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/*
    La anotación @Entity nos permite indicar que se mapeará en la base de datos como una tabla.
    Opcionalmente podemos indicar el nombre de la tabla mediante tableName.
 */
@Entity(tableName = "peliculas")
public class Pelicula implements Parcelable{


    /*
        Con @PrimaryKey indicamos que es id es clave primaria.
            Es posible hacer lo siguiente: @PrimaryKey(autoGenerate = true)
        @NonNull es autoexplicativo.
     */
    @PrimaryKey
    @NonNull
    int id;


    /*
        Con @ColumnInfo podemos indicar el nombre de la columna en la tabla
     */
    @ColumnInfo(name="titulo")
    String titulo;


    String argumento;


    /*
        Esto es lo más importante de este fichero.
        Categoria es un objeto y Room no almacena referencias a objetos.

        Una posibilidad sería utilizar un conversor:
            https://developer.android.com/training/data-storage/room/referencing-data?hl=es-419

        Sin embargo, vamos a solucionarlo con @Embebbed
        Esto añadirá tantas columnas a la tabla películas como atributos tenga Categoría.
        Mediante prefix indicamos un prefijo, creándose las columnas:
            categoria_nombre y categoria_descripcion

     */
    @Embedded(prefix="categoria_")
    Categoria categoria;


    String duracion;
    String fecha;


    //EJERCICIO: El nombre de la columna será url_caratula
    @ColumnInfo(name="url_caratula")
    String urlCaratula;

    //EJERCICIO: El nombre de la columna será url_fondo
    @ColumnInfo(name="url_fondo")
    String urlFondo;

    //EJERCICIO: El nombre de la columna será url_trailer
    @ColumnInfo(name="url_trailer")
    String urlTrailer;



    //EJERCICIO: Añade la id al constructor, getter, setter, toString y parcelable.
    public Pelicula(int id, String titulo, String argumento, Categoria categoria, String duracion, String fecha,
                    String caratula, String fondo, String trailer) {
        this.id = id;
        this.titulo = titulo;
        this.argumento = argumento;
        this.categoria = categoria;
        this.duracion = duracion;
        this.fecha = fecha;

        this.urlCaratula= caratula;
        this.urlFondo= fondo;
        this.urlTrailer= trailer;
    }

	//Constructor por defecto para evitar problemas.
	public Pelicula() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id='"+id+ '\''+
                "titulo='" + titulo + '\'' +
                ", argumento='" + argumento + '\'' +
                ", categoria=" + categoria.toString() +
                ", duracion='" + duracion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArgumento() {
        return argumento;
    }

    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getUrlCaratula() {
        return urlCaratula;
    }

    public void setUrlCaratula(String urlCaratula) {
        this.urlCaratula = urlCaratula;
    }

    public String getUrlFondo() {
        return urlFondo;
    }

    public void setUrlFondo(String urlFondo) {
        this.urlFondo = urlFondo;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }
    @Override
    public int describeContents() {
        return 0;
    }


	/* 
		Interface Parcelable 
	*/
	
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeString(argumento);
        parcel.writeParcelable(categoria, i);
        parcel.writeString(duracion);
        parcel.writeString(fecha);
        parcel.writeString(urlCaratula);
        parcel.writeString(urlFondo);
        parcel.writeString(urlTrailer);


    }

    protected Pelicula(Parcel in) {
        id=in.readInt();
        titulo = in.readString();
        argumento = in.readString();
        categoria = in.readParcelable(Categoria.class.getClassLoader());
        duracion = in.readString();
        fecha = in.readString();
        urlCaratula= in.readString();
        urlFondo= in.readString();
        urlTrailer= in.readString();
    }

    public static final Creator<Pelicula> CREATOR = new Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel in) {
            return new Pelicula(in);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };	
}
