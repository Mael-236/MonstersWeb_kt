package org.ldv.webmonsters.model.entity

class monstre(
    val id: Int,
    val nom: String,
    var niveau: Int,
    var pv: Int,
    val pvMax: Int,
    var attaque: Int,
    var defense: Int,
    val type: String,
    val estSauvage: Boolean = true
) {
    fun attaquer(cible: monstre) {
        val degats = maxOf(0, this.attaque - cible.defense)
        cible.recevoirDegats(degats)
        println("$nom attaque ${cible.nom} et inflige $degats dégâts")
    }

    fun recevoirDegats(degats: Int) {
        pv = maxOf(0, pv - degats)
        if (estKO()) {
            println("$nom est KO!")
        }
    }

    fun monterNiveau() {
        niveau++
        attaque += 5
        defense += 3
        println("$nom monte au niveau $niveau!")
    }

    fun estKO(): Boolean = pv <= 0
}