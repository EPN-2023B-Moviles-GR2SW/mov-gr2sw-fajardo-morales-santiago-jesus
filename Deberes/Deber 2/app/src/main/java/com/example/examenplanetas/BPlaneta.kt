package com.example.examenplanetas

class BPlaneta(
    var id: Int,
    var nombre: String,
    var galaxia: String,
    var estrella: String,
    var masa: Double,
    var edad: Long,
    var arregloHabitantes: ArrayList<BHabitante>
) {
    // Constructor primario
    constructor(
        nombre: String,
        galaxia: String,
        estrella: String,
        masa: Double,
        edad: Long,
        arregloHabitantes: ArrayList<BHabitante>
    ) : this(
        generateId(),
        nombre,
        galaxia,
        estrella,
        masa,
        edad,
        arregloHabitantes
    )

    override fun toString(): String {
        return "${nombre} - ${galaxia}"
    }

    companion object {
        private var idCounter = 1

        private fun generateId(): Int {
            return idCounter++
        }
    }
}