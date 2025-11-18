package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
class monstre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var nom: String,

    @Column(nullable = false)
    var niveau: Int,

    @Column(nullable = false)
    var pv: Int,

    @Column(nullable = false)
    var pvMax: Int,

    @Column(nullable = false)
    var attaque: Int,

    @Column(nullable = false)
    var defense: Int,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var estSauvage: Boolean = true,
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