package org.ldv.webmonsters.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainController {

    @GetMapping("/")
    fun home(): String {
        return "index"
    }

    @GetMapping("/login")
    fun login(@RequestParam error: Boolean?, model: Model): String {
        model.addAttribute("error", error == true)
        return "pagesVisiteur/login"
    }

    @GetMapping("/profil")
    fun profile(authentication: Authentication): String {
        // Récupération des rôles de l'utilisateur connecté
        val roles = authentication.authorities.map { it.authority }

        // Si l'utilisateur est admin → redirection
        if ("ROLE_ADMIN" in roles) {
            return "redirect:/webmonsters/admin"
        }

        // Sinon → page profil joueur
        return "redirect:/webmonsters/joueur/profil"
    }

    @GetMapping("/a-propos")
    fun aPropos(): String {
        return "pagesVisiteur/a-propos"
    }

    @GetMapping("/contact")
    fun contact(): String {
        return "pagesVisiteur/contact"
    }

    @GetMapping("/inscription")
    fun inscription(): String {
        return "pagesVisiteur/inscription"
    }

    @GetMapping("/produits")
    fun produits(): String {
        return "pagesVisiteur/produits"
    }

    @GetMapping("/rgpd")
    fun rgpd(): String {
        return "pagesVisiteur/rgpd"
    }
}