package org.ldv.webmonsters.service

import org.ldv.webmonsters.model.dao.UtilisateurDAO
import org.ldv.webmonsters.model.entity.Admin
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Service chargé de charger un utilisateur depuis la base de données
 * pour Spring Security.
 */
@Service
class MyUserDetailsService(private val utilisateurDAO: UtilisateurDAO) : UserDetailsService {

    /**
     * Méthode appelée automatiquement par Spring Security lors du login
     */
    override fun loadUserByUsername(username: String): UserDetails {
        // Récupération de l'utilisateur via son pseudo
        val utilisateur = utilisateurDAO.findByPseudo(username)
            ?: throw UsernameNotFoundException("User not found")

        // Détermination du rôle
        val leRole = when(utilisateur) {
            is Admin -> "ADMIN"
            else -> "JOUEUR"
        }

        // Construction de l'objet UserDetails
        return User
            .withUsername(utilisateur.pseudo)
            .password(utilisateur.getMotDePasse())
            .roles(leRole)
            .build()
    }
}