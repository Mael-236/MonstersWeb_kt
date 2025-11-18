package org.ldv.webmonsters.model.entity

open class utilisateur(
    val id: Int,
    var pseudo: String,
    private var motDePasse: String,
    val estAdmin: Boolean = false
) {
    fun seConnecter() {
        println("$pseudo s'est connecté")
    }

    fun seDeconnecter() {
        println("$pseudo s'est déconnecté")
    }

    fun modifierMotDePasse(nouveauMotDePasse: String) {
        motDePasse = nouveauMotDePasse
        println("Mot de passe modifié")
    }

    fun sauvegarderPartie() {
        println("Partie sauvegardée")
    }

    fun chargerSauvegarde() {
        println("Sauvegarde chargée")
    }
}