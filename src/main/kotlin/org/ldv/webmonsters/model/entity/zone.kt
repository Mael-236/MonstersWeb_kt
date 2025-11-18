package org.ldv.webmonsters.model.entity

class zone(
    val id: Long?,
    val nom: String,
    val description: String,
    val niveauMax: Int,
    val niveauMin: Int,
) {
    private val monstres = mutableListOf<monstre>()
    private val objets = mutableListOf<objet>()

    fun explorer() {
        println("Exploration de la zone: $nom")
    }

    fun genererMonstreSauvage(): monstre {
        val niveauMonstre = (niveauMin..niveauMax).random()
        return monstre(
            id = (1..1000).random().toLong(),
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

    fun estAccessible(utilisateur: utilisateur): Boolean {
        // Logique pour vérifier si l'utilisateur peut accéder à cette zone
        return true
    }
}