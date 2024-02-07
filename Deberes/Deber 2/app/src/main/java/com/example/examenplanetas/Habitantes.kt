package com.example.examenplanetas

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class Habitantes : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<BHabitante>
    val callbackCrearHabitante =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoHabitante = BHabitante(
                        data?.getStringExtra("nombreHabitante").toString(),
                        data?.getStringExtra("reinoHabitante").toString(),
                        data?.getStringExtra("tipoHabitante").toString(),
                        data?.getBooleanExtra("extintoHabitante", true).toString().toBoolean(),
                        data?.getStringExtra("reproduccionHabitante").toString()
                    )


                    val respuesta = BaseDeDatos
                        .tablaHabitante!!.crearHabitante(
                            nuevoHabitante.nombre,
                            nuevoHabitante.reino,
                            nuevoHabitante.tipo,
                            nuevoHabitante.extinto,
                            nuevoHabitante.tipoReproduccion,
                            MainActivity.idItemSeleccionado
                        )

                    MainActivity.arreglo[MainActivity.posicionItemSeleccionado].arregloHabitantes.add(
                        nuevoHabitante
                    )
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nombre =
                        data?.getStringExtra("nombreHabitante").toString()
                    val reino =
                        data?.getStringExtra("reinoHabitante").toString()
                    val tipo =
                        data?.getStringExtra("tipoHabitante").toString()
                    val extinto =
                        data?.getBooleanExtra("extintoHabitante", true).toString().toBoolean()
                    val tipoReproduccion =
                        data?.getStringExtra("reproduccionHabitante").toString()
                    val respuesta = BaseDeDatos
                        .tablaHabitante!!.actualizarHabitanteFormulario(
                            nombre,
                            reino,
                            tipo,
                            extinto,
                            tipoReproduccion,
                            MainActivity.idItemSeleccionado,
                            idHabitanteSeleccionado
                        )
                    //Lógica para modificar los datos del arreglo
                    arreglo[posicionItemSeleccionadoHabitantes].nombre =
                        data?.getStringExtra("nombreHabitante").toString()
                    arreglo[posicionItemSeleccionadoHabitantes].reino =
                        data?.getStringExtra("reinoHabitante").toString()
                    arreglo[posicionItemSeleccionadoHabitantes].tipo =
                        data?.getStringExtra("tipoHabitante").toString()
                    arreglo[posicionItemSeleccionadoHabitantes].extinto =
                        data?.getBooleanExtra("extintoHabitante", true).toString().toBoolean()
                    arreglo[posicionItemSeleccionadoHabitantes].tipoReproduccion =
                        data?.getStringExtra("reproduccionHabitante").toString()

                    MainActivity.arreglo[MainActivity.posicionItemSeleccionado]
                        .arregloHabitantes[posicionItemSeleccionadoHabitantes] =
                        arreglo[posicionItemSeleccionadoHabitantes]
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("Habitante modificado exitosamente")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseDeDatos.tablaPlaneta = SqliteHelperPlaneta(
            this
        )
        BaseDeDatos.tablaHabitante = SqliteHelperHabitante(
            this
        )

        val arregloHabitante = BaseDeDatos.tablaHabitante!!
            .consultarHabitantesPorIdPlaneta(MainActivity.idItemSeleccionado)

        val listaBHabitante = arregloHabitante.map { habitante ->
            BHabitante(
                habitante.nombre,
                habitante.reino,
                habitante.tipo,
                habitante.extinto,
                habitante.tipoReproduccion
            ).apply { id = habitante.id } // Asignar el id del planeta original al nuevo objeto BPlaneta
        }

        setContentView(R.layout.activity_habitantes)
        MainActivity.arreglo[MainActivity.posicionItemSeleccionado].arregloHabitantes =
            listaBHabitante as ArrayList<BHabitante>

        arreglo = listaBHabitante

        val listView = findViewById<ListView>(R.id.lv_habitantes)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val planetaSeleccionado = findViewById<TextView>(R.id.txt_planetaHabitantes)
        planetaSeleccionado.setText(MainActivity.arreglo[MainActivity.posicionItemSeleccionado].nombre)

        val botonCrearHabitante = findViewById<Button>(R.id.btn_crearHabitante)
        botonCrearHabitante
            .setOnClickListener {
                anadirHabitante()
            }
        val botonRegresar = findViewById<Button>(R.id.btn_regresarHabitantes)
        botonRegresar
            .setOnClickListener {
                finish()
            }
        registerForContextMenu(listView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_habitantes, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionadoHabitantes = posicion
        val habitanteSeleccionado = BaseDeDatos.tablaHabitante!!.consultarHabitantesPorIdPlaneta(MainActivity.idItemSeleccionado)[posicion]
        idHabitanteSeleccionado = habitanteSeleccionado.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editarHabitante -> {
                abrirActividadEditarHabitante(
                    EditarHabitante::class.java
                )
                return true
            }

            R.id.mi_eliminarHabitante -> {
                confirmarEliminacionDialogo()
                return true
            }


            else -> super.onContextItemSelected(item)
        }
    }

    fun anadirHabitante() {
        abrirActividadCrearHabitante(
            CrearHabitante::class.java
        )
        adaptador.notifyDataSetChanged()
    }

    fun confirmarEliminacionDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                val respuesta = BaseDeDatos
                    .tablaHabitante!!.eliminarHabitanteFormulario(
                        idHabitanteSeleccionado
                    )
                if (respuesta) mostrarSnackbar("Habitante Eliminado")
                MainActivity.arreglo[MainActivity.posicionItemSeleccionado]
                    .arregloHabitantes.removeAt(posicionItemSeleccionadoHabitantes)
                adaptador.notifyDataSetChanged()
                mostrarSnackbar("Planeta eliminado exitosamente")
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun devolverRespuesta(
        nombre: String,
        galaxia: String,
        estrella: String,
        masa: Double,
        edad: Long
    ) {
        //enviamos anteriormente, y ahora revolvemos o recibimos una respuesta
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", nombre)
        intentDevolverParametros.putExtra("galaxiaModificada", galaxia)
        intentDevolverParametros.putExtra("estrellaModificada", estrella)
        intentDevolverParametros.putExtra("masaModificada", masa)
        intentDevolverParametros.putExtra("edadModificada", edad)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    fun abrirActividadCrearHabitante(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)

        callbackCrearHabitante.launch(intent)

    }

    fun abrirActividadEditarHabitante(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parámetros (solamente variables primitivas)
        intentExplicito.putExtra("nombre", arreglo[posicionItemSeleccionadoHabitantes].nombre)
        intentExplicito.putExtra("reino", arreglo[posicionItemSeleccionadoHabitantes].reino)
        intentExplicito.putExtra("tipo", arreglo[posicionItemSeleccionadoHabitantes].tipo)
        intentExplicito.putExtra("extinto", arreglo[posicionItemSeleccionadoHabitantes].extinto)
        intentExplicito.putExtra(
            "reproduccion",
            arreglo[posicionItemSeleccionadoHabitantes].tipoReproduccion
        )


        callbackContenidoIntentExplicito.launch(intentExplicito)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_habitantes),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    companion object {
        var arreglo = arrayListOf<BHabitante>()
        var posicionItemSeleccionadoHabitantes = 0
        var idHabitanteSeleccionado = 0
    }
}