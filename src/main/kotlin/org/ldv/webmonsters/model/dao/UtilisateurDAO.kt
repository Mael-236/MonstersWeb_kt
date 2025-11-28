package org.ldv.webmonsters.model.dao

import org.ldv.webmonsters.model.entity.Utilisateur
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UtilisateurDAO : JpaRepository<Utilisateur, Long> {
    @Query("select u from Utilisateur u where u.pseudo = ?1")
    fun findByPseudo(pseudo: String): Utilisateur?

    // Méthode pour trouver par email (au cas où vous voulez utiliser email comme identifiant)
    @Query("select u from Utilisateur u where u.pseudo = ?1")
    fun findByEmail(email: String): Utilisateur?
}