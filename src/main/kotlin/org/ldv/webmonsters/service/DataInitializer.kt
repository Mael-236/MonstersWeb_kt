package org.ldv.webmonsters.service

import org.ldv.webmonsters.model.dao.*
import org.ldv.webmonsters.model.entity.*
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val utilisateurDAO: UtilisateurDAO,
    private val adminDAO: AdminDAO,
    private val roleDAO: RoleDAO,
    private val zoneDAO: ZoneDAO,
    private val monstreDAO: MonstreDAO,
    private val objetDAO: ObjetDAO,
    private val equipeDAO: EquipeDAO,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Vérifie si la base contient déjà des données
        if (utilisateurDAO.count() > 0 || zoneDAO.count() > 0) {
            println("ℹ️ Données déjà présentes, initialisation ignorée.")
            return
        }

        println("🚀 Initialisation des données...")

        // === Rôles ===
        val roleAdmin = Role(
            id = null,
            nom = "ADMIN"
        )
        val roleJoueur = Role(
            id = null,
            nom = "JOUEUR"
        )
        roleDAO.saveAll(listOf(roleAdmin, roleJoueur))

        // === Utilisateurs ===
        val admin = Admin(
            id = null,
            pseudo = "Admin",
            motDePasse = passwordEncoder.encode("admin123")
        )
        admin.role = roleAdmin
        adminDAO.save(admin)

        val joueur = Utilisateur(
            id = null,
            pseudo = "Joueur1",
            motDePasse = passwordEncoder.encode("joueur123")
        )
        joueur.role = roleJoueur
        utilisateurDAO.save(joueur)

        // === Zones ===
        val foretMystique = Zone(
            id = null,
            nom = "Forêt Mystique",
            description = "Une forêt dense peuplée de créatures de type plante et feu",
            niveauMax = 10,
            niveauMin = 1
        )

        val lacCristal = Zone(
            id = null,
            nom = "Lac Cristal",
            description = "Un lac paisible où vivent des créatures aquatiques",
            niveauMax = 20,
            niveauMin = 8
        )

        val montTonnerre = Zone(
            id = null,
            nom = "Mont Tonnerre",
            description = "Une montagne dangereuse habitée par des créatures électriques",
            niveauMax = 35,
            niveauMin = 18
        )

        zoneDAO.saveAll(listOf(foretMystique, lacCristal, montTonnerre))

        // === Objets ===
        val potion = Objet(
            id = null,
            nom = "Potion",
            description = "Restaure 20 PV",
            prix = 50,
            type = "Soin",
            effet = "heal_20"
        )

        val superPotion = Objet(
            id = null,
            nom = "Super Potion",
            description = "Restaure 50 PV",
            prix = 100,
            type = "Soin",
            effet = "heal_50"
        )

        val monsterBall = Objet(
            id = null,
            nom = "Monster Ball",
            description = "Permet de capturer un monstre affaibli",
            prix = 200,
            type = "Capture",
            effet = "capture_30"
        )

        val superBall = Objet(
            id = null,
            nom = "Super Ball",
            description = "Meilleure chance de capturer un monstre",
            prix = 400,
            type = "Capture",
            effet = "capture_50"
        )

        objetDAO.saveAll(listOf(potion, superPotion, monsterBall, superBall))

        // === Monstres ===
        val charbouk = Monstre(
            id = null,
            nom = "Charbouk",
            niveau = 5,
            pv = 45,
            pvMax = 45,
            attaque = 12,
            defense = 8,
            type = "Feu",
            estSauvage = true,
        )

        val aquabri = Monstre(
            id = null,
            nom = "Aquabri",
            niveau = 5,
            pv = 50,
            pvMax = 50,
            attaque = 10,
            defense = 10,
            type = "Eau",
            estSauvage = true,
        )

        val brambou = Monstre(
            id = null,
            nom = "Brambou",
            niveau = 5,
            pv = 55,
            pvMax = 55,
            attaque = 11,
            defense = 12,
            type = "Plante",
            estSauvage = true,
        )

        val voltix = Monstre(
            id = null,
            nom = "Voltix",
            niveau = 15,
            pv = 60,
            pvMax = 60,
            attaque = 18,
            defense = 10,
            type = "Électrique",
            estSauvage = true,
        )

        monstreDAO.saveAll(listOf(charbouk, aquabri, brambou, voltix))

        // === Équipe de départ ===
        val equipeJoueur = Equipe(
            id = null,
            tailleMax = 6
        )
        equipeDAO.save(equipeJoueur)

        val monstreStarter = Monstre(
            id = null,
            nom = "Charbouk",
            niveau = 5,
            pv = 45,
            pvMax = 45,
            attaque = 12,
            defense = 8,
            type = "Feu",
            estSauvage = false
        )
        equipeJoueur.ajouterMonstre(monstreStarter)
        monstreDAO.save(monstreStarter)

        equipeJoueur.monsActif = monstreStarter
        equipeDAO.save(equipeJoueur)

        println("✅ Données initiales insérées :")
        println("   - ${roleDAO.count()} rôles")
        println("   - ${utilisateurDAO.count()} utilisateurs")
        println("   - ${zoneDAO.count()} zones")
        println("   - ${monstreDAO.count()} monstres")
        println("   - ${objetDAO.count()} objets")
        println("   - ${equipeDAO.count()} équipes")
    }
}