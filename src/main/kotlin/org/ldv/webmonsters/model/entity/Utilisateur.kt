package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Utilisateur(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var pseudo: String,

    @Column(nullable = false)
    private var motDePasse: String,

    @Column(nullable = false)
    val estAdmin: Boolean = false,
)
{
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