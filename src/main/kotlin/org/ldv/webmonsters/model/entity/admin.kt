package org.ldv.webmonsters.model.entity

import jakarta.persistence.Entity

@Entity
open class admin(

    id: Long?,
    pseudo: String,
    motDePasse: String
) : utilisateur(id, pseudo, motDePasse, estAdmin = true) {

    fun supprimerJoueur(joueur: utilisateur) {
        println("Joueur ${joueur.pseudo} supprimé par l'admin")
    }
}