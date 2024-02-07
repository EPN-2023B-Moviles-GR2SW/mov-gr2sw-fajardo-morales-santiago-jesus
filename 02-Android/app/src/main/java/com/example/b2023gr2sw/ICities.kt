package com.example.b2023gr2sw

class ICities(
    private var name: String?,
    private var state: String?,
    private var country: String?,
    private var capital: Boolean?,
    private var population: Long?,
    private var regions: List<String>?,

    ) {
    override fun toString(): String {
        return "${name} - ${country}"
    }
}