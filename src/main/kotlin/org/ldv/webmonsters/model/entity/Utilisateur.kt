package org.ldv.webmonsters.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

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

    @Column(nullable = false, updatable = false)
    var dateCreation: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var dateModification: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "role_id")
    var role: Role? = null
) {
    // Getter pour le mot de passe (nécessaire pour Spring Security)
    fun getMotDePasse(): String = motDePasse

    // Setter pour le mot de passe
    fun setMotDePasse(nouveauMotDePasse: String) {
        motDePasse = nouveauMotDePasse
    }

    @PreUpdate
    fun preUpdate() {
        dateModification = LocalDateTime.now()
    }

    fun seConnecter() {
        println("$pseudo s'est connecté")
    }

    fun seDeconnecter() {
        println("$pseudo s'est déconnecté")
    }

    fun sauvegarderPartie() {
        println("Partie sauvegardée")
    }

    fun chargerSauvegarde() {
        println("Sauvegarde chargée")
    }
}