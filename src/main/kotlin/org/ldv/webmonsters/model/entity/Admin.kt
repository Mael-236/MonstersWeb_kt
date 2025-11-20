package org.ldv.webmonsters.model.entity

import jakarta.persistence.Entity

@Entity
open class Admin(

    id: Long?,
    pseudo: String,
    motDePasse: String
) : Utilisateur(id, pseudo, motDePasse, estAdmin = true) {

    fun supprimerJoueur(joueur: Utilisateur) {
        println("Joueur ${joueur.pseudo} supprimé par l'admin")
    }
}