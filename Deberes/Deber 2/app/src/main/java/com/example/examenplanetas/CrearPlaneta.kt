package com.example.examenplanetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CrearPlaneta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_planeta)


        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_crear_planeta)
        botonAceptar.setOnClickListener {
            val inputNombre = findViewById<EditText>(R.id.input_crear_nombre_planeta)
            val inputGalaxia = findViewById<EditText>(R.id.input_crear_galaxia)
            val inputEstrella = findViewById<EditText>(R.id.input_crear_estrella)
            val inputMasa = findViewById<EditText>(R.id.input_crear_masa)
            val inputEdad = findViewById<EditText>(R.id.input_crear_edad)

            val nombreModificado = if (inputNombre.text.isNotEmpty()) {
                inputNombre.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Nombre")
                null
            }
            val galaxiaModificada = if (inputGalaxia.text.isNotEmpty()) {
                inputGalaxia.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Galaxia")
                null
            }
            val estrellaModificada = if (inputEstrella.text.isNotEmpty()) {
                inputEstrella.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Estrella")
                null
            }
            val masaModificada = if (inputMasa.text.isNotEmpty()) {
                inputMasa.text.toString().toDouble()
            } else {
                mostrarSnackbar("Llene el campo Masa")
                null
            }
            val edadModificada = if (inputEdad.text.isNotEmpty()) {
                inputEdad.text.toString().toLong()
            } else {
                mostrarSnackbar("Llene el campo Edad")
                null
            }

            if (nombreModificado != null && galaxiaModificada != null && estrellaModificada
                != null && masaModificada != null && edadModificada != null
            ) {
                devolverRespuesta(
                    nombreModificado,
                    galaxiaModificada,
                    estrellaModificada,
                    masaModificada,
                    edadModificada
                )
            }
        }
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_crear_planeta)
        botonCancelar.setOnClickListener {
            finish()
        }


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
        intentDevolverParametros.putExtra("nombrePlaneta", nombre)
        intentDevolverParametros.putExtra("galaxiaPlaneta", galaxia)
        intentDevolverParametros.putExtra("estrellaPlaneta", estrella)
        intentDevolverParametros.putExtra("masaPlaneta", masa)
        intentDevolverParametros.putExtra("edadPlaneta", edad)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_crear_planeta),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

}