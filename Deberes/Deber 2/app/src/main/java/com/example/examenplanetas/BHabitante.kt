package com.example.examenplanetas


class BHabitante (
    var id: Int,
    var nombre: String,
    var reino: String,
    var tipo: String,
    var extinto: Boolean,
    var tipoReproduccion: String
) {
    // Constructor primario
    constructor(
        nombre: String,
        reino: String,
        tipo: String,
        extinto: Boolean,
        tipoReproduccion: String
    ) : this(
        generateId(),
        nombre,
        reino,
        tipo,
        extinto,
        tipoReproduccion
    )

    override fun toString(): String {
        return "${nombre} - ${reino}"
    }

    companion object {
        private var idCounter = 1

        private fun generateId(): Int {
            return idCounter++
        }
    }
}