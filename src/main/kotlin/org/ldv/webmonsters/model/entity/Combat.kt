package org.ldv.webmonsters.model.entity

class Combat(
    val id: Int,
    val zone: Zone,
    private val Monstres: List<Monstre>
) {
    var dureeCombat: Double = 0.0
    var tour: Int = 0
    var estTermine: Boolean = false

    fun demarrerCombat() {
        println("Le combat commence!")
        tour = 1
    }

    fun executerTour() {
        if (!estTermine) {
            println("--- Tour $tour ---")
            tour++
            // Logique du tour de combat
        }
    }

    fun terminerCombat() {
        estTermine = true
        println("Le combat est terminé!")
    }

    fun capturerMonstre(): Boolean {
        val reussite = (1..100).random() > 50
        if (reussite) {
            println("Monstre capturé avec succès!")
        } else {
            println("La capture a échoué!")
        }
        return reussite
    }

    fun fuir(): Boolean {
        val reussite = (1..100).random() > 30
        if (reussite) {
            println("Fuite réussie!")
            terminerCombat()
        } else {
            println("Impossible de fuir!")
        }
        return reussite
    }
}