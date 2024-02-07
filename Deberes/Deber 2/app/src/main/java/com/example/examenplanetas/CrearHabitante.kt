package com.example.examenplanetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class CrearHabitante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_habitante)

        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_crearHabitante)
        botonAceptar.setOnClickListener {
            val inputNombre = findViewById<EditText>(R.id.input_crear_nombreHabitante)
            val inputReino = findViewById<EditText>(R.id.input_crear_reinoHabitante)
            val inputTipo = findViewById<EditText>(R.id.input_crear_tipoHabitante)
            val inputReproduccion = findViewById<EditText>(R.id.input_crear_reproduccion)
            val checkBox_extinto = findViewById<CheckBox>(R.id.chb_crear_extintoHabitante)


            val nombreModificado = if (inputNombre.text.isNotEmpty()) {
                inputNombre.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Nombre")
                null
            }
            val reinoModificado = if (inputReino.text.isNotEmpty()) {
                inputReino.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Reino")
                null
            }
            val tipoModificado = if (inputTipo.text.isNotEmpty()) {
                inputTipo.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Tipo")
                null
            }
            val reproduccionModificada = if (inputReproduccion.text.isNotEmpty()) {
                inputReproduccion.text.toString()
            } else {
                mostrarSnackbar("Llene el campo Reproducción")
                null
            }
            val extinto = checkBox_extinto.isChecked

            if (nombreModificado != null && reinoModificado != null && tipoModificado
                != null && reproduccionModificada != null
            ) {
                devolverRespuesta(
                    nombreModificado,
                    reinoModificado,
                    tipoModificado,
                    extinto,
                    reproduccionModificada
                )
            }
        }
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_crearHabitante)
        botonCancelar.setOnClickListener {
            finish()
        }


    }

    fun devolverRespuesta(
        nombre: String,
        reino: String,
        tipo: String,
        extinto: Boolean,
        reproduccion: String
    ) {
        //enviamos anteriormente, y ahora revolvemos o recibimos una respuesta
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreHabitante", nombre)
        intentDevolverParametros.putExtra("reinoHabitante", reino)
        intentDevolverParametros.putExtra("tipoHabitante", tipo)
        intentDevolverParametros.putExtra("extintoHabitante", extinto)
        intentDevolverParametros.putExtra("reproduccionHabitante", reproduccion)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_crearHabitante),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}