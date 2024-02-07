package com.example.examenplanetas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelperHabitante(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
)  {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaHabitantes =
            """
               CREATE TABLE HABITANTES(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               reino VARCHAR(50),
               tipo VARCHAR(50),
               extinto BOOLEAN,
               tipoReproduccion VARCHAR(50),
               idPlaneta INTEGER,
               FOREIGN KEY (idPlaneta) REFERENCES PLANETA(id)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaHabitantes)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}
    fun crearHabitante(
        nombre: String,
        reino: String,
        tipo: String,
        extinto: Boolean,
        tipoReproduccion: String,
        idPlaneta: Int

    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("reino", reino)
        valoresAGuardar.put("tipo", tipo)
        valoresAGuardar.put("extinto", extinto)
        valoresAGuardar.put("tipoReproduccion", tipoReproduccion)
        valoresAGuardar.put("idPlaneta", idPlaneta)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "HABITANTES", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }
    fun eliminarHabitanteFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "HABITANTES", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }
    fun actualizarHabitanteFormulario(
        nombre: String,
        reino: String,
        tipo: String,
        extinto: Boolean,
        tipoReproduccion: String,
        idPlaneta: Int,
        id:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("reino", reino)
        valoresAActualizar.put("tipo", tipo)
        valoresAActualizar.put("extinto", extinto)
        valoresAActualizar.put("tipoReproduccion", tipoReproduccion)
        valoresAActualizar.put("idPlaneta", idPlaneta)

        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "HABITANTES", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }
    fun consultarHabitantePorID(id: Int): BHabitante{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM HABITANTES WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BHabitante( "", "", "", true, "" )
        val arreglo = arrayListOf<BHabitante>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val reino = resultadoConsultaLectura.getString(2)
                val tipo = resultadoConsultaLectura.getString(3)
                val extintoInt = resultadoConsultaLectura.getInt(4)
                val extinto: Boolean = extintoInt != 1
                val tipoReproduccion = resultadoConsultaLectura.getString(5)
                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.reino = reino
                    usuarioEncontrado.tipo = tipo
                    usuarioEncontrado.extinto = extinto
                    usuarioEncontrado.tipoReproduccion = tipoReproduccion

                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
    fun consultarTodosHabitantes(): ArrayList<BHabitante> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM HABITANTES
    """.trimIndent()
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        val listaHabitante = ArrayList<BHabitante>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val reino = resultadoConsultaLectura.getString(2)
                val tipo = resultadoConsultaLectura.getString(3)
                val extintoInt = resultadoConsultaLectura.getInt(4)
                val extinto: Boolean = extintoInt != 1
                val tipoReproduccion = resultadoConsultaLectura.getString(5)

                val habitante = BHabitante(nombre, reino, tipo, extinto, tipoReproduccion)
                habitante.id = id

                listaHabitante.add(habitante)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return listaHabitante
    }

    fun consultarHabitantesPorIdPlaneta(idPlaneta: Int): ArrayList<BHabitante> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM HABITANTES WHERE idPlaneta = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idPlaneta.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura // Parámetros
        )

        val listaHabitantes = ArrayList<BHabitante>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val reino = resultadoConsultaLectura.getString(2)
                val tipo = resultadoConsultaLectura.getString(3)
                val extintoInt = resultadoConsultaLectura.getInt(4)
                val extinto: Boolean = extintoInt != 1
                val tipoReproduccion = resultadoConsultaLectura.getString(5)

                val habitante = BHabitante(nombre, reino, tipo, extinto, tipoReproduccion)
                habitante.id = id

                listaHabitantes.add(habitante)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return listaHabitantes
    }

}
