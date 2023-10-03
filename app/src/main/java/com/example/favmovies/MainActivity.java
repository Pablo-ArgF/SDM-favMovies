package com.example.favmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.favmovies.modelo.Categoria;
import com.example.favmovies.modelo.Pelicula;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String POS_CATEGORIA_SELECCIONADA = " ";
    public static final int GESTION_CATEOGIRA = 1;
    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoría_modificada";
    public static final int IDENTIFICADOR_MAIN_ACTIVITY = 777;

    public static final int RESULT_OK = 2;

    private Snackbar msgCreaCategoria;
    private Spinner spinner;
    private ArrayList<Categoria> listaCategorias;
    private boolean creandoCategoria = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Hola","Hola");

        //metemos los listeners despues de que se haga el setcontetview para asegurar
        //de que el componente está en memoria
        Button btnGuardar = findViewById(R.id.BtnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                //validacion de campos
                if(validarCampos()){
                    Snackbar.make(findViewById(R.id.layoutPrincipal),"Elemento guardado",
                            Snackbar.LENGTH_LONG)
                            .show();
                    Intent intentResultado = new Intent();
                    EditText inputTitulo = findViewById(R.id.InputTitulo);
                    EditText inputArgumento = findViewById(R.id.InputArgumento);
                    EditText inputFecha = findViewById(R.id.FechaPelicula);
                    EditText inputDuracion = findViewById(R.id.DuracionPelicula);
                    Pelicula peli = new Pelicula(inputTitulo.getText().toString(),inputArgumento.getText().toString(),
                            listaCategorias.get(spinner.getSelectedItemPosition() - 1),
                            inputFecha.getText().toString(),inputDuracion.getText().toString());

                    intentResultado.putExtra(MainRecyclerActivity.PELICULA_CREADA,peli);
                    setResult(MainActivity.RESULT_OK,intentResultado);
                    finish();
                }
            }
        });

        //boton de edicion
        ImageButton btnEdit = findViewById(R.id.EditarBoton);
        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Spinner spinner = findViewById(R.id.SpinnerCategoría);
                if(spinner.getSelectedItemPosition() == 0){ //si esta sin categoria
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal),
                            R.string.msg_crearcategoria,Snackbar.LENGTH_LONG);
                }
                else {
                    //TODO acabar esto en la clase
                    /*msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal),
                            R.string.msg_crear,Snackbar.LENGTH_LONG);*/
                }


                msgCreaCategoria.setAction(android.R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.layoutPrincipal),"Accion cancelada",
                                Snackbar.LENGTH_LONG).show();
                    }
                });

                //accion de crear una categoria
                msgCreaCategoria.setAction(android.R.string.ok, new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.layoutPrincipal),"Accion realizada",
                                Snackbar.LENGTH_LONG).show();
                        modificarCategoria();
                    }

                });
                //IMPORTANTE para ver el snackbar
                msgCreaCategoria.show();
            }
        });

        listaCategorias = new ArrayList<>();
        listaCategorias.add(new Categoria("Accion","Pelicula de accion"));
        listaCategorias.add(new Categoria("Comedia","Pelicula de comedia"));

        spinner = (Spinner) findViewById(R.id.SpinnerCategoría);
        introListaSpinner(spinner,listaCategorias);


        //comprobarmos los valores del bundle en caso que este consultando la pelicula
        Intent intentPeli = getIntent();
        Pelicula peli = intentPeli.getParcelableExtra(MainRecyclerActivity.PELICULA_SELECCIONADA);
        if(peli != null){ //Estamos en modo enseñar una peli
            //desactivamos todos los fields para que no pueda tocarlos junto con el boton de guardar
            btnGuardar.setEnabled(false);
            EditText inputTitulo = findViewById(R.id.InputTitulo);
            EditText inputArgumento = findViewById(R.id.InputArgumento);
            EditText inputFecha = findViewById(R.id.FechaPelicula);
            EditText inputDuracion = findViewById(R.id.DuracionPelicula);
            inputTitulo.setEnabled(false);
            inputArgumento.setEnabled(false);
            inputFecha.setEnabled(false);
            inputDuracion.setEnabled(false);

            inputTitulo.setText(peli.getTitulo());
            inputArgumento.setText(peli.getArgumento());
            inputFecha.setText(peli.getFecha());
            inputDuracion.setText(peli.getDuracion());
            //TODO falta la categoria
            int posicion = -1;
            for (int i = 0; i<listaCategorias.size() ; i++) {
                if (listaCategorias.get(i).getNombre().equals(peli.getCategoria().getNombre()))
                    posicion = i;
                i++;
            }
            spinner.setSelection(posicion);
            spinner.setEnabled(false);
        }



    }

    private void modificarCategoria(){
        // En el intent pasamos datos de una categoria a otra
        Intent categoriaIntent = new Intent(MainActivity.this, CategoriasActivity.class);
        // Lanza activity categoria sin pasarle niguna categoria primero
        // startActivity(categoriaIntent);

        Log.i("Posicion Categoría Seleccionada Spinner", spinner.getSelectedItemPosition() + " ");
        categoriaIntent.putExtra(POS_CATEGORIA_SELECCIONADA, spinner.getSelectedItemPosition());

        creandoCategoria = true;
        if (spinner.getSelectedItemPosition() > 0) {
            creandoCategoria = false;
            categoriaIntent.putExtra(CATEGORIA_SELECCIONADA, listaCategorias.get(spinner.getSelectedItemPosition() - 1));
        }

        // lanzamos activity para gestion cateogria esperando por un resultado
        startActivityForResult(categoriaIntent, GESTION_CATEOGIRA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return false; //TODO acabar esto en clase
        /*if(item.getItemId() == R.id.Compartir){
            Conexion conexion = new Conexion(getApplicationContext());
            if(conexion.compruebaConexion()){
                compartirPeli();
            }
            else Toast.makeText(getApplicationContext(),
                    "No se ha establecido la la conexino. Comprueba tu conexion",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);*/
    }

    private void introListaSpinner(Spinner spinner, ArrayList<Categoria> listaCategorias){
        //creamos un array con nombres de categorias
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Sin definir");
        for(Categoria cat : listaCategorias){
            nombres.add(cat.getNombre());
        }

        //crea un ArrayAdapter usando un array de strings y el layout por defecto del spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,nombres);
        //especifica el layout para usar cuando aparece la lista de elecciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //aplicar el adaptador al spinner
        spinner.setAdapter(adapter);

    }

    /**
     * Valida que los campos del formulario han sido correctamente rellenados
     * @return
     */
    private boolean validarCampos() {
        boolean result = true;

        EditText inputTitulo = findViewById(R.id.InputTitulo);
        EditText inputArgumento = findViewById(R.id.InputArgumento);
        EditText inputFecha = findViewById(R.id.FechaPelicula);
        EditText inputDuracion = findViewById(R.id.DuracionPelicula);


        //validamos que el titulo no esté vacio
        String titulo = inputTitulo.getText().toString();
        if(titulo.trim().isEmpty()){
            inputTitulo.setError(getString(R.string.e_tituloInvalido));
            result = false;
        }
        //hacemos lo mismo con el argumento de la pelicula
        String argumento = inputArgumento.getText().toString();
        if(argumento.trim().isEmpty()){
            inputArgumento.setError(getString(R.string.e_descripcionInvalida));
            result = false;
        }
        //validamos también la duración de la pelicula:
        //  ha de tener un valor --> no puede estar vacía
        //  ha de tener el formato HH:mm
        String duracion = inputDuracion.getText().toString();
        if(duracion.trim().isEmpty()){
            inputDuracion.setError(getString(R.string.e_duracionInvalida_vacia));
            result = false;
        }
        else {
            //probamos a formatear el input a HH:mm
            try {
                Date time = new SimpleDateFormat("HH:mm").parse(duracion);
            } catch (ParseException e) {
                result = false;
                inputDuracion.setError(getString(R.string.e_duracionInvalida_formato));
            }
        }

        //validamos también la fecha de salida de la pelicula:
        //  ha de tener un valor --> no puede estar vacía
        //  ha de tener el formato dd/MM/yyyy
        String fecha = inputFecha.getText().toString();
        if(fecha.trim().isEmpty()){
            inputFecha.setError(getString(R.string.e_fechaInvalida_vacia));
            result = false;
        }
        else {
            //probamos a formatear el input a dd/MM/yyyy
            try {
                Date fechaDate = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            } catch (ParseException e) { //si no nos permite formatearlo no cumple el formato correcto
                result = false;
                inputFecha.setError(getString(R.string.e_fechaInvalida_formato));
            }
        }

        return result;
    }
}