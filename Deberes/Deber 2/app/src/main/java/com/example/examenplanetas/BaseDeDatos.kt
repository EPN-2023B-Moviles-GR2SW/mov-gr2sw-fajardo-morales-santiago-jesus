package com.example.examenplanetas

class BaseDeDatos {
    companion object{
        var tablaPlaneta: SqliteHelperPlaneta? = null
        var tablaHabitante: SqliteHelperHabitante? = null
    }
}