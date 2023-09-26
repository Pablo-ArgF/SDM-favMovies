package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.favmovies.modelo.Categoria;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Snackbar msgCreaCategoria;
    private Spinner spinner;
    private ArrayList<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    //TODO acabar esto en la clase
                    /*msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal),
                            R.string.msg_crearcategoria,Snackbar.LENGTH_LONG);*/
                }
                else {
                    //TODO acabar esto en la clase
                    /*msgCreaCategoria = Snackbar.make(findViewById(R.id.layoutPrincipal),
                            R.string.msg_crear,Snackbar.LENGTH_LONG);*/
                }
            }
        });

        listaCategorias = new ArrayList<>();
        listaCategorias.add(new Categoria("Accion","Pelicula de accion"));
        listaCategorias.add(new Categoria("Comedia","Pelicula de comedia"));

        spinner = (Spinner) findViewById(R.id.SpinnerCategoría);
        introListaSpinner(spinner,listaCategorias);


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