package org.ldv.webmonsters.controller.joueurcontrollers

import org.ldv.webmonsters.model.dao.UtilisateurDAO
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/webmonsters/joueur")
class JoueurController(
    private val utilisateurDAO: UtilisateurDAO
) {

    @GetMapping("/profil")
    fun profil(authentication: Authentication, model: Model): String {
        val utilisateur = utilisateurDAO.findByPseudo(authentication.name)
        model.addAttribute("utilisateur", utilisateur)
        return "pageJoueur/profil"
    }
}