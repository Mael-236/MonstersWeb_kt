package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
class Objet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var nom: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var prix: Int,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var effet: String,
) {
    fun utiliser(cible: Monstre) {
        println("$nom utilisé sur ${cible.nom}: $effet")
        // Logique d'application de l'effet
    }

    fun acheter() {
        println("$nom acheté pour $prix pièces")
    }

    fun vendre() {
        val prixVente = prix / 2
        println("$nom vendu pour $prixVente pièces")
    }
}