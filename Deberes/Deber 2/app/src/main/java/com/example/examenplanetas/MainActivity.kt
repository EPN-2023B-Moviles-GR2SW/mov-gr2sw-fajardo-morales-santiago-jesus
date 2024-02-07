package com.example.examenplanetas

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    //val arreglo = BBaseDatosMemoria.arregloBPlaneta

    lateinit var adaptador: ArrayAdapter<BPlaneta>

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    //Lógica para modificar los datos del arreglo
                    val nombre = data?.getStringExtra("nombreModificado").toString()
                    val galaxia = data?.getStringExtra("galaxiaModificada").toString()
                    val estrella = data?.getStringExtra("estrellaModificada").toString()
                    val masa = data?.getDoubleExtra("masaModificada",0.0).toString().toDouble()
                    val edad = data?.getLongExtra("edadModificada",0).toString().toLong()

                    val respuesta = BaseDeDatos
                        .tablaPlaneta!!.actualizarPlanetaFormulario(
                            nombre,
                            galaxia,
                            estrella,
                            masa,
                            edad,
                            idItemSeleccionado
                        )

                    arreglo[posicionItemSeleccionado].nombre = data?.getStringExtra("nombreModificado").toString()
                    arreglo[posicionItemSeleccionado].galaxia = data?.getStringExtra("galaxiaModificada").toString()
                    arreglo[posicionItemSeleccionado].estrella = data?.getStringExtra("estrellaModificada").toString()
                    arreglo[posicionItemSeleccionado].masa = data?.getDoubleExtra("masaModificada",0.0).toString().toDouble()
                    arreglo[posicionItemSeleccionado].edad = data?.getLongExtra("edadModificada",0).toString().toLong()
                    adaptador.notifyDataSetChanged()
                    mostrarSnackbar("Planeta modificado exitosamente")
                }
            }
        }
    val callbackCrearPlaneta =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    val nuevoPlaneta = BPlaneta(
                        data?.getStringExtra("nombrePlaneta").toString(),
                        data?.getStringExtra("galaxiaPlaneta").toString(),
                        data?.getStringExtra("estrellaPlaneta").toString(),
                        data?.getDoubleExtra("masaPlaneta",0.0).toString().toDouble(),
                        data?.getLongExtra("edadPlaneta",0).toString().toLong(),
                        arrayListOf()
                    )
                    val respuesta = BaseDeDatos
                        .tablaPlaneta!!.crearPlaneta(
                            nuevoPlaneta.nombre,
                            nuevoPlaneta.galaxia,
                            nuevoPlaneta.estrella,
                            nuevoPlaneta.masa,
                            nuevoPlaneta.edad.toInt()
                        )
                    if (respuesta) mostrarSnackbar("Ent. Creado")

                    arreglo.add( nuevoPlaneta)
                    adaptador.notifyDataSetChanged()
                }
            }
        }
    val callbackHabitantes =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val data = result.data
                    adaptador.notifyDataSetChanged()
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

        val arregloPlanetas = BaseDeDatos.tablaPlaneta!!
            .consultarTodosPlanetas()
        val listaBPlanetas = arregloPlanetas.map { planeta ->
            BPlaneta(
                planeta.nombre,
                planeta.galaxia,
                planeta.estrella,
                planeta.masa,
                planeta.edad,
                arrayListOf()
            ).apply { id = planeta.id } // Asignar el id del planeta original al nuevo objeto BPlaneta
        }

        arreglo = listaBPlanetas as ArrayList<BPlaneta>

        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.lv_list_view)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearPlaneta = findViewById<Button>(R.id.btn_crear_planeta)
        botonCrearPlaneta
            .setOnClickListener {
                anadirPlaneta(adaptador)
                mostrarSnackbar("Planeta creado exitosamente")
            }
        registerForContextMenu(listView)
    }

    fun anadirPlaneta(
        adaptador: ArrayAdapter<BPlaneta>
    ) {
        abrirActividadSinParametros(
            CrearPlaneta::class.java
        )
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        val planetaSeleccionado = BaseDeDatos.tablaPlaneta!!.consultarTodosPlanetas()[posicion]
        idItemSeleccionado = planetaSeleccionado.id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_planeta -> {
                abrirActividadConParametros(
                    EditarPlaneta::class.java
                )
                return true
            }

            R.id.mi_eliminar_planeta -> {
                confirmarEliminacionDialogo()
                return true
            }

            R.id.mi_ver_habitantes -> {
                abrirActividadHabitantes(
                    Habitantes::class.java
                )
                return true
            }


            else -> super.onContextItemSelected(item)
        }
    }

    fun confirmarEliminacionDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                val respuesta = BaseDeDatos
                    .tablaPlaneta!!.eliminarPlanetaFormulario(
                        idItemSeleccionado
                    )
                if (respuesta) mostrarSnackbar("Planeta Eliminado")
                arreglo.removeAt(posicionItemSeleccionado)
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


    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.lv_list_view),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(this, clase)
        //Enviar parámetros (solamente variables primitivas)
        val planetaSeleccionad = BaseDeDatos.tablaPlaneta!!.consultarPlanetaPorID(idItemSeleccionado)
        intentExplicito.putExtra("nombre", planetaSeleccionad.nombre)
        intentExplicito.putExtra("galaxia", planetaSeleccionad.galaxia)
        intentExplicito.putExtra("estrella", planetaSeleccionad.estrella)
        intentExplicito.putExtra("masa", planetaSeleccionad.masa)
        intentExplicito.putExtra("edad", planetaSeleccionad.edad)

        callbackContenidoIntentExplicito.launch(intentExplicito)
    }
    fun abrirActividadSinParametros(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)

        callbackCrearPlaneta.launch(intent)
    }
    fun abrirActividadHabitantes(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)

        callbackHabitantes.launch(intent)
    }
    companion object {
        var arreglo = arrayListOf<BPlaneta>()
        //val arreglo = BBaseDatosMemoria.arregloBPlaneta
        var posicionItemSeleccionado = 0
        var idItemSeleccionado = 0
    }
}
