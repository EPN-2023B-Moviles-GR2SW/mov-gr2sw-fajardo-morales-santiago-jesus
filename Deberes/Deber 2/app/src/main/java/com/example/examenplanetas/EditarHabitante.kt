package com.example.examenplanetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class EditarHabitante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_habitante)

        val habitanteSeleccionado =findViewById<TextView>(R.id.txt_habitante)
        habitanteSeleccionado.setText(Habitantes.arreglo[Habitantes.posicionItemSeleccionadoHabitantes].nombre)

        val nombre = intent.getStringExtra("nombre")
        val reino = intent.getStringExtra("reino")
        val tipo = intent.getStringExtra("tipo")
        val extinto = intent.getBooleanExtra("extinto", true)
        val reproduccion = intent.getStringExtra("reproduccion")

        val inputNombre = findViewById<EditText>(R.id.input_editar_nombreHabitante)
        inputNombre.hint = nombre
        val inputReino = findViewById<EditText>(R.id.input_editar_reinoHabitante)
        inputReino.hint = reino
        val inputTipo = findViewById<EditText>(R.id.input_editar_tipoHabitante)
        inputTipo.hint = tipo
        val inputReproduccion = findViewById<EditText>(R.id.input_editar_reproduccionHabitante)
        inputReproduccion.hint = reproduccion

        val checkBoxExtinto = findViewById<CheckBox>(R.id.chb_editar_habitante)
        checkBoxExtinto.isChecked = extinto

        val botonAceptar = findViewById<Button>(R.id.btn_editar_aceptar)
        botonAceptar.setOnClickListener {

            val nombreModificado = if (inputNombre.text.isNotEmpty()) {
                inputNombre.text.toString()
            } else {
                nombre
            }
            val reinoModificado = if (inputReino.text.isNotEmpty()) {
                inputReino.text.toString()
            } else {
                reino
            }
            val tipoModificado = if (inputTipo.text.isNotEmpty()) {
                inputTipo.text.toString()
            } else {
                tipo
            }
            val reproduccionModificada = if (inputReproduccion.text.isNotEmpty()) {
                inputReproduccion.text.toString()
            } else {
                reproduccion
            }

            val extintoModificado = checkBoxExtinto.isChecked

            if (nombreModificado != null && reinoModificado != null && tipoModificado
                != null && extintoModificado != null && reproduccionModificada != null
            ) {
                devolverRespuesta(
                    nombreModificado,
                    reinoModificado,
                    tipoModificado,
                    extintoModificado,
                    reproduccionModificada
                )
            }

        }
        val botonCancelar = findViewById<Button>(R.id.btn_editar_cancelar)
        botonCancelar.setOnClickListener {
            setResult(
                RESULT_OK,
                null
            )
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
}