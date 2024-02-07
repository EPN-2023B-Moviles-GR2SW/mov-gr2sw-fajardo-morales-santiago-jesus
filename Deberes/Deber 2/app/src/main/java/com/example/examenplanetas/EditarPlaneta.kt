package com.example.examenplanetas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class EditarPlaneta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_planeta)
        val nombre = intent.getStringExtra("nombre")
        val galaxia = intent.getStringExtra("galaxia")
        val estrella = intent.getStringExtra("estrella")
        val masa = intent.getDoubleExtra("masa", 0.0)
        val edad = intent.getLongExtra("edad", 0)


        val inputNombre = findViewById<EditText>(R.id.input_nombre_planeta)
        inputNombre.hint = nombre
        val inputGalaxia = findViewById<EditText>(R.id.input_galaxia_planeta)
        inputGalaxia.hint = galaxia
        val inputEstrella = findViewById<EditText>(R.id.input_estrella_planeta)
        inputEstrella.hint = estrella
        val inputMasa = findViewById<EditText>(R.id.input_masa_planeta)
        inputMasa.hint = masa.toString()
        val inputEdad = findViewById<EditText>(R.id.input_edad_planeta)
        inputEdad.hint = edad.toString()

        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_editar)
        botonAceptar.setOnClickListener {

            val nombreModificado = if (inputNombre.text.isNotEmpty()) {
                inputNombre.text.toString()
            } else {
                nombre
            }
            val galaxiaModificada = if (inputGalaxia.text.isNotEmpty()) {
                inputGalaxia.text.toString()
            } else {
                galaxia
            }
            val estrellaModificada = if (inputEstrella.text.isNotEmpty()) {
                inputEstrella.text.toString()
            } else {
                estrella
            }
            val masaModificada = if (inputMasa.text.isNotEmpty()) {
                inputMasa.text.toString().toDouble()
            } else {
                masa
            }
            val edadModificada = if (inputEdad.text.isNotEmpty()) {
                inputEdad.text.toString().toLong()
            } else {
                edad
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
        val botonCancelar = findViewById<Button>(R.id.btn_cancelar_editar)
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

}