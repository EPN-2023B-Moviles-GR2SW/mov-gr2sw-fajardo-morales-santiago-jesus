package com.example.b2023gr2sw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        //el intent ya existe por defecto
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringArrayExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener { devolverRespuesta() }
    }
    fun devolverRespuesta(){
        //enviamos anteriormente, y ahora revolvemos o recibimos una respuesta
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Vicente")
        intentDevolverParametros.putExtra("edadModificado",33)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
}