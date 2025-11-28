package org.ldv.webmonsters.model.entity

import jakarta.persistence.*

@Entity
class Zone(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var nom: String,

    @Column(nullable = false, length = 500)
    var description: String,

    @Column(nullable = false)
    var niveauMax: Int,

    @Column(nullable = false)
    var niveauMin: Int,

    // Relations (à ajouter plus tard selon votre diagramme)
    @ManyToMany

    @JoinTable(
        name = "zone_monstre",
        joinColumns = [JoinColumn(name = "zone_id")],
        inverseJoinColumns = [JoinColumn(name = "monstre_id")]
    )
    var monstres: MutableList<Monstre> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "zone_objet",
        joinColumns = [JoinColumn(name = "zone_id")],
        inverseJoinColumns = [JoinColumn(name = "objet_id")]
    )
    var objets: MutableList<Objet> = mutableListOf()
) {
    fun explorer() {
        println("Exploration de la zone: $nom")
    }

    fun genererMonstreSauvage(): Monstre {
        val niveauMonstre = (niveauMin..niveauMax).random()
        return Monstre(
            id = null,
            nom = "Monstre Sauvage",
            niveau = niveauMonstre,
            pv = 50 + niveauMonstre * 10,
            pvMax = 50 + niveauMonstre * 10,
            attaque = 10 + niveauMonstre * 2,
            defense = 5 + niveauMonstre,
            type = "Normal",
            estSauvage = true
        )
    }

    fun estAccessible(utilisateur: Utilisateur): Boolean {
        return true
    }
}