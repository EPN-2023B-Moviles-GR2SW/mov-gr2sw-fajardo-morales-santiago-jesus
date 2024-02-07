package com.example.examenplanetas

class BBaseDatosMemoria {
    companion object {
        val arregloBPlaneta = arrayListOf<BPlaneta>()

        init {

            arregloBPlaneta.add(
                BPlaneta(

                    "Tierra",
                    "Vía Láctea",
                    "El Sol",
                    5.972E24,
                    4550000000,
                    arrayListOf(
                        BHabitante("Humano","Animal","Mamífero",false,"Sexual"),
                        BHabitante("Rosa","Vegetal","Magnoliopsida",false,"Asexual")
                    )
                )
            )
            arregloBPlaneta.add(
                BPlaneta(

                    "Marte",
                    "Vía Láctea",
                    "El Sol",
                    6.4185E23,
                    4500000000,
                    arrayListOf(
                        BHabitante("Marciano","Animal","Mamífero",true,"Sexual"),
                        BHabitante("Lobo","Animal","Mamífero",true,"Sexual")
                    )
                )
            )
        }
    }
}