package org.ldv.webmonsters.controller
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController (){

    /**
     * Méthode permettant d'afficher la page d'accueil de l'application.
     * @return le chemin vers le template a partir du dossier ressources/templates (on ne marque pas le .html)
     */
    @GetMapping("/leCheminDuGetMapping")
    fun home():String{
        return "index"
    }

    /**
     * Méthode permettant d'afficher la page "à propos"
     */
    @GetMapping("/a-propos")
    fun aPropos():String{
        return "a-propos"
    }

    /**
     * Méthode permettant d'afficher la page de contact
     */
    @GetMapping("/contact")
    fun contact():String{
        return "contact"
    }

    /**
     * Méthode permettant d'afficher la page d'inscription
     */
    @GetMapping("/inscription")
    fun inscription():String{
        return "inscription"
    }

    /**
     * Méthode permettant d'afficher la page des produits
     */
    @GetMapping("/produits")
    fun produits():String{
        return "produits"
    }

    /**
     * Méthode permettant d'afficher la page RGPD
     */
    @GetMapping("/rgpd")
    fun rgpd():String{
        return "rgpd"
    }
}