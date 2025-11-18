package org.ldv.webmonsters.model.entity

class objet(
    val id: Int,
    val nom: String,
    val description: String,
    val prix: Int,
    val type: String,
    val effet: String
) {
    fun utiliser(cible: monstre) {
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