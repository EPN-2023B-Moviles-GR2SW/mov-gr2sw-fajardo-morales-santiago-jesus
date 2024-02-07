package com.example.examenplanetas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperPlaneta(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPlanetas =
            """
               CREATE TABLE PLANETAS(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               galaxia VARCHAR(50),
               estrella VARCHAR(50),
               masa DOUBLE,
               edad INTEGER
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPlanetas)
    }
    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}

    fun crearPlaneta(
        nombre: String,
        galaxia: String,
        estrella: String,
        masa: Double,
        edad: Int

    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("galaxia", galaxia)
        valoresAGuardar.put("estrella", estrella)
        valoresAGuardar.put("masa", masa)
        valoresAGuardar.put("edad", edad)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "PLANETAS", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1

    }
    fun eliminarPlanetaFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PLANETAS", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarPlanetaFormulario(
        nombre: String,
        galaxia: String,
        estrella: String,
        masa: Double,
        edad: Long,
        id:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("galaxia", galaxia)
        valoresAActualizar.put("estrella", estrella)
        valoresAActualizar.put("masa", masa)
        valoresAActualizar.put("edad", edad)

        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "PLANETAS", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }
    fun consultarPlanetaPorID(id: Int): BPlaneta{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PLANETAS WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BPlaneta("", "", "", 0.0, 0, arrayListOf())
        val arreglo = arrayListOf<BPlaneta>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1) // Indice 0
                val galaxia = resultadoConsultaLectura.getString(2)
                val estrella = resultadoConsultaLectura.getString(3)
                val masa = resultadoConsultaLectura.getDouble(4)
                val edad = resultadoConsultaLectura.getLong(5)
                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.galaxia = galaxia
                    usuarioEncontrado.estrella = estrella
                    usuarioEncontrado.masa = masa
                    usuarioEncontrado.edad = edad

                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarTodosPlanetas(): ArrayList<BPlaneta> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM PLANETAS
    """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val listaPlanetas = ArrayList<BPlaneta>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val galaxia = resultadoConsultaLectura.getString(2)
                val estrella = resultadoConsultaLectura.getString(3)
                val masa = resultadoConsultaLectura.getDouble(4)
                val edad = resultadoConsultaLectura.getLong(5)

                val planeta = BPlaneta(nombre, galaxia, estrella, masa, edad, arrayListOf())
                planeta.id = id

                listaPlanetas.add(planeta)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return listaPlanetas
    }

}