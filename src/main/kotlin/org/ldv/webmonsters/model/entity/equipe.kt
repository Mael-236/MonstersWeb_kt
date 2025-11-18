package org.ldv.webmonsters.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class equipe {
    @Id
    var monsActif: monstre? = null
    val tailleMax: Int = 6
    private val monstres = mutableListOf<monstre>()

    fun ajouterMonstre(monstre: monstre): Boolean {
        return if (!estPleine()) {
            monstres.add(monstre)
            if (monsActif == null) {
                monsActif = monstre
            }
            println("${monstre.nom} ajouté à l'équipe")
            true
        } else {
            println("L'équipe est pleine!")
            false
        }
    }

    fun retirerMonstre(monstre: monstre) {
        if (monstres.remove(monstre)) {
            if (monsActif == monstre) {
                monsActif = monstres.firstOrNull()
            }
            println("${monstre.nom} retiré de l'équipe")
        }
    }

    fun changerMonstreActif(monstre: monstre) {
        if (monstre in monstres) {
            monsActif = monstre
            println("${monstre.nom} est maintenant actif")
        }
    }

    fun estPleine(): Boolean = monstres.size >= tailleMax

    fun getMonstres(): List<monstre> = monstres.toList()
}