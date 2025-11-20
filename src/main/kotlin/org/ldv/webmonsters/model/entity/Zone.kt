package org.ldv.webmonsters.model.entity

class Zone(
    val id: Long?,
    val nom: String,
    val description: String,
    val niveauMax: Int,
    val niveauMin: Int,
) {
    private val Monstres = mutableListOf<Monstre>()
    private val objets = mutableListOf<Objet>()

    fun explorer() {
        println("Exploration de la zone: $nom")
    }

    fun genererMonstreSauvage(): Monstre {
        val niveauMonstre = (niveauMin..niveauMax).random()
        return Monstre(
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

    fun estAccessible(utilisateur: Utilisateur): Boolean {
        // Logique pour vérifier si l'utilisateur peut accéder à cette zone
        return true
    }
}