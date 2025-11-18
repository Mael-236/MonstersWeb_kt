package org.ldv.webmonsters.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class admin(
    @Id
    id: Int,
    pseudo: String,
    motDePasse: String
) : utilisateur(id, pseudo, motDePasse, estAdmin = true) {

    fun supprimerJoueur(joueur: utilisateur) {
        println("Joueur ${joueur.pseudo} supprimé par l'admin")
    }
}